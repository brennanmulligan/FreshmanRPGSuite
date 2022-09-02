package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.VanityType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The RDS implementation of the row data gateway
 */
public class VanityInventoryTableDataGateway
{

    private static VanityInventoryTableDataGateway singleton;
    private final DefaultItemsTableDataGateway defaultItemsTableDataGateway =
            DefaultItemsTableDataGateway.getSingleton();

    /**
     * Gets the instance of this gateway
     *
     * @return the instance
     * @throws DatabaseException shouldn't
     */
    public static synchronized VanityInventoryTableDataGateway getSingleton() throws DatabaseException
    {
        if (singleton == null)
        {
            singleton = new VanityInventoryTableDataGateway();
        }
        return singleton;
    }

    /**
     * Drop and re-create the VanityInventory table this gateway manages
     *
     * @throws DatabaseException shouldnt
     */
    public static void createTable() throws DatabaseException
    {
        String dropSql = "DROP TABLE IF EXISTS VanityInventory";
        String vanityInventorySql = "CREATE TABLE VanityInventory ("
                + "playerID INT NOT NULL, " +
                "vanityID INT NOT NULL," +
                "isWearing INT NOT NULL," +
                "FOREIGN KEY (playerID) REFERENCES Players(playerID) ON DELETE CASCADE, " +
                "FOREIGN KEY (vanityID) REFERENCES VanityItems(vanityID) ON DELETE CASCADE," +
                "CONSTRAINT PK_playerID_vanityID PRIMARY KEY (playerID, vanityID));";

        Connection connection = DatabaseManager.getSingleton().getConnection();

        try
        {
            PreparedStatement stmt = connection.prepareStatement(dropSql);
            stmt.execute();
            stmt.close();

            stmt = connection.prepareStatement(vanityInventorySql);
            stmt.execute();
            stmt.close();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create the vanity inventory table", e);
        }
    }

    /**
     * @param playerID the ID of the player
     * @return the list of vanity items
     */
    public ArrayList<VanityDTO> getWearing(int playerID) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        ArrayList<VanityDTO> results = new ArrayList<>();
        try
        {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM VanityInventory INNER JOIN VanityItems on VanityInventory.vanityID = VanityItems.vanityID WHERE playerID = ? AND isWearing = ?");
            stmt.setInt(1, playerID);
            stmt.setInt(2, 1);
            ResultSet result = stmt.executeQuery();

            while (result.next())
            {
                VanityDTO item = new VanityDTO(result.getInt("vanityID"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("textureName"),
                        VanityType.fromInt(result.getInt("type")));
                results.add(item);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't get the list of items the player is wearing", e);
        }
        ArrayList<VanityType> types = new ArrayList<>(Arrays.asList(VanityType.values()));
        ArrayList<VanityType> toRemove = new ArrayList<>();
        toRemove.add(VanityType.NAMEPLATE);
        for (VanityDTO dto : results)
        {
            toRemove.add(dto.getVanityType());
        }
        types.removeAll(toRemove);
        for (VanityType type : types)
        {
            try
            {
                results.add(defaultItemsTableDataGateway.getDefaultItem(type));
            }
            catch (DatabaseException e)
            {
                e.printStackTrace();
            }
        }

        return results;
    }

    /**
     * @param playerID the ID of the player
     * @return vanity DTOs of owned items
     */
    public ArrayList<VanityDTO> getAllOwnedItems(int playerID) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        ArrayList<VanityDTO> results = new ArrayList<>();
        try
        {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM (SELECT vanityID FROM VanityInventory WHERE playerID = ? UNION SELECT defaultID FROM DefaultItems) t1 INNER JOIN VanityItems on t1.vanityID = VanityItems.vanityID");
            stmt.setInt(1, playerID);
            ResultSet result = stmt.executeQuery();

            while (result.next())
            {
                VanityDTO item = new VanityDTO(result.getInt("vanityID"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("textureName"),
                        VanityType.fromInt(result.getInt("type")));
                results.add(item);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't get the list of items the player owns", e);
        }
        return results;
    }

    /**
     * Saves what a player has in their inventory
     *
     * @param playerID     the ID of the player to update
     * @param newInventory the list of items in their new inventory
     * @throws DatabaseException shouldnt
     */
    public void updateInventory(int playerID, ArrayList<VanityDTO> newInventory) throws DatabaseException
    {
        ArrayList<VanityDTO> owned = getAllOwnedItems(playerID);
        int lastKnown = newInventory.size() - 1;
        while (lastKnown >= 0 && !owned.contains(newInventory.get(lastKnown)))
        {
            lastKnown--;
        }
        for (int i = lastKnown + 1; i < newInventory.size(); i++)
        {
            addItemToInventory(playerID, newInventory.get(i).getID());
        }
    }

    /**
     * Saves what a player is currently wearing
     *
     * @param playerID   the ID of the player to update
     * @param newWearing the list of items they are wearing
     * @throws DatabaseException if the player doesn't own the item they are equipping
     */
    public void updateCurrentlyWearing(int playerID, ArrayList<VanityDTO> newWearing) throws DatabaseException
    {
        if (newWearing != null)
        {
            Connection connection = DatabaseManager.getSingleton().getConnection();
            ArrayList<VanityDTO> owned = getAllOwnedItems(playerID);
            ArrayList<Integer> itemsIDs = new ArrayList<>();
            for (VanityDTO dto : newWearing)
            {
                itemsIDs.add(dto.getID());
            }

            for (VanityDTO item : newWearing)
            {
                if (!owned.contains(item))
                {
                    throw new DatabaseException("Player " + playerID + "can't put on item " +
                            item.getID() + " because they don't own it.");
                }
            }
            try
            {
                PreparedStatement stmt = connection.prepareStatement("UPDATE VanityInventory SET isWearing = 0 WHERE playerID = ?");
                stmt.setInt(1, playerID);
                stmt.executeUpdate();
                for (int id : itemsIDs)
                {
                    stmt = connection.prepareStatement("UPDATE VanityInventory SET isWearing = 1 WHERE playerID = ? AND vanityID = ?");
                    stmt.setInt(1, playerID);
                    stmt.setInt(2, id);
                    int updated = stmt.executeUpdate();
                    if (updated == 0)
                    {
                        addItemToInventory(playerID, id, 1);
                    }
                }
                stmt = connection.prepareStatement("DELETE FROM VanityInventory WHERE EXISTS(SELECT * FROM DefaultItems WHERE vanityID = DefaultItems.defaultID) AND isWearing = 0;");
                stmt.executeUpdate();
            }
            catch (SQLException e)
            {
                throw new DatabaseException("Couldn't update what the player is wearing", e);
            }
        }
    }

    /**
     * Adds an item to a player's owned items
     *
     * @param playerID the ID of the player to update
     * @param vanityID the ID of the vanity they now own
     * @throws DatabaseException if the database couldn't update
     */
    public void addItemToInventory(int playerID, int vanityID) throws DatabaseException
    {
        addItemToInventory(playerID, vanityID, 0);
    }

    /**
     * Adds an item to a player's owned items
     *
     * @param playerID  the ID of the player to update
     * @param vanityID  the ID of the vanity they now own
     * @param isWearing 1 if the player is wearing the item, 0 if not
     * @throws DatabaseException if the database couldn't update
     */
    public void addItemToInventory(int playerID, int vanityID, int isWearing) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO VanityInventory SET playerID = ?, vanityID = ?, isWearing = ?");
            stmt.setInt(1, playerID);
            stmt.setInt(2, vanityID);
            stmt.setInt(3, isWearing);
            int updated = stmt.executeUpdate();
            if (updated != 1)
            {
                throw new DatabaseException("Duplicate item added " + playerID + " " + vanityID);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't add item to player inventory" + playerID, e);
        }
    }
}

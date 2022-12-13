package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.VanityType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The RDS gateway for the items in the default inventory table
 */
public class DefaultItemsTableDataGateway
{
    private static DefaultItemsTableDataGateway singleton;

    /**
     * Drop and re-create the DefaultItems table this gateway manages
     *
     * @throws DatabaseException shouldnt
     */
    public static void createTable() throws DatabaseException
    {
        String dropSql = "DROP TABLE IF EXISTS DefaultItems";
        String createSql =
                "CREATE TABLE DefaultItems(" + "defaultID INT NOT NULL UNIQUE," +
                        "defaultWearing INT, " +
                        "FOREIGN KEY (defaultID) REFERENCES VanityItems(vanityID) ON DELETE CASCADE );";

        Connection connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(dropSql))
        {
            stmt.execute();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to drop DefaultItems table", e);
        }

        try (PreparedStatement stmt = connection.prepareStatement(createSql))
        {
            stmt.execute();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create DefaultItems table", e);
        }
    }

    public static DefaultItemsTableDataGateway getSingleton()
    {
        if (singleton == null)
        {
            singleton = new DefaultItemsTableDataGateway();
        }
        return singleton;
    }

    /**
     * Adds a vanity item to the default item list so it will
     * be in all player's inventories
     *
     * @param defaultID the ID of the vanity item to add
     * @throws DatabaseException shouldn't
     */
    public void addDefaultItem(int defaultID) throws DatabaseException
    {
        addDefaultItem(defaultID, 0);
    }

    /**
     * Adds a vanity item to the default item list so it will
     * be in all player's inventories
     *
     * @param defaultID      the ID of the vanity item to add
     * @param defaultWearing 1 if the item should be default for that type, 0 if not
     * @throws DatabaseException shouldn't
     */
    public void addDefaultItem(int defaultID, int defaultWearing) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO DefaultItems SET defaultID = ?, defaultWearing = 0"))
        {
            stmt.setInt(1, defaultID);
            int updated = stmt.executeUpdate();
            if (updated != 1)
            {
                throw new DatabaseException(
                        "Could not add new Default Item to database (NOT ADDED)");
            }
            if (defaultWearing == 1)
            {
                setDefaultWearing(defaultID);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not add new Default Item to database", e);
        }
    }

    /**
     * Gets the default item for a specific vanity type
     *
     * @param type the vanity type
     * @return a dto of the default item for that type, or null if no default
     * @throws DatabaseException shouldnt
     */
    public VanityDTO getDefaultItem(VanityType type) throws DatabaseException
    {
        ArrayList<VanityDTO> defaults = getDefaultWearing();
        VanityDTO toReturn = null;
        for (VanityDTO dto : defaults)
        {
            if (dto.getVanityType() == type)
            {
                toReturn = dto;
            }
        }
        if (toReturn == null)
        {
            throw new DatabaseException(
                    "No default exists for vanity type: " + type.name());
        }
        return toReturn;
    }

    /**
     * Gets all the default items stored in the database
     *
     * @return a list of the default vanity items all players have
     * @throws DatabaseException shouldn't
     */
    public ArrayList<VanityDTO> getDefaultItems() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        ArrayList<VanityDTO> defaultItems = new ArrayList<>();
        VanityItemsTableDataGateway vanityItemsGateway = VanityItemsTableDataGateway.getSingleton();

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM DefaultItems");
             ResultSet result = stmt.executeQuery())
        {
            while (result.next())
            {
                defaultItems.add(vanityItemsGateway.getVanityItemByID(result.getInt("defaultID")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return defaultItems;
    }

    /**
     * @return the vanity items that are default when a player isn't
     * wearing a specific item
     * @throws DatabaseException shouldnt
     */
    public ArrayList<VanityDTO> getDefaultWearing() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        ArrayList<VanityDTO> defaultItems = new ArrayList<>();
        VanityItemsTableDataGateway vanityItemsGateway = VanityItemsTableDataGateway.getSingleton();

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM DefaultItems WHERE defaultWearing = 1");
             ResultSet result = stmt.executeQuery())
        {
            while (result.next())
            {
                defaultItems.add(vanityItemsGateway.getVanityItemByID(result.getInt("defaultID")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return defaultItems;
    }

    /**
     * Sets a default item as the default item that
     * is worn if a user doesn't have anything selected
     *
     * @param vanityID the vanity id
     * @throws DatabaseException if the item isn't a default item
     */
    public void setDefaultWearing(int vanityID) throws DatabaseException
    {
        ArrayList<VanityDTO> items = getDefaultItems();
        VanityDTO toBeAdded =
                VanityItemsTableDataGateway.getSingleton().getVanityItemByID(vanityID);
        VanityDTO toBeRemoved = null;
        VanityType type;
        ArrayList<VanityDTO> current = getDefaultWearing();

        if (items.contains(toBeAdded))
        {
            type = toBeAdded.getVanityType();
            for (VanityDTO dto : current)
            {
                if (dto.getVanityType() == type)
                {
                    toBeRemoved = dto;
                }
            }


            Connection connection = DatabaseManager.getSingleton().getConnection();
            if (toBeRemoved != null)
            {
                try (PreparedStatement rmvStmt = connection.prepareStatement("UPDATE DefaultItems SET defaultWearing = 0 WHERE defaultID = ?"))
                {
                    rmvStmt.setInt(1, toBeRemoved.getID());
                    rmvStmt.executeUpdate();
                }
                catch (SQLException e)
                {
                    throw new DatabaseException("Could not update default wearing", e);
                }
            }

            try (PreparedStatement addStmt = connection.prepareStatement("UPDATE DefaultItems SET defaultWearing = 1 WHERE defaultID = ?"))
            {
                addStmt.setInt(1, toBeAdded.getID());
                int updated = addStmt.executeUpdate();
                if (updated != 1)
                {
                    throw new DatabaseException("Could not update default wearing");
                }
            }
            catch (SQLException e)
            {
                throw new DatabaseException("Could not update default wearing", e);
            }
        }
        else
        {
            throw new DatabaseException("Invalid vanity ID for default");
        }
    }

    /**
     * Removes a vanity item from the default item list so it wont
     * be in all player's inventories anymore
     *
     * @param defaultID the id of the item to be removed
     * @throws DatabaseException shouldnt
     */
    public void removeDefaultItem(int defaultID) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM DefaultItems WHERE defaultID = ?"))
        {
            stmt.setInt(1, defaultID);
            int updated = stmt.executeUpdate();
            if (updated != 1)
            {
                throw new DatabaseException(
                        "Could not remove Default Item from database (NOT ADDED)");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not remove Default Item from database", e);
        }
    }

    /**
     * Resets the data
     */
    public void resetTableGateway()
    {

    }
}

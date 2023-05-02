package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.VanityType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The RDS implementation of the row data gateway
 */
public class VanityItemsTableDataGateway
{

    private static VanityItemsTableDataGateway singleton;

    /**
     * Gets the instance of this gateway
     *
     * @return the instance
     * @throws DatabaseException shouldn't
     */
    public static synchronized VanityItemsTableDataGateway getSingleton() throws DatabaseException
    {
        if (singleton == null)
        {
            singleton = new VanityItemsTableDataGateway();
        }
        return singleton;
    }

    /**
     * Drop and re-create the VanityItems table this gateway manages
     *
     * @throws DatabaseException if table could not be created
     */
    public static void createTable() throws DatabaseException
    {
        String dropSql = "DROP TABLE IF EXISTS DefaultItems, VanityAwards, VanityShop, VanityItems";
        String createSql = "CREATE TABLE VanityItems(" +
                "vanityID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "description VARCHAR(255), " +
                "textureName VARCHAR(255) UNIQUE, " +
                "type INT NOT NULL," +
                "price INT," +
                "isDeletable TINYINT," +
                "isInShop TINYINT," +
                "isDefault TINYINT," +
                "isDefaultEquipped TINYINT)";

        Connection connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(dropSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to drop VanityItems table", e);
        }

        try (PreparedStatement stmt = connection.prepareStatement(createSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create VanityItems table", e);
        }
    }

    /**
     * Gets a vanity item by id
     *
     * @param id the id of the vanity item
     * @return a DTO containing all the info of the vanity item
     */
    public VanityDTO getVanityItemByID(int id) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        VanityDTO item;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM VanityItems WHERE vanityID = ?"))
        {
            stmt.setInt(1, id);

            try (ResultSet result = stmt.executeQuery())
            {
                result.next();
                item = new VanityDTO(result.getInt("vanityID"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("textureName"),
                        VanityType.fromInt(result.getInt("type")),
                        result.getInt("price")
                );
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
        return item;
    }

    /**
     * @return a list of all vanity items
     */
    public ArrayList<VanityDTO> getAllVanityItems() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM VanityItems");
             ResultSet result = stmt.executeQuery())
        {
            return buildDTOs(result);
        }
        catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }

    /**
     * Gets a vanity type by id
     *
     * @param id the id of the vanity item
     * @return the type of the vanity
     */
    public VanityType getVanityTypeByID(int id) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        VanityType type = null;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM VanityItems WHERE vanityID = ?"))
        {
            stmt.setInt(1, id);

            try (ResultSet result = stmt.executeQuery())
            {
                result.next();
                type = VanityType.fromInt(result.getInt("type"));
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
        return type;
    }

    /**
     * Updates a vanity item
     *
     * @param id          the vanity item's id
     * @param name        the new name
     * @param description the new description
     * @param textureName the new texture name
     * @param vanityType  the new vanity type
     */
    public void updateVanityItem(int id, String name, String description, String textureName,
                                 VanityType vanityType, int price, boolean isDeletable, boolean isInShop,
                                 boolean isDefault, boolean isDefaultEquipped) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE VanityItems SET name = ?, description = ?, textureName = ?, type = ?, " +
                " price = ?, isDeletable = ?, isInShop = ?, isDefault = ?, isDefaultEquipped = ? WHERE vanityID = ?"))
        {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, textureName);
            stmt.setInt(4, vanityType.ordinal());
            stmt.setInt(5, price);
            stmt.setBoolean(6, isDeletable);
            stmt.setBoolean(7, isInShop);
            stmt.setBoolean(8, isDefault);
            stmt.setBoolean(9, isDefaultEquipped);

            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
           throw new DatabaseException(e.getMessage());
        }
    }

    /**
     * Adds a new vanity item
     *
     * @param name              the name of the vanity item
     * @param description       the description of the vanity item
     * @param textureName       the texture name of the vanity item
     * @param vanityType        the type of vanity
     * @param isDefaultEquipped
     */
    public void addVanityItem(String name, String description, String textureName,
                              VanityType vanityType, int price, boolean isDeletable,
                              boolean isInShop, boolean isDefault,
                              boolean isDefaultEquipped) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO VanityItems SET name = ?, description = ?, " +
                "textureName = ?, type = ?, price = ?, isDeletable = ?, isInShop = ?,  isDefault = ?,  isDefaultEquipped = ?"))
        {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, textureName);
            stmt.setInt(4, vanityType.ordinal());
            stmt.setInt(5, price);
            stmt.setBoolean(6, isDeletable);
            stmt.setBoolean(7, isInShop);
            stmt.setBoolean(8, isDefault);
            stmt.setBoolean(9, isDefaultEquipped);

            int updated = stmt.executeUpdate();
            if (updated != 1)
            {
                throw new DatabaseException("Could not add new Vanity Item to database (NOT ADDED)");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not add new Vanity Item to database", e);
        }
    }

    /**
     * Static method that returns a list of all the default items currently in the game
     * @return an ArrayList of VanityDTOs that represent the default items
     * @throws DatabaseException should not
     */
    public static ArrayList<VanityDTO> getAllDefaultItems()
            throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM VanityItems WHERE isDefault = 1"))
        {
            try (ResultSet queryResults = stmt.executeQuery())
            {
                return buildDTOs(queryResults);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }

    /**
     * A boolean method that checks whether a given item is deletable or not
     * @param vanityID the item to be checked for deletabilty
     * @return true if it is deletable, false otherwise
     * @throws DatabaseException if the vanity id does not exist
     */
    public static boolean checkDeletability(int vanityID)
            throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();

        try(PreparedStatement stmt = connection.prepareStatement("SELECT isDeletable FROM VanityItems WHERE vanityID = ?"))
        {
            stmt.setInt(1, vanityID);
            try (ResultSet queryResults = stmt.executeQuery())
            {
                queryResults.next();
                if(queryResults.getBoolean("isDeletable"))
                {
                    return true;
                }
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Cannot find entry for vanity item: " + vanityID);
        }
        return false;
    }

    /**
     * Method that returns all the rows where isInShop is 'true', aka it equals 1
     * @return an ArrayList of VanityDTOs representing all in shop items
     * @throws DatabaseException should not.
     */
    public static ArrayList<VanityDTO> getAllInShopItems()
            throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM VanityItems WHERE isInShop = 1"))
        {
            try (ResultSet queryResults = stmt.executeQuery())
            {
                return buildDTOs(queryResults);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }

    /**
     * Refactoring method to reduce code duplication that simply takes a result set and parses it into a list of DTOs
     * @param result the ResultSet to be parsed through
     * @return the list of VanityDTOs
     * @throws SQLException should not.
     */
    private static ArrayList<VanityDTO> buildDTOs(ResultSet result)
            throws SQLException
    {
        ArrayList<VanityDTO> vanityItems = new ArrayList<>();

        while (result.next())
        {
            VanityDTO item = new VanityDTO(result.getInt("vanityID"),
                    result.getString("name"),
                    result.getString("description"),
                    result.getString("textureName"),
                    VanityType.fromInt(result.getInt("type")),
                    result.getInt("price"));
            vanityItems.add(item);
        }
        return vanityItems;
    }

}

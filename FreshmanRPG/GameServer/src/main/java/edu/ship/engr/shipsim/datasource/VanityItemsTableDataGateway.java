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
                "isDefault TINYINT," +
                "isDeletable TINYINT," +
                "isInShop TINYINT)";

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
                        result.getInt("price"),
                        result.getInt("isDefault"),
                        result.getInt("isDeletable"),
                        result.getInt("isInShop"));
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
                                 VanityType vanityType, int price, int isDefault, int isDeletable, int isInShop) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE VanityItems SET name = ?, description = ?, textureName = ?, type = ?, " +
                " price = ?, isDefault = ?, isDeletable = ?, isInShop = ? WHERE vanityID = ?"))
        {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, textureName);
            stmt.setInt(4, vanityType.ordinal());
            stmt.setInt(5, price);
            stmt.setInt(6, isDefault);
            stmt.setInt(7, isDeletable);
            stmt.setInt(8, isInShop);

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
     * @param name        the name of the vanity item
     * @param description the description of the vanity item
     * @param textureName the texture name of the vanity item
     * @param vanityType  the type of vanity
     */
    public void addVanityItem(String name, String description, String textureName,
                              VanityType vanityType, int price, int isDefault, int isDeletable, int isInShop) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO VanityItems SET name = ?, description = ?, " +
                "textureName = ?, type = ?, price = ?, isDefault = ?, isDeletable = ?, isInShop = ?"))
        {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, textureName);
            stmt.setInt(4, vanityType.ordinal());
            stmt.setInt(5, price);
            stmt.setInt(6, isDefault);
            stmt.setInt(7, isDeletable);
            stmt.setInt(8, isInShop);

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

    public static ArrayList<VanityDTO> buildDTOs(ResultSet result)
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
                    result.getInt("price"),
                    result.getInt("isDefault"),
                    result.getInt("isDeletable"),
                    result.getInt("isInShop"));
            vanityItems.add(item);
        }
        return vanityItems;
    }

}

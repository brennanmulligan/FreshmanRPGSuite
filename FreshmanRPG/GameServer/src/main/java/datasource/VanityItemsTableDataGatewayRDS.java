package datasource;

import dataDTO.VanityDTO;
import datatypes.VanityType;

import java.sql.*;
import java.util.ArrayList;

/**
 * The RDS implementation of the row data gateway
 */
public class VanityItemsTableDataGatewayRDS implements VanityItemsTableDataGatewayInterface
{

    private static VanityItemsTableDataGatewayInterface singleton;

    /**
     * Gets the instance of this gateway
     * @return the instance
     * @throws DatabaseException shouldn't
     */
    public static synchronized VanityItemsTableDataGatewayInterface getSingleton() throws DatabaseException
    {
        if (singleton == null)
        {
            singleton = new VanityItemsTableDataGatewayRDS();
        }
        return singleton;
    }

    /**
     * Drop and re-create the VanityItems table this gateway manages
     * @throws DatabaseException if table could not be created
     */
    public static void createTable() throws DatabaseException
    {
        String dropSql = "DROP TABLE IF EXISTS VanityItems, DefaultItems, VanityAwards";
        String vanityItemCreationSql = "CREATE TABLE VanityItems(" +
                "vanityID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "+
                "name VARCHAR(255), " +
                "description VARCHAR(255), " +
                "textureName VARCHAR(255) UNIQUE, " +
                "type INT NOT NULL);";

        Connection connection = DatabaseManager.getSingleton().getConnection();

        try
        {
            PreparedStatement stmt = connection.prepareStatement(dropSql);
            stmt.execute();
            stmt.close();

            stmt = connection.prepareStatement(vanityItemCreationSql);
            stmt.execute();
            stmt.close();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not create the Vanity item table", e);
        }
    }

    /**
     * Gets a vanity item by id
     *
     * @param id the id of the vanity item
     * @return a DTO containing all the info of the vanity item
     */
    @Override
    public VanityDTO getVanityItemByID(int id) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        VanityDTO item = null;
        try
        {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM VanityItems WHERE vanityID = ?");
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            result.next();
            item = new VanityDTO(result.getInt("vanityID"),
                                            result.getString("name"),
                                            result.getString("description"),
                                            result.getString("textureName"),
                                            VanityType.fromInt(result.getInt("type")));

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return item;
    }

    /**
     * @return a list of all vanity items
     */
    @Override
    public ArrayList<VanityDTO> getAllVanityItems() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        ArrayList<VanityDTO> vanityItems = new ArrayList<>();
        try
        {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM VanityItems");
            ResultSet result = stmt.executeQuery();

            while (result.next())
            {
                VanityDTO item = new VanityDTO(result.getInt("vanityID"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("textureName"),
                        VanityType.fromInt(result.getInt("type")));
                vanityItems.add(item);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return vanityItems;
    }

    /**
     * Gets a vanity type by id
     *
     * @param id the id of the vanity item
     * @return the type of the vanity
     */
    @Override
    public VanityType getVanityTypeByID(int id) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        VanityType type = null;
        try
        {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM VanityItems WHERE vanityID = ?");
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            result.next();
            type = VanityType.fromInt(result.getInt("type"));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return type;
    }

    /**
     * Updates a vanity item
     *
     * @param id
     * @param name        the new name
     * @param description the new description
     * @param textureName the new texture name
     * @param vanityType  the new vanity type
     */
    @Override
    public void updateVanityItem(int id, String name, String description, String textureName, VanityType vanityType) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt = connection.prepareStatement("UPDATE VanityItems SET name = ?, description = ?, textureName = ?, type = ? WHERE vanityID = ?");
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, textureName);
            stmt.setInt(4, vanityType.ordinal());
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
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
    @Override
    public void addVanityItem(String name, String description, String textureName, VanityType vanityType) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO VanityItems SET name = ?, description = ?, textureName = ?, type = ?");
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, textureName);
            stmt.setInt(4, vanityType.ordinal());
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
     * Resets the test data
     */
    @Override
    public void resetData()
    {

    }
}

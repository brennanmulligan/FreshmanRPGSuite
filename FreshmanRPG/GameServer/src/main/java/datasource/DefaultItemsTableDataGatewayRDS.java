package datasource;

import dataDTO.VanityDTO;
import datatypes.VanityType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The RDS gateway for the items in the default inventory table
 */
public class DefaultItemsTableDataGatewayRDS implements DefaultItemsTableDataGateway
{
    private static DefaultItemsTableDataGateway singleton;

    /**
     * Gets the instance of this gateway
     * @return the instance
     */
    public static DefaultItemsTableDataGateway getSingleton()
    {
        if (singleton == null)
        {
            singleton = new DefaultItemsTableDataGatewayRDS();
        }
        return singleton;
    }

    /**
     * Drop and re-create the DefaultItems table this gateway manages
     * @throws DatabaseException shouldnt
     */
    public static void createTable() throws DatabaseException
    {
        String dropSql = "DROP TABLE IF EXISTS DefaultItems";
        String defaultItemsCreationSql = "CREATE TABLE DefaultItems(" +
                "defaultID INT NOT NULL UNIQUE," +
                "FOREIGN KEY (defaultID) REFERENCES VanityItems(vanityID) ON DELETE CASCADE );";

        Connection connection = DatabaseManager.getSingleton().getConnection();

        try
        {
            PreparedStatement stmt = connection.prepareStatement(dropSql);
            stmt.execute();
            stmt.close();

            stmt = connection.prepareStatement(defaultItemsCreationSql);
            stmt.execute();
            stmt.close();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not create the Default items table", e);
        }
    }

    /**
     * Gets all the default items stored in the database
     * @return a list of the default vanity items all players have
     * @throws DatabaseException shouldn't
     */
    @Override
    public ArrayList<VanityDTO> getDefaultItems() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        ArrayList<VanityDTO> defaultItems = new ArrayList<>();
        VanityItemsTableDataGatewayInterface vanityItemsGateway = VanityItemsTableDataGatewayRDS.getSingleton();
        try
        {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM DefaultItems");
            ResultSet result = stmt.executeQuery();

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
     * Adds a vanity item to the default item list so it will
     * be in all player's inventories
     * @param defaultID the ID of the vanity item to add
     * @throws DatabaseException shouldn't
     */
    @Override
    public void addDefaultItem(int defaultID) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO DefaultItems SET defaultID = ?");
            stmt.setInt(1, defaultID);
            int updated = stmt.executeUpdate();
            if (updated != 1)
            {
                throw new DatabaseException("Could not add new Default Item to database (NOT ADDED)");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not add new Default Item to database", e);
        }
    }

    /**
     * Removes a vanity item from the default item list so it wont
     * be in all player's inventories anymore
     * @param defaultID the id of the item to be removed
     * @throws DatabaseException shouldnt
     */
    @Override
    public void removeDefaultItem(int defaultID) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM DefaultItems WHERE defaultID = ?");
            stmt.setInt(1, defaultID);
            int updated = stmt.executeUpdate();
            if (updated != 1)
            {
                throw new DatabaseException("Could not remove Default Item from database (NOT ADDED)");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not remove Default Item from database", e);
        }
    }

    /**
     * Resets the data
     * @throws DatabaseException shouldnt
     */
    @Override
    public void resetData()
    {

    }
}

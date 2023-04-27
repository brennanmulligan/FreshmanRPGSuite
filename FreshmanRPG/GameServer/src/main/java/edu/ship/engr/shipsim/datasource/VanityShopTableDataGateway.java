package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VanityShopTableDataGateway
{
    private static VanityShopTableDataGateway singleton;

    /**
     * Gets the instance of this gateway
     *
     * @return the instance
     * @throws DatabaseException shouldn't
     */
    public static synchronized VanityShopTableDataGateway getSingleton() throws DatabaseException
    {
        if (singleton == null)
        {
            singleton = new VanityShopTableDataGateway();
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
        String dropSql = "DROP TABLE IF EXISTS VanityShop";
        String createSql = "CREATE TABLE VanityShop(" +
                "vanityID INT NOT NULL UNIQUE," +
                "price INT NOT NULL," +
                "FOREIGN KEY (vanityID) REFERENCES VanityItems(vanityID) ON DELETE CASCADE);";

        Connection connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(dropSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to drop VanityShop table", e);
        }

        try (PreparedStatement stmt = connection.prepareStatement(createSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create VanityShop table", e);
        }
    }

    /**
     * @return the list of vanity show items
     */
    public ArrayList<VanityDTO> getVanityShopInventory() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        ArrayList<VanityDTO> shopItems = new ArrayList<>();
        VanityItemsTableDataGateway vanityShopGateway = VanityItemsTableDataGateway.getSingleton();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM VanityShop");
             ResultSet result = stmt.executeQuery())
        {
            while (result.next())
            {
                VanityDTO actualItem = vanityShopGateway.getVanityItemByID(result.getInt("vanityID"));
                VanityDTO shopItem = new VanityDTO(actualItem.getID(), actualItem.getName(), actualItem.getDescription(),
                        actualItem.getTextureName(), actualItem.getVanityType(), result.getInt("price")
                );
                shopItems.add(shopItem);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return shopItems;
    }

    public void addItem(int vanityID, int price) throws DatabaseException
    {
        if (price > 0)
        {
            Connection connection = DatabaseManager.getSingleton().getConnection();
            try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO VanityShop SET vanityID = ?, price = ?"))
            {
                stmt.setInt(1, vanityID);
                stmt.setInt(2, price);

                int updated = stmt.executeUpdate();
                if (updated != 1)
                {
                    throw new DatabaseException("Could not add new Vanity Item to shop)");
                }
            }
            catch (SQLException e)
            {
                throw new DatabaseException("Could not add new Vanity Item to shop", e);
            }
        }
        else
        {
            throw new DatabaseException("Could not add new Vanity Item to shop because price < 0");
        }
    }

    public void updateItem(int vanityID, int price) throws DatabaseException
    {
        if (price > 0)
        {
            Connection connection = DatabaseManager.getSingleton().getConnection();
            try (PreparedStatement stmt = connection.prepareStatement("UPDATE VanityShop SET price = ? WHERE vanityID = ?"))
            {
                stmt.setInt(1, price);
                stmt.setInt(2, vanityID);

                int updated = stmt.executeUpdate();
                if (updated != 1)
                {
                    throw new DatabaseException("Could not update shop");
                }
            }
            catch (SQLException e)
            {
                throw new DatabaseException("Could not update shop", e);
            }
        }
        else
        {
            throw new DatabaseException("Could not update shop item because price < 0");
        }
    }

    public void resetData()
    {

    }
}

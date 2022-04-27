package DatabaseBuilders;

import datasource.*;
import datatypes.VanityShopItemsForTest;
import model.OptionsManager;

import java.sql.SQLException;

public class BuildTestVanityShop
{
    /**
     * @param args unused
     * @throws DatabaseException shouldnt
     * @throws SQLException shouldnt
     */
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingMocKDataSource(false);
        OptionsManager.getSingleton().setUsingTestDB(true);
        createVanityShopItemTable();
    }

    /**
     * Create a table of vanity items
     *
     * @throws DatabaseException shouldnt
     */
    public static void createVanityShopItemTable() throws DatabaseException
    {
        VanityShopTableDataGatewayRDS.createTable();
        VanityShopTableDataGateway gateway = VanityShopTableDataGatewayRDS.getSingleton();
        for (VanityShopItemsForTest v : VanityShopItemsForTest.values())
        {
            gateway.addItem(v.getVanityID(), v.getPrice());
        }
    }
}

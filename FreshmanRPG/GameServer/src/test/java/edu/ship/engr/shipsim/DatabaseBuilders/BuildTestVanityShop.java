package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.VanityShopTableDataGateway;
import edu.ship.engr.shipsim.datatypes.VanityShopItemsForTest;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.sql.SQLException;

public class BuildTestVanityShop
{
    /**
     * @param args unused
     * @throws DatabaseException shouldnt
     * @throws SQLException      shouldnt
     */
    public static void main(String[] args) throws DatabaseException, SQLException
    {
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
        System.out.println("Building the VanityShop Table");
        VanityShopTableDataGateway.createTable();
        VanityShopTableDataGateway gateway = VanityShopTableDataGateway.getSingleton();

        ProgressBar bar = new ProgressBar(VanityShopItemsForTest.values().length);
        for (VanityShopItemsForTest v : VanityShopItemsForTest.values())
        {
            gateway.addItem(v.getVanityID(), v.getPrice());

            bar.update();
        }
    }
}

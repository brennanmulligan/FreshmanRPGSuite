package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.VanityInventoryTableDataGateway;
import edu.ship.engr.shipsim.datatypes.VanityInventoryItemsForTest;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.sql.SQLException;

/**
 * Builds the Vanity inventory portion of the database
 */
public class BuildTestVanityInventory
{
    /**
     * @param args unused
     * @throws DatabaseException shouldnt
     * @throws SQLException      shouldnt
     */
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createVanityInventoryTable();
    }

    /**
     * Create a table of vanity inventory
     *
     * @throws DatabaseException shouldn't
     */
    private static void createVanityInventoryTable() throws DatabaseException
    {
        System.out.println("Building the VanityInventory Table");
        VanityInventoryTableDataGateway.createTable();
        VanityInventoryTableDataGateway gateway = VanityInventoryTableDataGateway.getSingleton();

        ProgressBar bar = new ProgressBar(VanityInventoryItemsForTest.values().length);
        for (VanityInventoryItemsForTest v : VanityInventoryItemsForTest.values())
        {
            gateway.addItemToInventory(v.getPlayerID(), v.getVanityID(), v.getIsWearing());

            bar.update();
        }
    }
}

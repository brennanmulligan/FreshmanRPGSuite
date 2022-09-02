package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DefaultItemsTableDataGateway;
import edu.ship.engr.shipsim.datatypes.DefaultItemsForTest;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.sql.SQLException;

public class BuildTestDefaultItems
{
    /**
     * @param args unused
     * @throws DatabaseException shouldnt
     * @throws SQLException      shouldnt
     */
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createDefaultItemsTable();
    }

    private static void createDefaultItemsTable() throws DatabaseException
    {
        System.out.println("Building the DefaultItems Table");
        DefaultItemsTableDataGateway.createTable();
        DefaultItemsTableDataGateway gateway = DefaultItemsTableDataGateway.getSingleton();

        ProgressBar bar = new ProgressBar(DefaultItemsForTest.values().length);
        for (DefaultItemsForTest v : DefaultItemsForTest.values())
        {
            gateway.addDefaultItem(v.getDefaultID(), v.getDefaultWearing());

            bar.update();
        }
    }
}

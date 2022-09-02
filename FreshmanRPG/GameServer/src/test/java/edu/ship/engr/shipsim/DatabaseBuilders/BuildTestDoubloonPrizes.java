package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DoubloonPrizesTableDataGateway;
import edu.ship.engr.shipsim.datatypes.DoubloonPrizesForTest;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.sql.SQLException;

/**
 * @author Mina, Christian
 */
public class BuildTestDoubloonPrizes
{
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createDoubloonPrizesTable();
    }

    /**
     * Create a table of levels
     */
    private static void createDoubloonPrizesTable() throws DatabaseException
    {
        System.out.println("Building the DoubloonPrizes Table");
        DoubloonPrizesTableDataGateway.createTable();
        DoubloonPrizesTableDataGateway gateway = DoubloonPrizesTableDataGateway.getSingleton();

        ProgressBar bar = new ProgressBar(DoubloonPrizesForTest.values().length);
        for (DoubloonPrizesForTest prize : DoubloonPrizesForTest.values())
        {
            gateway.createRow(prize.getName(), prize.getCost(), prize.getDescription());

            bar.update();
        }
    }
}

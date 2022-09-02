package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.VanityAwardsTableDataGateway;
import edu.ship.engr.shipsim.datatypes.VanityAwardsForTest;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.sql.SQLException;

public class BuildTestVanityAwards
{
    /**
     * @param args unused
     * @throws DatabaseException shouldnt
     * @throws SQLException      shouldnt
     */
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createVanityAwardsTable();
    }

    /**
     * Create a table of vanity inventory
     *
     * @throws DatabaseException shouldnt
     */
    private static void createVanityAwardsTable() throws DatabaseException
    {
        System.out.println("Building the VanityAwards Table");
        VanityAwardsTableDataGateway.createTable();
        VanityAwardsTableDataGateway gateway = VanityAwardsTableDataGateway.getSingleton();

        ProgressBar bar = new ProgressBar(VanityAwardsForTest.values().length);
        for (VanityAwardsForTest v : VanityAwardsForTest.values())
        {
            gateway.addVanityAward(v.getQuestID(), v.getVanityID());

            bar.update();
        }
    }
}

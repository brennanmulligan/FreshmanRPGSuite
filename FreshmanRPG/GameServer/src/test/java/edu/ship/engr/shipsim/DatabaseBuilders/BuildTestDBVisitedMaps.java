package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.VisitedMapsGateway;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.util.Arrays;

/**
 * Build the visited maps database table
 */
public class BuildTestDBVisitedMaps
{
    /**
     * @param args unused
     * @throws DatabaseException if database access issue
     */
    public static void main(String[] args) throws DatabaseException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createVisitedMapsTable();
    }

    private static void createVisitedMapsTable() throws DatabaseException
    {
        System.out.println();
        VisitedMapsGateway.createTable();

        int totalMaps = Arrays.stream(PlayersForTest.values()).map(p -> p.getMapsVisited().size()).reduce(0, Integer::sum);

        ProgressBar bar = new ProgressBar(totalMaps);
        for (PlayersForTest p : PlayersForTest.values())
        {
            for (String mapTitle : p.getMapsVisited())
            {
                new VisitedMapsGateway(p.getPlayerID(), mapTitle);

                bar.update();
            }
        }
    }
}

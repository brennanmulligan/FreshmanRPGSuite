package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.PlayerLoginRowDataGateway;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.OptionsManager;

/**
 * Builds the login portion of the database
 *
 * @author Merlin
 */
public class BuildTestDBPlayerLogin
{

    /**
     * @param args unused
     * @throws DatabaseException shouldn't
     */
    public static void main(String[] args) throws DatabaseException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createPlayerLoginTable();

    }

    private static void createPlayerLoginTable() throws DatabaseException
    {
        PlayerLoginRowDataGateway.createPlayerLoginTable();

        for (PlayersForTest p : PlayersForTest.values())
        {
            new PlayerLoginRowDataGateway(p.getPlayerID(), p.getPlayerName(), p.getPlayerPassword());
        }

    }

}

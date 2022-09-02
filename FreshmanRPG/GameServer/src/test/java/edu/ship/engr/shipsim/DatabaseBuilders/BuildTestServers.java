package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ServerRowDataGateway;
import edu.ship.engr.shipsim.datatypes.ServersForTest;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.sql.SQLException;

/**
 * Builds the Level portion of the database
 *
 * @author Merlin
 */
public class BuildTestServers
{

    /**
     * @param args unused
     * @throws DatabaseException shouldn't
     * @throws SQLException      shouldn'tupdateFriendButton(false);
     */
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createServerTable();
    }

    /**
     * Create a table of levels
     */
    private static void createServerTable() throws DatabaseException
    {
        System.out.println("Building the Server Table");
        ServerRowDataGateway.createTable();

        ProgressBar bar = new ProgressBar(ServersForTest.values().length);
        for (ServersForTest server : ServersForTest.values())
        {
            new ServerRowDataGateway(server.getMapName(), server.getHostName(), server.getPortNumber(), server.getMapTitle(), server.getTeleportPositionX(), server.getTeleportPositionY());

            bar.update();
        }
    }
}

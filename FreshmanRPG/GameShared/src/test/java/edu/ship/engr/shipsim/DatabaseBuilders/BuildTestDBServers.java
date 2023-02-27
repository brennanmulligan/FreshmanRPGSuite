package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DatabaseManager;
import edu.ship.engr.shipsim.datasource.ServerRowDataGateway;
import edu.ship.engr.shipsim.datatypes.ServersForTest;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.sql.SQLException;

/**
 * Builds the Player portion of the database
 *
 * @author Merlin
 */
public class BuildTestDBServers
{

    /**
     * @param args unused
     * @throws DatabaseException shouldn't
     * @throws SQLException      shouldn't
     */
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createServerTable();
    }

    private static void createServerTable() throws DatabaseException
    {
        ProgressBar bar = new ProgressBar(ServersForTest.values().length);

        DatabaseManager.getSingleton().getConnection();
        ServerRowDataGateway.createTable();
        for (ServersForTest p : ServersForTest.values())
        {
            new ServerRowDataGateway(p.getMapName(), p.getHostName(), p.getPortNumber(), p.getMapTitle(), p.getTeleportPositionX(), p.getTeleportPositionY(), p.isQuiet());

            bar.update();
        }

    }
}

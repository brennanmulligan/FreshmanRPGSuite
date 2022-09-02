package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.model.reports.LoginSuccessfulReport;

/**
 * @author Merlin
 */
public class LoginPlayerManager
{
    /**
     *
     */
    final String DEFAULT_MAP = "quad.tmx";

    private static LoginPlayerManager singleton;

    /**
     * @return the only PlayerManger in the system
     */
    public synchronized static LoginPlayerManager getSingleton()
    {
        if (singleton == null)
        {
            singleton = new LoginPlayerManager();
        }
        return singleton;
    }

    /**
     * reset the singleton for testing purposes.
     */
    public static void resetSingleton()
    {
        singleton = null;
    }

    private int numberOfPlayers;

    private LoginPlayerManager()
    {

    }

    /**
     * Attempt to login to the system. Credentials will be checked and
     * appropriate reports will be made
     *
     * @param playerName the player's name
     * @param password   the password
     * @return a report giving the instructions for how the client should
     * connect to an area server
     * @throws LoginFailedException for database errors or invalid credentials
     */
    public LoginSuccessfulReport login(String playerName, String password) throws LoginFailedException
    {

        PlayerLogin pl = null;
        try
        {
            pl = new PlayerLogin(playerName, password);

            numberOfPlayers++;
            PlayerConnection pp = new PlayerConnection(pl.getPlayerID());

            String server;
            int port;

            MapToServerMapping mapping = new MapToServerMapping(pp.getMapName());
            server = mapping.getHostName();
            port = mapping.getPortNumber();
            return new LoginSuccessfulReport(pl.getPlayerID(), server, port, pp.generatePin());
        }
        catch (DatabaseException e)
        {
            throw new LoginFailedException(e.getSimpleDescription());
        }

    }

    /**
     * @return the number of players that have logged in
     */
    public int getNumberOfPlayers()
    {
        return numberOfPlayers;
    }
}

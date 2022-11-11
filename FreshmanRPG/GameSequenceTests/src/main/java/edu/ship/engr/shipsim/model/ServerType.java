package edu.ship.engr.shipsim.model;

/**
 * The kinds of servers in the system
 *
 * @author Merlin
 */
public enum ServerType
{
    /**
     * the client that a player making the request is using
     */
    THIS_PLAYER_CLIENT(false),

    /**
     * the client of another player who should participate even though they did
     * not initiate the sequence
     */
    OTHER_CLIENT(false),

    /**
     * the server that manages logins
     */
    LOGIN_SERVER(false),

    /**
     * An area server
     */
    AREA_SERVER(true),

    /**
     * A Restful Server
     */
    RESTFUL_SERVER(false),

    /**
     * the standalone management app
     */
    MANAGER(false);

    private boolean oneToManyConnections;

    /**
     * @return true if one incoming message may cause outgoing messages to other receivers
     */
    public boolean supportsOneToManyConnections()
    {
        return oneToManyConnections;
    }

    ServerType(boolean oneToManyConnections)
    {
        this.oneToManyConnections = oneToManyConnections;
    }
}
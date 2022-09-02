package edu.ship.engr.shipsim.communication;

/**
 * A problem with the communication between the client and the game server
 *
 * @author merlin
 */
public class CommunicationException extends Exception
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Just remember the string
     *
     * @param string a message describing what went wrong
     */
    public CommunicationException(String string)
    {
        super(string);
    }

}

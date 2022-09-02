package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * If no buff is remaining alert player.
 *
 * @author Aaron Gerber
 * @author Stephen Clabaugh
 */
public class NoMoreBuffMessage implements Message, Serializable
{
    private static final long serialVersionUID = 1L;

    private final int playerID;

    /**
     * Constructor
     *
     * @param playerID - the id of the player
     */
    public NoMoreBuffMessage(int playerID)
    {
        this.playerID = playerID;
    }

    /**
     * Instance variable getter
     *
     * @return playerID
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * Standard toString for the message class
     */
    @Override
    public String toString()
    {
        return "NoMoreBuffMessage: No remaining buff for playerID = " + playerID;
    }
}

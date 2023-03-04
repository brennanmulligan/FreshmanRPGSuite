package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * If no buff is remaining alert player.
 *
 * @author Aaron Gerber
 * @author Stephen Clabaugh
 */
public class NoMoreBuffMessage extends Message implements Serializable
{
    private static final long serialVersionUID = 1L;


    /**
     * Constructor
     *
     * @param playerID - the id of the player
     */
    public NoMoreBuffMessage(int playerID, boolean quietMessage)
    {
        super(playerID, quietMessage);
    }

    /**
     * Standard toString for the message class
     */
    @Override
    public String toString()
    {
        return "NoMoreBuffMessage: No remaining buff for playerID = " + relevantPlayerID;
    }
}

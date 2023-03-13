package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * Sent to all clients when a player disconnects from an area server
 *
 * @author nhydock
 */
public class PlayerLeaveMessage extends Message implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param playerID the unique ID of the player
     */
    public PlayerLeaveMessage(int playerID, boolean quietMessage)
    {
        super(playerID, quietMessage);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        PlayerLeaveMessage that = (PlayerLeaveMessage) o;

        return relevantPlayerID == that.relevantPlayerID;
    }

    @Override
    public int hashCode()
    {
        return relevantPlayerID;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "PlayerLeaveMessage: playerID = " + relevantPlayerID;
    }
}

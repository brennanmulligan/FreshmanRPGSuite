package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * Sent to all clients when a player disconnects from an area server
 *
 * @author nhydock
 */
public class PlayerLeaveMessage implements Message, Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final int playerID;

    /**
     * @param playerID the unique ID of the player
     */
    public PlayerLeaveMessage(int playerID)
    {
        this.playerID = playerID;
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

        return playerID == that.playerID;
    }

    @Override
    public int hashCode()
    {
        return playerID;
    }

    /**
     * get this player's unique ID
     *
     * @return the player's ID
     */
    public int getPlayerID()
    {
        return this.playerID;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "PlayerLeaveMessage: playerID = " + playerID;
    }
}

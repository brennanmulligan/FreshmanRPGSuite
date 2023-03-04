package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.Position;

import java.io.Serializable;

/**
 * Encodes the fact that a player has moved to a given location
 *
 * @author merlin
 */
public class OtherPlayerMovedMessage extends Message implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final Position position;

    /**
     * @param playerID The player who moved
     * @param p        Where the player moved to
     */
    public OtherPlayerMovedMessage(int playerID, boolean quietMessage, Position p)
    {
        super(playerID, quietMessage);
        this.position = p;
    }


    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + relevantPlayerID;
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof OtherPlayerMovedMessage))
        {
            return false;
        }
        OtherPlayerMovedMessage other = (OtherPlayerMovedMessage) obj;
        if (relevantPlayerID != other.relevantPlayerID)
        {
            return false;
        }
        if (position == null)
        {
            if (other.position != null)
            {
                return false;
            }
        }
        else if (!position.equals(other.position))
        {
            return false;
        }
        return true;
    }

    /**
     * @return the position to which the player moved
     */
    public Position getPosition()
    {
        return position;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "Other player moved message: playerID = " + relevantPlayerID + ", position = " + position;
    }

}

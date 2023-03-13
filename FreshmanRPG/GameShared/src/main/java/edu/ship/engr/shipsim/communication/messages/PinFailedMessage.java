package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * The message that goes from the main server to the client when the user has a
 * pin error
 * <p>
 * Matt and Andy
 */
public class PinFailedMessage extends Message implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param playerID the unique ID of the player who tried to connect with an
     *                 invalid pin
     */
    public PinFailedMessage(int playerID, boolean quietMessage)
    {
        super(playerID, quietMessage);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        PinFailedMessage other = (PinFailedMessage) obj;
        if (relevantPlayerID != other.relevantPlayerID)
        {
            return false;
        }
        return true;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + relevantPlayerID;
        return result;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Pin failed message for player #" + relevantPlayerID;
    }

}

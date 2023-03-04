package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * This message is sent to the client when an interaction is not allowed.
 * Holds the playerID
 *
 * @author ag0612
 * @author jk1964
 * @author tc9538
 */
public class InteractionDeniedMessage extends Message implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Constructor - sets the playerID
     *
     * @param playerID - ID of the player close enough to interact with the object
     */
    public InteractionDeniedMessage(int playerID, boolean quietMessage)
    {
        super(playerID, quietMessage);
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
     * allows object comparison
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
        InteractionDeniedMessage other = (InteractionDeniedMessage) obj;
        if (relevantPlayerID != other.relevantPlayerID)
        {
            return false;
        }
        return true;
    }


    /**
     * Prints out the message when the object is unavailable
     *
     * @return a string of message
     */
    @Override
    public String toString()
    {
        return "ObjectUnavailableMessage: playerID = " + relevantPlayerID;
    }


}

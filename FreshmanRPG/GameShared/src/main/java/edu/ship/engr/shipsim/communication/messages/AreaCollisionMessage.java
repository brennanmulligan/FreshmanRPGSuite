package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * @author nhydock
 */
public class AreaCollisionMessage extends Message implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final String areaName;

    /**
     * @param playerID The player who moved
     * @param areaName The name of the area the player has stepped into
     */
    public AreaCollisionMessage(int playerID, boolean quietMessage, String areaName)
    {
        super(playerID, quietMessage);
        this.areaName = areaName;
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
        result = prime * result + ((areaName == null) ? 0 : areaName.hashCode());
        return result;
    }

    /**
     * @see Object#equals(Object)
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
        if (!(obj instanceof AreaCollisionMessage))
        {
            return false;
        }
        AreaCollisionMessage other = (AreaCollisionMessage) obj;
        if (relevantPlayerID != other.relevantPlayerID)
        {
            return false;
        }
        if (!areaName.equals(other.areaName))
        {
            return false;
        }
        return true;
    }

    /**
     * @return the position to which the player moved
     */
    public String getAreaName()
    {
        return areaName;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "Collision Message: playerID = " + relevantPlayerID + " collided with object = " + areaName;
    }

}

package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * @author nhydock
 */
public class AreaCollisionMessage implements Message, Serializable
{
    private static final long serialVersionUID = 1L;

    private final int playerID;
    private final String areaName;

    /**
     * @param playerID The player who moved
     * @param areaName The name of the area the player has stepped into
     */
    public AreaCollisionMessage(int playerID, String areaName)
    {
        this.playerID = playerID;
        this.areaName = areaName;
    }

    /**
     * @return the player who moved
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + playerID;
        result = prime * result + ((areaName == null) ? 0 : areaName.hashCode());
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
        if (!(obj instanceof AreaCollisionMessage))
        {
            return false;
        }
        AreaCollisionMessage other = (AreaCollisionMessage) obj;
        if (playerID != other.playerID)
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
        return "Collision Message: playerID = " + playerID + " collided with object = " + areaName;
    }

}

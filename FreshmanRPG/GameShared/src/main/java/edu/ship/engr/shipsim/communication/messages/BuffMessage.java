package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * Experience point buffer message
 *
 * @author Truc Chau
 */
public class BuffMessage extends Message implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final int experiencePointPool;

    /**
     * Constructor
     *
     * @param playerID         - The id of the player
     * @param experiencePoints - The number of Bonus Exp Points
     */
    public BuffMessage(int playerID, boolean quietMessage, int experiencePoints)
    {
        super(playerID, quietMessage);
        this.experiencePointPool = experiencePoints;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + experiencePointPool;
        result = prime * result + relevantPlayerID;
        return result;
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
        BuffMessage other = (BuffMessage) obj;
        if (experiencePointPool != other.experiencePointPool)
        {
            return false;
        }
        if (relevantPlayerID != other.relevantPlayerID)
        {
            return false;
        }
        return true;
    }

    /**
     * Returns player's experience points
     *
     * @return player's experience points
     */
    public int getExperiencePointPool()
    {
        return experiencePointPool;
    }
}

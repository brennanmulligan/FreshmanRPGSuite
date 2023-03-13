package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;
import java.util.Date;

/**
 * time to level up deadline message
 *
 * @author Chris, Evan, Marty
 */
public class TimeToLevelUpDeadlineMessage extends Message implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Date timeToDeadline;
    private String nextLevel;

    /**
     * @param playerID       id of the player
     * @param timeToDeadline time the user has to level up before being
     *                       penalized
     * @param nextLevel      next level the player needs to get to
     */
    public TimeToLevelUpDeadlineMessage(int playerID, boolean quietMessage, Date timeToDeadline, String nextLevel)
    {
        super(playerID, quietMessage);
        this.timeToDeadline = timeToDeadline;
        this.nextLevel = nextLevel;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nextLevel == null) ? 0 : nextLevel.hashCode());
        result = prime * result + relevantPlayerID;
        result = prime * result + ((timeToDeadline == null) ? 0 : timeToDeadline.hashCode());
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
        TimeToLevelUpDeadlineMessage other = (TimeToLevelUpDeadlineMessage) obj;
        if (nextLevel == null)
        {
            if (other.nextLevel != null)
            {
                return false;
            }
        }
        else if (!nextLevel.equals(other.nextLevel))
        {
            return false;
        }
        if (relevantPlayerID != other.relevantPlayerID)
        {
            return false;
        }
        if (timeToDeadline == null)
        {
            if (other.timeToDeadline != null)
            {
                return false;
            }
        }
        else if (!timeToDeadline.equals(other.timeToDeadline))
        {
            return false;
        }
        return true;
    }

    /**
     * @return The time the user has to level up before being penalized
     */
    public Date getTimeToDeadline()
    {
        return timeToDeadline;
    }

    /**
     * @return The next level the player needs to get to
     */
    public String getNextLevel()
    {
        return nextLevel;
    }

}

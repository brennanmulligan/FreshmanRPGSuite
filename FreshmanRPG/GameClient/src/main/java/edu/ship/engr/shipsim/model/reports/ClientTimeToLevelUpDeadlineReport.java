package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

import java.util.Date;

/**
 * @author Evan, Chris, Marty
 */
public final class ClientTimeToLevelUpDeadlineReport implements Report
{
    private int playerID;
    private Date timeToDeadline;
    private String nextLevel;

    /**
     * @param playerID       id of the player
     * @param timeToDeadline time the user has to level up before being penalized
     * @param nextLevel      next level the player needs to get to
     */
    public ClientTimeToLevelUpDeadlineReport(int playerID, Date timeToDeadline, String nextLevel)
    {
        this.playerID = playerID;
        this.timeToDeadline = timeToDeadline;
        this.nextLevel = nextLevel;


    }


    /**
     * @return The player id
     */
    public int getPlayerID()
    {
        return playerID;
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

    /**
     * Override the default hashCode() so that instances of this class can be
     * properly compared with one another.
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + playerID;
        result = prime * result + ((timeToDeadline == null) ? 0 : timeToDeadline.hashCode());
        result = prime * result + ((nextLevel == null) ? 0 : nextLevel.hashCode());
        return result;
    }

    /**
     * Override the default equals() so that instances of this class can be
     * properly compared.
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
        ClientTimeToLevelUpDeadlineReport other = (ClientTimeToLevelUpDeadlineReport) obj;
        if (playerID == 0)
        {
            if (other.playerID != 0)
            {
                return false;
            }
        }
        else if (playerID != other.playerID)
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
        return true;
    }
}


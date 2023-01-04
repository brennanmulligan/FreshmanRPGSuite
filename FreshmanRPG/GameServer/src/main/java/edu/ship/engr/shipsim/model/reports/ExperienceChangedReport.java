package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datasource.LevelRecord;
import edu.ship.engr.shipsim.model.Report;

/**
 * The ExperienceChangedReport class
 *
 * @author Olivia
 * @author LaVonne
 */
public final class ExperienceChangedReport implements Report
{

    private final int experiencePoints;

    private final LevelRecord record;

    private final int playerID;

    /**
     * @param playerID         the player's id
     * @param experiencePoints experience points of the player
     * @param record           level record of the player
     */
    public ExperienceChangedReport(int playerID, int experiencePoints, LevelRecord record)
    {
        this.experiencePoints = experiencePoints;
        this.record = record;
        this.playerID = playerID;
    }

    /**
     * Gets player's current experience points
     *
     * @return experiencePoints
     */
    public int getExperiencePoints()
    {
        return experiencePoints;
    }

    /**
     * Returns the player's LevelRecord
     *
     * @return the record
     */
    public LevelRecord getRecord()
    {
        return record;
    }

    /**
     * Returns the player's ID
     *
     * @return playerID
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + experiencePoints;
        result = prime * result + playerID;
        result = prime * result + ((record == null) ? 0 : record.hashCode());
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
        ExperienceChangedReport other = (ExperienceChangedReport) obj;
        if (experiencePoints != other.experiencePoints)
        {
            return false;
        }
        if (playerID != other.playerID)
        {
            return false;
        }
        if (record == null)
        {
            if (other.record != null)
            {
                return false;
            }
        }
        else if (!record.equals(other.record))
        {
            return false;
        }
        return true;
    }

}

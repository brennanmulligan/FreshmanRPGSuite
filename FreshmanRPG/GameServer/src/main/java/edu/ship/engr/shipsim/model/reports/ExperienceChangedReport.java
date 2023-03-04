package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datasource.LevelRecord;

import java.util.Objects;

/**
 * The ExperienceChangedReport class
 *
 * @author Olivia
 * @author LaVonne
 */
public final class ExperienceChangedReport extends SendMessageReport
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
        super(playerID, true);
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof ExperienceChangedReport that))
        {
            return false;
        }
        return getExperiencePoints() == that.getExperiencePoints() &&
                getPlayerID() == that.getPlayerID() &&
                Objects.equals(getRecord(), that.getRecord()) &&
                this.getRelevantPlayerID() == that.getRelevantPlayerID() &&
                this.isQuiet() == that.isQuiet();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getExperiencePoints(), getRecord(), getPlayerID(), getRelevantPlayerID(), isQuiet());
    }
}

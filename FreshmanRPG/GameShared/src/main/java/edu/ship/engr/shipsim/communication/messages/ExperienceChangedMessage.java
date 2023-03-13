package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datasource.LevelRecord;

import java.io.Serializable;

/**
 * ExperienceChangeMessage class
 *
 * @author Olivia
 * @author LaVonne
 */
public class ExperienceChangedMessage extends Message implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int experiencePoints;
    private LevelRecord level;

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        ExperienceChangedMessage that = (ExperienceChangedMessage) o;

        if (experiencePoints != that.experiencePoints)
        {
            return false;
        }
        if (relevantPlayerID != that.relevantPlayerID)
        {
            return false;
        }
        return level.equals(that.level);
    }

    @Override
    public int hashCode()
    {
        int result = experiencePoints;
        result = 31 * result + level.hashCode();
        result = 31 * result + relevantPlayerID;
        return result;
    }

    /**
     * @param playerID         the id of the plyaer
     * @param experiencePoints the amount of experience points the player has
     * @param levelRecord      the id of the player
     */
    public ExperienceChangedMessage(int playerID, boolean quietMessage, int experiencePoints, LevelRecord levelRecord)
    {
        super(playerID, quietMessage);
        this.experiencePoints = experiencePoints;
        this.level = levelRecord;
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
     * Gets player's level
     *
     * @return level
     */
    public LevelRecord getLevel()
    {
        return level;
    }

}

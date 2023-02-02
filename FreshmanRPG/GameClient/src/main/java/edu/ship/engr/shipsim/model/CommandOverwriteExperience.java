/**
 *
 */
package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.communication.messages.InitializeThisClientsPlayerMessage;
import edu.ship.engr.shipsim.datasource.LevelRecord;

/**
 * @author nk3668
 */
public class CommandOverwriteExperience extends Command
{
    private final int experience;
    private final LevelRecord record;

    /**
     * constructor for the command
     *
     * @param msg InitializeClientsPlayerMessage which contains all the data to
     *            initialize this client player
     */
    public CommandOverwriteExperience(InitializeThisClientsPlayerMessage msg)
    {
        this.experience = msg.getExperiencePts();
        this.record = msg.getLevel();
    }

    /**
     * @param experiencePoints player's experience points
     * @param record           level record of the player
     */
    public CommandOverwriteExperience(int experiencePoints, LevelRecord record)
    {
        this.record = record;
        this.experience = experiencePoints;
    }

    /**
     * Overwrites the player's experience
     */
    @Override
    void execute()
    {
        ThisClientsPlayer thisClientsPlayer = ClientPlayerManager.getSingleton().getThisClientsPlayer();
        thisClientsPlayer.overwriteExperiencePoints(experience, record);
    }

    /**
     * retrieves the experience
     *
     * @return experience the client player's current experience
     */
    public int getExperiencePoints()
    {
        return experience;
    }

    /**
     * retrieves the LevelRecord
     *
     * @return record the client player's current Level Record
     */
    public LevelRecord getLevelRecord()
    {
        return record;
    }

}

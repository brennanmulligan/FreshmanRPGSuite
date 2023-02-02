package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;

/**
 * @author Ryan
 */
public class CommandObjectiveNotificationComplete extends Command
{

    private final int playerID;
    private final int questID;
    private final int objectiveID;

    /**
     * Constructor
     *
     * @param playerID    id of the player
     * @param questID     id of the quest
     * @param objectiveID id of the objective
     */
    public CommandObjectiveNotificationComplete(int playerID, int questID, int objectiveID)
    {
        this.playerID = playerID;
        this.questID = questID;
        this.objectiveID = objectiveID;
    }

    /**
     * @see Command#execute()
     */
    @Override
    void execute()
    {
        try
        {
            QuestManager.getSingleton().turnOffNotification(playerID, questID, objectiveID);
        }
        catch (DatabaseException | IllegalQuestChangeException | IllegalObjectiveChangeException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @return id of the player
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return id of the quest
     */
    public int getQuestID()
    {
        return questID;
    }

    /**
     * @return id of the objective
     */
    public int getObjectiveID()
    {
        return objectiveID;
    }

}

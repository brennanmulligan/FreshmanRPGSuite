package edu.ship.engr.shipsim.view.screen.popup;

import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandObjectiveNotificationComplete;

/**
 * Handles behavior for objectives being completed
 *
 * @author Ryan
 */
public class ObjectiveNotificationCompleteBehavior implements PopupBehavior
{

    private int playerID;
    private int questID;
    private int objectiveID;

    /**
     * @param playerID    the id of the player
     * @param questID     the id of the quest
     * @param objectiveID the id of the objective
     */
    public ObjectiveNotificationCompleteBehavior(int playerID, int questID, int objectiveID)
    {
        this.playerID = playerID;
        this.questID = questID;
        this.objectiveID = objectiveID;
    }

    /**
     * @see PopupBehavior#clicked()
     * <p>
     * Create a CommandObjectiveNotificationComplete command
     */
    @Override
    public void clicked()
    {
        CommandObjectiveNotificationComplete cmd = new CommandObjectiveNotificationComplete(playerID, questID, objectiveID);
        ClientModelFacade.getSingleton().queueCommand(cmd);
    }

    /**
     * @return the player's ID
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return the quest ID
     */
    public int getQuestID()
    {
        return questID;
    }

    /**
     * @return the objective ID
     */
    public int getObjectiveID()
    {
        return objectiveID;
    }
}

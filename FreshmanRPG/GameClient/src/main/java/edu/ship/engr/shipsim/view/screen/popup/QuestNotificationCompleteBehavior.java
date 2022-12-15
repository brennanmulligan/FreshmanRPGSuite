package edu.ship.engr.shipsim.view.screen.popup;

import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandQuestNotificationComplete;

/**
 * Handles behavior for objective being completed
 *
 * @author Ryan
 */
public class QuestNotificationCompleteBehavior implements PopupBehavior
{

    private int playerID;
    private int questID;

    /**
     * @param playerID the id of the player
     * @param questID  the id of the quest
     */
    public QuestNotificationCompleteBehavior(int playerID, int questID)
    {
        this.playerID = playerID;
        this.questID = questID;
    }

    /**
     * @see PopupBehavior#clicked()
     * <p>
     * Create a CommandObjectiveNotificationComplete command
     */
    @Override
    public void clicked()
    {
        CommandQuestNotificationComplete cmd = new CommandQuestNotificationComplete(playerID, questID);
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
}

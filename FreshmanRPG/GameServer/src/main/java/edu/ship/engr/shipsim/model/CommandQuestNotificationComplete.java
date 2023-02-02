package edu.ship.engr.shipsim.model;

/**
 * @author Ryan
 */
public class CommandQuestNotificationComplete extends Command
{

    private final int playerID;
    private final int questID;

    /**
     * Constructor
     *
     * @param playerID id of the player
     * @param questID  id of the quest
     */
    public CommandQuestNotificationComplete(int playerID, int questID)
    {
        this.playerID = playerID;
        this.questID = questID;
    }

    /**
     * @see Command#execute()
     */
    @Override
    void execute()
    {
        QuestManager.getSingleton().turnOffQuestNotification(playerID, questID);
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
}

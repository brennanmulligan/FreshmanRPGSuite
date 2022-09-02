package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * @author Ryan
 */
public class QuestNotificationCompleteMessage implements Message, Serializable
{

    private int playerID;
    private int questID;

    /**
     * @param playerID id of the player
     * @param questID  id of the quest
     */
    public QuestNotificationCompleteMessage(int playerID, int questID)
    {
        this.playerID = playerID;
        this.questID = questID;
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

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

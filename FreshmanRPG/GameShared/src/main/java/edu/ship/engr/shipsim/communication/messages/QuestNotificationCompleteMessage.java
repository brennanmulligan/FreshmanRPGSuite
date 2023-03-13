package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * @author Ryan
 */
public class QuestNotificationCompleteMessage extends Message implements Serializable
{

    private int questID;

    /**
     * @param playerID id of the player
     * @param questID  id of the quest
     */
    public QuestNotificationCompleteMessage(int playerID, boolean quietMessage, int questID)
    {
        super(playerID, quietMessage);
        this.questID = questID;
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return id of the quest
     */
    public int getQuestID()
    {
        return questID;
    }
}

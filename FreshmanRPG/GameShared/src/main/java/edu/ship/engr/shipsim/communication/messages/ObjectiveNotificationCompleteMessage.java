package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * @author Ryan
 */
public class ObjectiveNotificationCompleteMessage extends Message implements Serializable
{


    private int questID;
    private int objectiveID;

    /**
     * @param playerID    id of the player
     * @param questID     id of the quest
     * @param objectiveID id of the objective
     */
    public ObjectiveNotificationCompleteMessage(int playerID, boolean quietMessage, int questID, int objectiveID)
    {
        super(playerID, quietMessage);
        this.questID = questID;
        this.objectiveID = objectiveID;
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

    /**
     * @return id of the objective
     */
    public int getObjectiveID()
    {
        return objectiveID;
    }

}

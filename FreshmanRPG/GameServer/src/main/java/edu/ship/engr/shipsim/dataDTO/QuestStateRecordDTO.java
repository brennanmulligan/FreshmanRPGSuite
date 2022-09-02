package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.datatypes.QuestStateEnum;

/**
 * A data transfer object that contains the state of a quest for a player
 *
 * @author Merlin
 */
public class QuestStateRecordDTO
{

    private int playerID;

    private int questID;

    private QuestStateEnum state;

    private boolean needingNotification;

    /**
     * @param playerID            the player's unique ID
     * @param questID             the quest's unique ID
     * @param state               this player's state for the given quest
     * @param needingNotification true if the player should be notified about
     *                            this state
     */
    public QuestStateRecordDTO(int playerID, int questID, QuestStateEnum state, boolean needingNotification)
    {
        this.playerID = playerID;
        this.questID = questID;
        this.state = state;
        this.needingNotification = needingNotification;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        QuestStateRecordDTO other = (QuestStateRecordDTO) obj;
        if (playerID != other.playerID)
        {
            return false;
        }
        if (questID != other.questID)
        {
            return false;
        }
        if (state != other.state)
        {
            return false;
        }
        return true;
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
     * @return the player's state for this quest
     */
    public QuestStateEnum getState()
    {
        return state;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + playerID;
        result = prime * result + questID;
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        return result;
    }

    /**
     * @return true if the player should be notified about this state
     */
    public boolean isNeedingNotification()
    {
        return needingNotification;
    }

    /**
     * @param state the new state
     */
    public void setState(QuestStateEnum state)
    {
        this.state = state;
    }

    /**
     * @param needingNotification value to save
     */
    public void setNeedingNotification(boolean needingNotification)
    {
        this.needingNotification = needingNotification;
    }

}

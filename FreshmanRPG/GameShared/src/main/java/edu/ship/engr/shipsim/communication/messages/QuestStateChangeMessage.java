package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.QuestStateEnum;

import java.io.Serializable;

/**
 * A message from an area server to a client telling the client to notify the
 * player that he has fulfilled a quest
 *
 * @author Merlin
 */
public class QuestStateChangeMessage extends Message implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int questID;
    private String questTitle;
    private String questDescription;
    private QuestStateEnum newState;

    /**
     * @param playerID         the ID of the player
     * @param questID          the ID of the quest
     * @param questTitle       the title of the quest
     * @param questDescription the description of the quest
     * @param newState         the state the quest has moved to
     */
    public QuestStateChangeMessage(int playerID, boolean quietMessage, int questID, String questTitle, String questDescription,
                                   QuestStateEnum newState)
    {
        super(playerID, quietMessage);
        this.questID = questID;
        this.questTitle = questTitle;
        this.questDescription = questDescription;
        this.newState = newState;
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
        QuestStateChangeMessage other = (QuestStateChangeMessage) obj;
        if (newState != other.newState)
        {
            return false;
        }
        if (relevantPlayerID != other.relevantPlayerID)
        {
            return false;
        }
        if (questDescription == null)
        {
            if (other.questDescription != null)
            {
                return false;
            }
        }
        else if (!questDescription.equals(other.questDescription))
        {
            return false;
        }
        if (questID != other.questID)
        {
            return false;
        }
        return true;
    }

    /**
     * @return the state the quest has moved to
     */
    public QuestStateEnum getNewState()
    {
        return newState;
    }

    /**
     * @return the quest's descrption
     */
    public String getQuestDescription()
    {
        return questDescription;
    }

    /**
     * @return the quest's ID
     */
    public int getQuestID()
    {
        return questID;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((newState == null) ? 0 : newState.hashCode());
        result = prime * result + relevantPlayerID;
        result = prime * result + ((questDescription == null) ? 0 : questDescription.hashCode());
        result = prime * result + questID;
        return result;
    }

    /**
     * @return this quest's title
     */
    public String getQuestTitle()
    {
        return this.questTitle;
    }
}

package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.view.screen.notification.NotificationType;

/**
 * @author sl6469, Cody
 */

/**
 * Sent when a quest moves to the needs fullfillment notification state
 *
 * @author Merlin
 */
public final class QuestStateChangeReport implements Report, NotificationTrigger
{

    private final int questID;
    private String questDescription;
    private QuestStateEnum newState;
    private int playerID;

    /**
     * @param playerID         the player whose state has changed (always this player)
     * @param questID          the quest's unique ID
     * @param questDescription the description of this quest
     * @param newState         the state the quest has transitioned to for this player
     */
    public QuestStateChangeReport(int playerID, int questID, String questDescription, QuestStateEnum newState)
    {
        this.playerID = playerID;
        this.questID = questID;
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
        QuestStateChangeReport other = (QuestStateChangeReport) obj;
        if (newState != other.newState)
        {
            return false;
        }
        if (playerID != other.playerID)
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
     * @return the player's id
     */
    public int getPlayerID()
    {
        return playerID;
    }


    /**
     * @return the description of the quest whose state has changed
     */
    public String getQuestDescription()
    {
        return questDescription;
    }


    /**
     * @return the questID that needs the report
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
        result = prime * result + playerID;
        result = prime * result
                + ((questDescription == null) ? 0 : questDescription.hashCode());
        result = prime * result + questID;
        return result;
    }

    @Override
    public String getNotificationTitle()
    {
        return "Quest updated";
    }

    @Override
    public String getNotificationBody()
    {
        return "Quest " + newState.getDescription() + getQuestDescription();//remove if description is too long
    }

    @Override
    public NotificationType getNotificationType()
    {
        return NotificationType.ALERT;
    }
}

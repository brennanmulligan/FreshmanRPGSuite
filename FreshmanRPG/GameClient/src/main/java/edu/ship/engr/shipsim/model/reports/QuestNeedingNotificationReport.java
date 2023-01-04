package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.view.screen.notification.NotificationType;

/**
 * This class will send a report that contains the strings of quests that are
 * currently of state "needing notification" so that the client
 * can be informed of their completion.
 *
 * @author merlin
 */
public final class QuestNeedingNotificationReport implements Report, NotificationTrigger
{

    private final int questID;
    private final int playerID;
    private final String questDescription;
    private QuestStateEnum state;

    /**
     * Constructor
     *
     * @param playerID         id of the player
     * @param questID          id of the quest
     * @param questDescription the description of the quest
     * @param state            the state of the quest for this player
     */
    public QuestNeedingNotificationReport(int playerID, int questID, String questDescription, QuestStateEnum state)
    {
        this.playerID = playerID;
        this.questID = questID;
        this.questDescription = questDescription;
        this.state = state;
    }


    /**
     * @return description of quest
     */
    public String getQuestDescription()
    {
        return questDescription;
    }

    /**
     * @return id of the quest
     */
    public int getQuestID()
    {
        return questID;
    }

    /**
     * @return id of the player
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return the state of this quest for this player
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
        result = prime * result
                + ((questDescription == null) ? 0 : questDescription.hashCode());
        result = prime * result + questID;
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        return result;
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
        QuestNeedingNotificationReport other = (QuestNeedingNotificationReport) obj;
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
        if (state != other.state)
        {
            return false;
        }
        return true;
    }

    @Override
    public String getNotificationTitle()
    {
        return "Quest " + state.getDescription();
    }

    @Override
    public String getNotificationBody()
    {
        return "Quest " + state.getDescription() + ": " + getQuestDescription();
    }

    @Override
    public NotificationType getNotificationType()
    {
        return NotificationType.ALERT;
    }
}

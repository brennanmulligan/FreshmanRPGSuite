package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.view.screen.notification.NotificationType;

/**
 * @author sl6469, cody
 */
public final class ObjectiveStateChangeReportInClient implements Report, NotificationTrigger
{

    private final int objectiveID;
    private final String objectiveDescription;
    private final ObjectiveStateEnum newState;
    private final int questID;
    private final int playerID;

    /**
     * @param playerID             this player's playerID
     * @param questID              the quest this objective is attached to
     * @param objectiveID          unique objective ID
     * @param objectiveDescription description of objective
     * @param newState             state the objective has moved to for this client player
     */
    public ObjectiveStateChangeReportInClient(int playerID, int questID, int objectiveID, String objectiveDescription, ObjectiveStateEnum newState)
    {
        this.playerID = playerID;
        this.questID = questID;
        this.objectiveID = objectiveID;
        this.objectiveDescription = objectiveDescription;
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
        ObjectiveStateChangeReportInClient other = (ObjectiveStateChangeReportInClient) obj;
        if (objectiveDescription == null)
        {
            if (other.objectiveDescription != null)
            {
                return false;
            }
        }
        else if (!objectiveDescription.equals(other.objectiveDescription))
        {
            return false;
        }
        if (objectiveID != other.objectiveID)
        {
            return false;
        }
        if (newState != other.newState)
        {
            return false;
        }
        if (playerID != other.playerID)
        {
            return false;
        }
        return questID == other.questID;
    }

    /**
     * @return the description of the objective whose state has changed
     */
    public String getObjectiveDescription()
    {
        return objectiveDescription;
    }


    /**
     * @return the objectiveID that needs the report
     */
    public int getObjectiveID()
    {
        return objectiveID;
    }

    /**
     * @return the state the objective has moved to
     */
    public ObjectiveStateEnum getNewState()
    {
        return newState;
    }

    /**
     * @return the player id of the player this state is associated with
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return the id of the quest containing the objective
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
        result = prime * result
                + ((objectiveDescription == null) ? 0 : objectiveDescription.hashCode());
        result = prime * result + objectiveID;
        result = prime * result + ((newState == null) ? 0 : newState.hashCode());
        result = prime * result + playerID;
        result = prime * result + questID;
        return result;
    }

    @Override
    public String getNotificationTitle()
    {
        if (newState.equals(ObjectiveStateEnum.LATE))
        {
            return "Objective failed";
        }
        else
        {
            return "Objective updated";
        }
    }

    @Override
    public String getNotificationBody()
    {
        return "Objective " + newState.getDescription() + getObjectiveDescription();
    }

    @Override
    public NotificationType getNotificationType()
    {
        return NotificationType.ALERT;
    }
}

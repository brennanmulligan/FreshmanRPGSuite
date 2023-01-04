package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.view.screen.notification.NotificationType;

import java.util.Objects;

/**
 * This class will send a report that contains the strings of objectives that
 * are currently of state "needing notification" so that the client can be
 * informed of their completion.
 *
 * @author nk3668 & ew4344
 */
public final class ObjectiveNeedingNotificationReport implements Report, NotificationTrigger
{

    private final int questID;
    private final int objectiveID;
    private final int playerID;
    private final String objectiveDescription;
    private final ObjectiveStateEnum state;
    private final boolean realLifeObjective;
    private final String witnessTitle;

    /**
     * Constructor
     *
     * @param playerID             id of the player
     * @param questID              id of the quest
     * @param objectiveID          id of the objective
     * @param objectiveDescription the description of the objective
     * @param state                the state of the objective for this player
     * @param realLifeObjective    true if the player must complete this objective in real life
     * @param witnessTitle         if this is a real life objective, this will hold the title of
     *                             the person who can witness its completion
     */
    public ObjectiveNeedingNotificationReport(int playerID, int questID, int objectiveID,
                                              String objectiveDescription, ObjectiveStateEnum state,
                                              boolean realLifeObjective, String witnessTitle)
    {
        this.playerID = playerID;
        this.questID = questID;
        this.objectiveID = objectiveID;
        this.objectiveDescription = objectiveDescription;
        this.state = state;
        this.realLifeObjective = realLifeObjective;
        this.witnessTitle = witnessTitle;
    }


    /**
     * @return description of objective
     */
    public String getObjectiveDescription()
    {
        return objectiveDescription;
    }

    /**
     * @return id of the objective
     */
    public int getObjectiveID()
    {
        return objectiveID;
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

    /**
     * @return the state of this objective for this player
     */
    public ObjectiveStateEnum getState()
    {
        return state;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        ObjectiveNeedingNotificationReport that = (ObjectiveNeedingNotificationReport) o;
        return questID == that.questID && objectiveID == that.objectiveID && playerID == that.playerID &&
                realLifeObjective == that.realLifeObjective &&
                Objects.equals(objectiveDescription, that.objectiveDescription) && state == that.state &&
                Objects.equals(witnessTitle, that.witnessTitle);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(questID, objectiveID, playerID, objectiveDescription, state, realLifeObjective, witnessTitle);
    }

    /**
     * @return true if the player must complete this objective in real life
     */
    public boolean isRealLifeObjective()
    {
        return realLifeObjective;
    }

    /**
     * @return the title of the witness who can vouch for completion if this is
     * a real life title
     */
    public String getWitnessTitle()
    {
        return witnessTitle;
    }


    @Override
    public String getNotificationTitle()
    {
        return "Objective Update";
    }

    @Override
    public String getNotificationBody()
    {
        return "Objective " + state.getDescription() + ": " + getObjectiveDescription();
    }

    @Override
    public NotificationType getNotificationType()
    {
        return NotificationType.ALERT;
    }
}

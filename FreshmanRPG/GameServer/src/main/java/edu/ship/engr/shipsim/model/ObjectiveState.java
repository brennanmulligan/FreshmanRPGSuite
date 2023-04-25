package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.criteria.CriteriaTimerDTO;
import edu.ship.engr.shipsim.dataENUM.ObjectiveCompletionType;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.model.reports.ObjectiveStateChangeReport;

import java.util.Date;

/**
 * Stores the states of all the objectives for an individual player on the
 * server
 * <p>
 * Visibility within this class is VERY important!  Only the model should be able to
 * create these objects and call methods that change their state.  However, reports
 * needs access to the data stored in the object, so the getters need to be public.
 *
 * @author Ryan
 */
public class ObjectiveState
{

    private final int objectiveID;

    private ObjectiveStateEnum objectiveState;

    private QuestState parentQuestState;

    private boolean needingNotification;

    /**
     * Constructor for the instance variables.
     *
     * @param id                  : id of objective
     * @param state               : state of objective
     * @param needingNotification true if the player has not been notified that
     *                            we entered this state
     */
    protected ObjectiveState(int id, ObjectiveStateEnum state,
                             boolean needingNotification)
    {
        this.objectiveID = id;
        this.objectiveState = state;
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
        ObjectiveState other = (ObjectiveState) obj;
        if (objectiveID != other.objectiveID)
        {
            return false;
        }
        if (objectiveState != other.objectiveState)
        {
            return false;
        }
        if (needingNotification != other.needingNotification)
        {
            return false;
        }
        if (parentQuestState == null)
        {
            return other.parentQuestState == null;
        }
        else
        {
            return parentQuestState.equals(other.parentQuestState);
        }
    }

    /**
     * returns the id of the current objective
     *
     * @return the id
     */
    public int getID()
    {
        return objectiveID;
    }

    /**
     * returns the state of the current objective.
     *
     * @return the state
     */
    public ObjectiveStateEnum getState()
    {
        return objectiveState;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + objectiveID;
        result = prime * result +
                ((objectiveState == null) ? 0 : objectiveState.hashCode());
        result = prime * result + (needingNotification ? 1231 : 1237);
        result = prime * result +
                ((parentQuestState == null) ? 0 : parentQuestState.hashCode());
        return result;
    }

    /**
     * Does the player need to be notified about the state of this objective?
     *
     * @return true if notification is required
     */
    public boolean isNeedingNotification()
    {
        return needingNotification;
    }

    /**
     * Tell this objective which quest state it is contained within
     *
     * @param questState the parent state
     */
    public void setParentQuest(QuestState questState)
    {
        parentQuestState = questState;
    }

    private boolean objectiveIsExpiredOrCompleted()
    {
        return this.objectiveState == ObjectiveStateEnum.COMPLETED ||
                this.objectiveState == ObjectiveStateEnum.EXPIRED;
    }

    /**
     * Changes the state of an objective from hidden to pending.
     *
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong
     *                                         state
     * @throws DatabaseException               shouldn't
     * @throws IllegalQuestChangeException     thrown if error occurred during quest
     *                                         state change
     */
    protected void trigger() throws IllegalObjectiveChangeException, DatabaseException,
            IllegalQuestChangeException
    {
        QuestRecord quest = QuestManager.getSingleton().getQuest(this.parentQuestState.getID());
        ObjectiveRecord objective = quest.getObjective(this.objectiveID);

        if(objective.getCompletionType() == ObjectiveCompletionType.TIMED)
        {
            CriteriaTimerDTO timerDTO = (CriteriaTimerDTO) objective.getCompletionCriteria();

            Date endsAt = new Date(new Date().getTime() + timerDTO.getTime());

            CommandObjectiveTimerEnded command = new CommandObjectiveTimerEnded(this.parentQuestState.getID(), this.getID(), this.parentQuestState.getPlayerID());

            TimerManager.getSingleton().scheduleCommand(endsAt, command, this.parentQuestState.getPlayerID());
        }

        changeState(ObjectiveStateEnum.TRIGGERED, false);
    }

    /**
     * Change the state of the objective from pending to complete. The objective
     * is complete, but we need to tell the player
     *
     * @throws DatabaseException               if the datasource fails
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong
     *                                         state
     * @throws IllegalQuestChangeException     thrown if illegal state change
     */
    protected void complete() throws DatabaseException, IllegalObjectiveChangeException,
            IllegalQuestChangeException
    {
        // If we are late, we should ignore the completion
        if (this.objectiveState == ObjectiveStateEnum.LATE)
        {
            return;
        }

        changeState(ObjectiveStateEnum.COMPLETED, true);

        if (parentQuestState.getStateValue() != QuestStateEnum.EXPIRED)
        {

            PlayerManager.getSingleton()
                    .getPlayerFromID(this.parentQuestState.getPlayerID())
                    .addExperiencePoints(QuestManager.getSingleton()
                            .getObjective(this.parentQuestState.getID(), objectiveID)
                            .getExperiencePointsGained());

        }

        this.parentQuestState.checkForFulfillmentOrFinished();


    }

    /**
     * Changes the state of an objective from triggered to late.
     *
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong
     *                                         state
     * @throws DatabaseException               shouldn't
     * @throws IllegalQuestChangeException     thrown if error occurred during quest
     *                                         state change
     */
    protected void missed() throws IllegalObjectiveChangeException, DatabaseException,
            IllegalQuestChangeException
    {
        changeState(ObjectiveStateEnum.LATE, true);
    }

    /**
     * Changes the current states state to the given state and tells it if it
     * needs to notify the user.
     *
     * @param state               state to change to
     * @param needingNotification whether to notify or not
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong
     *                                         state
     * @throws DatabaseException               shouldn't
     */
    protected void changeState(ObjectiveStateEnum state, boolean needingNotification)
            throws IllegalObjectiveChangeException, DatabaseException
    {
        if (this.parentQuestState.getStateValue() == QuestStateEnum.EXPIRED)
        {
            if (!objectiveIsExpiredOrCompleted())
            {
                this.objectiveState = ObjectiveStateEnum.EXPIRED;
            }

        }
        else if ((this.objectiveState.equals(ObjectiveStateEnum.HIDDEN) &&
                    state.equals(ObjectiveStateEnum.TRIGGERED)) ||
                (this.objectiveState.equals(ObjectiveStateEnum.TRIGGERED) &&
                        state.equals(ObjectiveStateEnum.COMPLETED)) ||
                (this.objectiveState.equals(ObjectiveStateEnum.TRIGGERED) &&
                        state.equals(ObjectiveStateEnum.LATE)))
        {
            this.objectiveState = state;
            this.needingNotification = needingNotification;

            if (needingNotification)
            {
                ObjectiveRecord objective = QuestManager.getSingleton()
                        .getObjective(parentQuestState.getID(), objectiveID);
                String witness = null;
                if (objective.isRealLifeObjective())
                {
                    witness = objective.getCompletionCriteria().toString();
                }
                ReportObserverConnector.getSingleton().sendReport(
                        new ObjectiveStateChangeReport(parentQuestState.getPlayerID(),
                                parentQuestState.getID(), objectiveID,
                                objective.getObjectiveDescription(), objectiveState,
                                objective.isRealLifeObjective(), witness));
            }
        }
        else
        {
            throw new IllegalObjectiveChangeException(this.objectiveState, state);
        }
    }

    /**
     * Set needing notification to false
     */
    protected void turnOffNotification()
    {
        this.needingNotification = false;
    }

}

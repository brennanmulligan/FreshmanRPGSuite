package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.criteria.GameLocationDTO;
import edu.ship.engr.shipsim.criteria.QuestListCompletionParameter;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.VanityAwardsTableDataGateway;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.model.reports.QuestStateChangeReport;
import edu.ship.engr.shipsim.model.reports.TeleportOnQuestCompletionReport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Stores the states of all the quests for an individual player on the server
 *
 * @author Ryan
 */
public class QuestState
{
    private final int questID;
    private QuestStateEnum questState;
    private final ArrayList<ObjectiveState> objectiveList = new ArrayList<>();
    private int playerID;
    private boolean needingNotification;

    /**
     * Constructs the QuestState
     *
     * @param playerID            the player whose state this represents
     * @param questID             unique ID for the quest in the system
     * @param currentState        the quest's state for the player, can be either
     *                            hidden, available, triggered, fulfilled, completed
     * @param needingNotification true if the player should be notified about
     *                            the quest's state
     */
    protected QuestState(int playerID, int questID, QuestStateEnum currentState, boolean needingNotification)
    {
        this.playerID = playerID;
        this.questID = questID;
        this.questState = currentState;
        this.needingNotification = needingNotification;
    }

    /**
     * Assigns the quest's objectives using an ArrayList of objectives prepared
     * already
     *
     * @param objectiveList a list containing multiple objectives for a quest
     */
    protected void addObjectives(List<ObjectiveState> objectiveList)
    {
        for (ObjectiveState objective : objectiveList)
        {
            this.objectiveList.add(objective);
            objective.setParentQuest(this);
        }
    }

    /**
     * Check to see if we have fulfilled this quest by completing enough
     * objectives. If so, transition to the NEED_FULFILLED_NOTIFICATION state
     *
     * @throws DatabaseException           if the datasource can't find the number of
     *                                     objectives required for the quest
     * @throws IllegalQuestChangeException thrown if illegal state change
     */
    protected void checkForFulfillmentOrFinished() throws DatabaseException, IllegalQuestChangeException
    {
        if ((questState == QuestStateEnum.TRIGGERED) || (questState == QuestStateEnum.FULFILLED))
        {
            int objectivesComplete = 0;
            for (ObjectiveState state : objectiveList)
            {
                if (state.getState() == ObjectiveStateEnum.COMPLETED)
                {
                    objectivesComplete++;
                }
            }
            int objectivesRequired = QuestManager.getSingleton().getQuest(this.questID).getObjectivesForFulfillment();
            if ((questState == QuestStateEnum.TRIGGERED) && (objectivesComplete >= objectivesRequired))
            {
                changeState(QuestStateEnum.FULFILLED, true);
                PlayerManager.getSingleton().getPlayerFromID(playerID)
                        .addExperiencePoints(QuestManager.getSingleton().getQuest(questID).getExperiencePointsGained());

            }
            if ((questState == QuestStateEnum.FULFILLED) && (objectivesComplete >= objectiveList.size()))
            {
                complete();
            }
        }
    }

    /**
     * Changes the quest's state from fulfilled to completed
     * <p>
     * If this quests's CompletionActionType is TRIGGER_QUESTS, then we set all associated quests
     * of this quest to be triggered.
     *
     * @throws IllegalQuestChangeException thrown if illegal state change
     * @throws DatabaseException           shouldn't
     */
    protected void complete() throws IllegalQuestChangeException, DatabaseException
    {
        changeState(QuestStateEnum.COMPLETED, true);
        QuestRecord q = QuestManager.getSingleton().getQuest(questID);
        if (q.getCompletionActionType() == QuestCompletionActionType.TELEPORT)
        {
            GameLocationDTO gl = (GameLocationDTO) q.getCompletionActionParameter();
            MapToServerMapping mapping = new MapToServerMapping(gl.getMapName());
            Player playerFromID = PlayerManager.getSingleton().getPlayerFromID(playerID);
            playerFromID.setPositionWithoutNotifying(gl.getPosition());
            playerFromID.setMapName(gl.getMapName());
            playerFromID.persist();
            TeleportOnQuestCompletionReport report = new TeleportOnQuestCompletionReport(playerID, questID, gl,
                    mapping.getHostName(), mapping.getPortNumber());

            ReportObserverConnector.getSingleton().sendReport(report);
        }
        else if (q.getCompletionActionType() == QuestCompletionActionType.TRIGGER_QUESTS)
        {
            QuestListCompletionParameter ql = (QuestListCompletionParameter) q.getCompletionActionParameter();
            QuestState qs;
            for (Integer questID : ql.getQuestIDs())
            {
                qs = QuestManager.getSingleton().getQuestStateByID(playerID, questID);
                if (qs.getStateValue() == QuestStateEnum.AVAILABLE)
                {
                    qs.changeState(QuestStateEnum.TRIGGERED, true);
                }
            }
        }

        VanityAwardsTableDataGateway gateway =
                VanityAwardsTableDataGateway.getSingleton();
        ArrayList<VanityDTO> awardedVanities = gateway.getVanityAwardsForQuest(questID);

        if (!awardedVanities.isEmpty())
        {
            Player player = PlayerManager.getSingleton().getPlayerFromID(playerID);
            awardedVanities.forEach(player::addItemToInventory);
        }
    }

    /**
     * Returns the objectives in this quest
     *
     * @return list of objectives
     */
    public ArrayList<ObjectiveState> getObjectiveList()
    {
        return objectiveList;
    }

    /**
     * Returns the quest's unique ID
     *
     * @return questID the quest's unique ID
     */
    public int getID()
    {
        return questID;
    }

    /**
     * @return the ID of the player whose state this belongs to
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * Returns the size of this quest's objective list
     *
     * @return the number of objectives this quest has
     */
    public int getSizeOfObjectiveList()
    {
        return this.objectiveList.size();
    }

    /**
     * Returns the quest's state, if quest is not completed and after end date
     * then its expired
     *
     * @return questState the state of the quest for a player
     */
    public QuestStateEnum getStateValue()
    {
        Date now = Calendar.getInstance().getTime();
        Date questEndDate;
        try
        {
            questEndDate = QuestManager.getSingleton().getQuest(questID).getEndDate();
            if (questState != QuestStateEnum.COMPLETED && now.after(questEndDate))
            {
                return QuestStateEnum.EXPIRED;
            }
            return questState;
        }
        catch (DatabaseException e)
        {
            return questState;

        }
    }

    /**
     * @return true if the player should be notified about the state of this
     * quest
     */
    public boolean isNeedingNotification()
    {
        return needingNotification;
    }

    /**
     * Tell this state which player it belongs to
     *
     * @param playerID the player's unique id
     */
    protected void setPlayerID(int playerID)
    {
        this.playerID = playerID;
    }

    /**
     * Change the quest's state from hidden to available Also change all the
     * quest's objectives from hidden to pending.
     *
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong
     *                                         state
     * @throws IllegalQuestChangeException     thrown if changing to a wrong state
     * @throws DatabaseException               shouldn't
     */
    protected void trigger() throws IllegalObjectiveChangeException, IllegalQuestChangeException, DatabaseException
    {

        changeState(QuestStateEnum.TRIGGERED, true);
        for (ObjectiveState state : objectiveList)
        {
            if (state.getState() == ObjectiveStateEnum.HIDDEN)
            {
                state.trigger();
            }
        }

    }

    /**
     * Change the state of the quest and create a report and send it to
     *
     * @param state  the new state being transitioned to
     * @param notify should the client be notified of this change
     * @throws IllegalQuestChangeException trying to make a state change that
     *                                     isn't allowed by our model's states.
     * @throws DatabaseException           shouldn't
     */
    protected void changeState(QuestStateEnum state, boolean notify)
            throws IllegalQuestChangeException, DatabaseException
    {
        if ((this.getStateValue().equals(QuestStateEnum.AVAILABLE) && state.equals(QuestStateEnum.TRIGGERED))
                || (this.getStateValue().equals(QuestStateEnum.TRIGGERED) && state.equals(QuestStateEnum.FULFILLED))
                || (this.getStateValue().equals(QuestStateEnum.FULFILLED) && state.equals(QuestStateEnum.COMPLETED)))
        {
            this.questState = state;
            this.needingNotification = notify;

            if (this.needingNotification)
            {
                QuestRecord quest = QuestManager.getSingleton().getQuest(questID);
                ReportObserverConnector.getSingleton().sendReport(new QuestStateChangeReport(playerID, questID,
                        quest.getTitle(), quest.getDescription(), questState));
            }

        }
        else
        {
            throw new IllegalQuestChangeException(this.getStateValue(), state);
        }
    }

    /**
     * @param newState true if the player should be notified about the current
     *                 state of the quest
     */
    protected void setNeedingNotification(boolean newState)
    {
        needingNotification = newState;
    }
}

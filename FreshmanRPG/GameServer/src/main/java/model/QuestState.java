package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import criteria.GameLocationDTO;
import criteria.QuestListCompletionParameter;
import dataENUM.QuestCompletionActionType;
import datasource.DatabaseException;
import datatypes.AdventureStateEnum;
import datatypes.QuestStateEnum;
import model.reports.QuestStateChangeReport;
import model.reports.TeleportOnQuestCompletionReport;

/**
 * Stores the states of all the quests for an individual player on the server
 *
 * @author Ryan
 *
 */
public class QuestState
{
	private int questID;
	private QuestStateEnum questState;
	private ArrayList<AdventureState> adventureList = new ArrayList<>();
	private int playerID;
	private boolean needingNotification;

	/**
	 * Constructs the QuestState
	 *
	 * @param playerID the player whose state this represents
	 * @param questID unique ID for the quest in the system
	 * @param currentState the quest's state for the player, can be either
	 *            hidden, available, triggered, fulfilled, completed
	 * @param needingNotification true if the player should be notified about
	 *            the quest's state
	 */
	protected QuestState(int playerID, int questID, QuestStateEnum currentState, boolean needingNotification)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.questState = currentState;
		this.needingNotification = needingNotification;
	}

	/**
	 * Assigns the quest's adventures using an ArrayList of adventures prepared
	 * already
	 *
	 * @param adventureList a list containing multiple adventures for a quest
	 */
	protected void addAdventures(ArrayList<AdventureState> adventureList)
	{
		for (AdventureState adventure : adventureList)
		{
			this.adventureList.add(adventure);
			adventure.setParentQuest(this);
		}
	}

	/**
	 * Check to see if we have fulfilled this quest by completing enough
	 * adventures. If so, transition to the NEED_FULFILLED_NOTIFICATION state
	 *
	 * @throws DatabaseException if the datasource can't find the number of
	 *             adventures required for the quest
	 * @throws IllegalQuestChangeException thrown if illegal state change
	 */
	protected void checkForFulfillmentOrFinished() throws DatabaseException, IllegalQuestChangeException
	{
		if ((questState == QuestStateEnum.TRIGGERED) || (questState == QuestStateEnum.FULFILLED))
		{
			int adventuresComplete = 0;
			for (AdventureState state : adventureList)
			{
				if (state.getState() == AdventureStateEnum.COMPLETED)
				{
					adventuresComplete++;
				}
			}
			int adventuresRequired = QuestManager.getSingleton().getQuest(this.questID).getAdventuresForFulfillment();
			if ((questState == QuestStateEnum.TRIGGERED) && (adventuresComplete >= adventuresRequired))
			{
				changeState(QuestStateEnum.FULFILLED, true);
				PlayerManager.getSingleton().getPlayerFromID(playerID)
						.addExperiencePoints(QuestManager.getSingleton().getQuest(questID).getExperiencePointsGained());

			}
			if ((questState == QuestStateEnum.FULFILLED) && (adventuresComplete >= adventureList.size()))
			{
				complete();
			}
		}
	}

	/**
	 * Changes the quest's state from fulfilled to completed
	 *
	 * If this quests's CompletionActionType is TRIGGER_QUESTS, then we set all associated quests
	 * of this quest to be triggered.
	 *
	 * @throws IllegalQuestChangeException thrown if illegal state change
	 * @throws DatabaseException shouldn't
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
			playerFromID.setPlayerPositionWithoutNotifying(gl.getPosition());
			playerFromID.setMapName(gl.getMapName());
			playerFromID.persist();
			TeleportOnQuestCompletionReport report = new TeleportOnQuestCompletionReport(playerID, questID, gl,
					mapping.getHostName(), mapping.getPortNumber());

			QualifiedObservableConnector.getSingleton().sendReport(report);
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
	}

	/**
	 * Returns the adventures in this quest
	 *
	 * @return list of adventures
	 */
	public ArrayList<AdventureState> getAdventureList()
	{
		return adventureList;
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
	 * Returns the size of this quest's adventure list
	 *
	 * @return the number of adventures this quest has
	 */
	public int getSizeOfAdventureList()
	{
		return this.adventureList.size();
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
	 *         quest
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
	 * quest's adventures from hidden to pending.
	 *
	 * @throws IllegalAdventureChangeException thrown if changing to a wrong
	 *             state
	 * @throws IllegalQuestChangeException thrown if changing to a wrong state
	 * @throws DatabaseException shouldn't
	 */
	protected void trigger() throws IllegalAdventureChangeException, IllegalQuestChangeException, DatabaseException
	{
		changeState(QuestStateEnum.TRIGGERED, true);
		for (AdventureState state : adventureList)
		{
			if (state.getState() == AdventureStateEnum.HIDDEN)
			{
				state.trigger();
			}
		}
	}

	/**
	 * Change the state of the quest and create a report and send it to
	 *
	 * @param state the new state being transitioned to
	 * @param notify should the client be notified of this change
	 * @throws IllegalQuestChangeException trying to make a state change that
	 *             isn't allowed by our model's states.
	 * @throws DatabaseException shouldn't
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

			if (this.needingNotification == true)
			{
				QuestRecord quest = QuestManager.getSingleton().getQuest(questID);
				QualifiedObservableConnector.getSingleton().sendReport(new QuestStateChangeReport(playerID, questID,
						quest.getTitle(), quest.getDescription(), questState));
			}

		}
		else
		{
			throw new IllegalQuestChangeException(this.getStateValue(), state);
		}
	}

	/**
	 *
	 * @param newState true if the player should be notified about the current
	 *            state of the quest
	 */
	protected void setNeedingNotification(boolean newState)
	{
		needingNotification = newState;
	}
}

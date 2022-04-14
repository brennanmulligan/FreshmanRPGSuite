package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import criteria.ObjectiveCompletionCriteria;
import criteria.QuestCompletionActionParameter;
import dataENUM.ObjectiveCompletionType;
import dataENUM.QuestCompletionActionType;
import datasource.ObjectiveRowDataGateway;
import datasource.ObjectiveRowDataGatewayMock;
import datasource.ObjectiveRowDataGatewayRDS;
import datasource.ObjectiveStateTableDataGateway;
import datasource.ObjectiveStateTableDataGatewayMock;
import datasource.ObjectiveStateTableDataGatewayRDS;
import datasource.ObjectiveTableDataGateway;
import datasource.ObjectiveTableDataGatewayMock;
import datasource.ObjectiveTableDataGatewayRDS;
import datasource.DatabaseException;
import datasource.QuestRowDataGateway;
import datasource.QuestRowDataGatewayMock;
import datasource.QuestRowDataGatewayRDS;
import datasource.QuestStateTableDataGateway;
import datasource.QuestStateTableDataGatewayMock;
import datasource.QuestStateTableDataGatewayRDS;
import datasource.QuestTableDataGateway;
import datasource.QuestTableDataGatewayMock;
import datasource.QuestTableDataGatewayRDS;
import datatypes.ObjectiveStateEnum;
import datatypes.Position;
import datatypes.QuestStateEnum;
import model.reports.AllQuestsAndObjectivesReport;
import model.reports.PlayersUncompletedObjectivesReport;

/**
 * Controls access to quests and their objectives.
 */
public class GameManagerQuestManager
{

	private static GameManagerQuestManager instance;
	private QuestStateTableDataGateway questStateGateway;
	private ObjectiveStateTableDataGateway objectiveStateGateway;

	/**
	 * @return The quest manager instance.
	 */
	public static GameManagerQuestManager getInstance()
	{
		if (instance == null)
		{
			instance = new GameManagerQuestManager();
		}

		return instance;
	}

	/**
	 * Used for testing. Force the instance to be reloaded.
	 */
	static void resetSingleton()
	{
		instance = null;
	}

	/**
	 * Used for testing
	 *
	 * @param manager
	 *            The instance to which the singleton will be set.
	 */
	static void setSingleton(GameManagerQuestManager manager)
	{
		instance = manager;
	}

	/**
	 * Should only be called by subclasses used for testing
	 */
	GameManagerQuestManager()
	{
	}

	/**
	 * @param questTitle
	 *            - Title of the quest
	 * @param questDescription
	 *            - Description of the quest
	 * @param triggerMapName
	 *            - The map name
	 * @param triggerPosition
	 *            - The Position
	 * @param experiencePointsGained
	 *            - The exp gained
	 * @param objectivesForFulfillment
	 *            - Objectives to fulfill the quest
	 * @param completionActionType
	 *            - How you complete the quest
	 * @param completionActionParameter
	 *            - What you need to complete the quest
	 * @param startDate
	 *            - Start date
	 * @param endDate
	 *            - End date
	 * @throws DatabaseException
	 *             - If it fails to access or add to the db
	 */
	public void addQuest(String questTitle, String questDescription, String triggerMapName, Position triggerPosition,
						 int experiencePointsGained, int objectivesForFulfillment, QuestCompletionActionType completionActionType,
						 QuestCompletionActionParameter completionActionParameter, Date startDate, Date endDate)
			throws DatabaseException
	{
		if (!OptionsManager.getSingleton().isUsingMockDataSource())
		{
			new QuestRowDataGatewayRDS(questTitle, questDescription, triggerMapName, triggerPosition,
					experiencePointsGained, objectivesForFulfillment, completionActionType, completionActionParameter,
					startDate, endDate);
		}
		else
		{
			new QuestRowDataGatewayMock(questTitle, questDescription, triggerMapName, triggerPosition,
					experiencePointsGained, objectivesForFulfillment, completionActionType, completionActionParameter,
					startDate, endDate);
		}
		sendQuestReport();
	}

	/**
	 * Update an objective given it's quest and objective ids
	 *
	 * @param questId
	 *            it's quest id
	 * @param objectiveID
	 *            it's objective id
	 * @param objectiveDescription
	 *            - the updated objective description
	 * @param experiencePointsGained
	 *            - the updated exp gained
	 * @param objectiveCompletionType
	 *            - the updated completion type
	 * @param objectiveCompletionCriteria
	 *            - the updated criteria
	 * @throws DatabaseException
	 *             - if the database couldn't save the changes
	 */
	public void editObjective(int questId, int objectiveID, String objectiveDescription, int experiencePointsGained,
							  ObjectiveCompletionType objectiveCompletionType, ObjectiveCompletionCriteria objectiveCompletionCriteria)
			throws DatabaseException
	{
		ObjectiveRowDataGateway gateway = null;

		if (!OptionsManager.getSingleton().isUsingMockDataSource())
		{
			gateway = new ObjectiveRowDataGatewayRDS(questId, objectiveID);
		}
		else
		{
			gateway = new ObjectiveRowDataGatewayMock(questId, objectiveID);
		}
		gateway.setObjectiveDescription(objectiveDescription);
		gateway.setCompletionCriteria(objectiveCompletionCriteria);
		gateway.setCompletionType(objectiveCompletionType);
		gateway.setExperiencePointsGained(experiencePointsGained);
		gateway.persist();
		sendQuestReport();

	}

	/**
	 * Get the uncompleted objective for a player
	 *
	 * @param playerID
	 *            the player to retrieve for
	 * @return ArrayList of ObjectiveStateDTO's
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	public ArrayList<ObjectiveRecord> getIncompleteObjectives(int playerID) throws DatabaseException
	{
		PlayerMapper mapper = new PlayerMapper(playerID);
		return mapper.getIncompleteObjectives();
	}

	/**
	 * edits a current player in the database
	 *
	 * @param questID
	 *            id of quest to be edited
	 * @param questTitle
	 *            - Title of the quest
	 * @param questDescription
	 *            - Description of the quest
	 * @param triggerMapName
	 *            - The map name
	 * @param triggerPosition
	 *            - The Position
	 * @param experiencePointsGained
	 *            - The experience gained
	 * @param objectivesForFulfillment
	 *            - Objectives to fulfill the quest
	 * @param completionActionType
	 *            - How you complete the quest
	 * @param completionActionParameter
	 *            - What you need to complete the quest
	 * @param startDate
	 *            - Start date
	 * @param endDate
	 *            - End date
	 * @throws DatabaseException
	 *             - If it fails to access or update data in the database
	 */
	public void editQuest(int questID, String questTitle, String questDescription, String triggerMapName,
						  Position triggerPosition, int experiencePointsGained, int objectivesForFulfillment,
						  QuestCompletionActionType completionActionType, QuestCompletionActionParameter completionActionParameter,
						  Date startDate, Date endDate) throws DatabaseException
	{
		QuestRowDataGateway gateway = null;

		if (!OptionsManager.getSingleton().isUsingMockDataSource())
		{
			gateway = new QuestRowDataGatewayRDS(questID);
		}
		else
		{
			gateway = new QuestRowDataGatewayMock(questID);
		}

		gateway.setObjectivesForFulfillment(objectivesForFulfillment);
		gateway.setCompletionActionParameter(completionActionParameter);
		gateway.setCompletionActionType(completionActionType);
		gateway.setEndDate(endDate);
		gateway.setExperiencePointsGained(experiencePointsGained);
		gateway.setQuestDescription(questDescription);
		gateway.setQuestTitle(questTitle);
		gateway.setStartDate(startDate);
		gateway.setTriggerMapName(triggerMapName);
		gateway.setTriggerPosition(triggerPosition);

		gateway.persist();

		sendQuestReport();
	}

	/**
	 * @return All of the quests managed by the quest manager.
	 * @throws DatabaseException
	 *             If the quests can not be loaded from the database.
	 */
	public ArrayList<QuestRecord> getQuests() throws DatabaseException
	{
		return getTableGateway().getAllQuests();
	}

	/**
	 * Retrieve a quest by quest ID.
	 *
	 * @param questId
	 *            - quest ID
	 * @return - quest record associated with that quest ID
	 * @throws DatabaseException
	 *             - quests unable to be loaded from data source
	 */
	public QuestRecord getQuest(int questId) throws DatabaseException
	{
		ArrayList<QuestRecord> quests = getQuests();
		final QuestRecord quest = quests.stream().filter(q -> q.getQuestID() == questId).findAny().orElse(null);
		return quest;
	}

	/**
	 * Returns the quest based on the title of the quest.
	 *
	 * @param questName
	 *            Name of the quest
	 * @return Quest
	 * @throws DatabaseException
	 *             Exception
	 */
	public QuestRecord getQuest(String questName) throws DatabaseException
	{
		final QuestRecord quest = getQuests().stream().filter(q -> q.getTitle().equals(questName)).findAny().orElse(null);
		return quest;
	}


	/**
	 * Get a Row data gateway for Objectives
	 *
	 * @param questId
	 *            id of quest
	 * @param objectiveId
	 *            id of objective
	 * @return the gateway
	 * @throws DatabaseException
	 *             will happen if objective doesn't exist
	 */
	protected ObjectiveRowDataGateway getObjectiveRowDataGateway(int questId, int objectiveId) throws DatabaseException
	{
		if (!OptionsManager.getSingleton().isUsingMockDataSource())
		{
			return new ObjectiveRowDataGatewayRDS(questId, objectiveId);
		}
		else
		{
			return new ObjectiveRowDataGatewayMock(questId, objectiveId);
		}
	}

	/**
	 * Get a QuestRowDataGateway
	 *
	 * @param questID the id of the quest we are interested in
	 * @return the gateway
	 * @throws DatabaseException if we can't create the appropriate gateway
	 */
	protected QuestRowDataGateway getQuestRowDataGateway(int questID) throws DatabaseException
	{
		if (!OptionsManager.getSingleton().isUsingMockDataSource())
		{
			return new QuestRowDataGatewayRDS(questID);
		}
		else
		{
			return new QuestRowDataGatewayMock(questID);
		}
	}

	/**
	 * @return the appropriate gateway to all of the quests
	 */
	protected QuestTableDataGateway getTableGateway()
	{
		if (!OptionsManager.getSingleton().isUsingMockDataSource())
		{
			return QuestTableDataGatewayRDS.getInstance();
		}
		return QuestTableDataGatewayMock.getInstance();
	}

	/**
	 * @return the appropriate gateway to all of the objectives
	 */
	protected ObjectiveTableDataGateway getObjectiveTableGateway()
	{
		if (!OptionsManager.getSingleton().isUsingMockDataSource())
		{
			return ObjectiveTableDataGatewayRDS.getSingleton();
		}
		return ObjectiveTableDataGatewayMock.getSingleton();
	}

	/**
	 * Create an objective and persist it in the data source.
	 *
	 * @param questID
	 *            - quest ID that this objective belongs to
	 * @param objectiveDescription
	 *            - description of objective
	 * @param experiencePointsGained
	 *            - experience points gained by completing objective
	 * @param type
	 *            - objective completion type
	 * @param criteria
	 *            - objective completion criteria
	 * @return - true if objective successfully added
	 * @throws DatabaseException
	 *             - quests unable to be loaded from data source
	 */
	public boolean addObjective(int questID, String objectiveDescription, int experiencePointsGained,
								ObjectiveCompletionType type, ObjectiveCompletionCriteria criteria) throws DatabaseException
	{
		final QuestRecord quest = getQuest(questID);
		if (quest == null)
		{
			return false;
		}

		final int ObjectiveId = getObjectiveTableGateway().getNextObjectiveID(questID);
		final ObjectiveRecord objective = new ObjectiveRecord(questID, ObjectiveId, objectiveDescription,
				experiencePointsGained, type, criteria);
		quest.getObjectives().add(objective);

		if (!OptionsManager.getSingleton().isUsingMockDataSource())
		{
			try
			{
				new ObjectiveRowDataGatewayRDS(ObjectiveId, objectiveDescription, questID, experiencePointsGained, type,
						criteria);
			}
			catch (DatabaseException e)
			{
				return false;
			}
		}
		else
		{
			new ObjectiveRowDataGatewayMock(ObjectiveId, objectiveDescription, questID, experiencePointsGained, type,
					criteria);
		}

		sendQuestReport();

		return true;
	}

	/**
	 * Delete an objective
	 *
	 * @param questId
	 *            quest
	 * @param objectiveId
	 *            objective
	 * @return True if successful and false if not
	 */
	public boolean deleteObjective(int questId, int objectiveId)
	{
		try
		{
			ObjectiveRowDataGateway gateway = getObjectiveRowDataGateway(questId, objectiveId);
			QuestRecord quest = getQuest(questId);
			quest.getObjectives().remove(quest.getObjectiveID(objectiveId));
			gateway.removeObjective();
			sendQuestReport();
			return true;

		}
		catch (DatabaseException e)
		{
			return false;
		}
	}

	/**
	 * Sends a player list report built from the in memory player list
	 *
	 * @throws DatabaseException
	 *             - If the quests can not be loaded from the database.
	 */
	public void sendQuestReport() throws DatabaseException
	{
		AllQuestsAndObjectivesReport report = new AllQuestsAndObjectivesReport(this.getQuests());
		QualifiedObservableConnector.getSingleton().sendReport(report);
	}

	/**
	 * Sends a report containing objectives that have not been completed based on a
	 * playerID
	 * @param playerID the one we are interested in
	 *
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	public void sendUncompletedObjectivesReport(int playerID) throws DatabaseException
	{
		PlayersUncompletedObjectivesReport report = new PlayersUncompletedObjectivesReport(new PlayerMapper(playerID).getIncompleteObjectives());
		QualifiedObservableConnector.getSingleton().sendReport(report);
	}

	/**
	 * Deletes a quest based on its ID
	 *
	 * @param questID
	 *            Id of the quest to be deleted
	 * @return success true or false
	 * @throws SQLException
	 *             - shouldn't
	 */
	public boolean deleteQuest(int questID) throws SQLException
	{
		try
		{
			QuestRowDataGateway gateway = getQuestRowDataGateway(questID);
			QuestRecord quest = getQuest(questID);
			ArrayList<ObjectiveRecord> objectiveList = quest.getObjectives();

			for (ObjectiveRecord objective : objectiveList)
			{
				ObjectiveRowDataGateway objectiveRDG = getObjectiveRowDataGateway(questID, objective.getObjectiveID());
				objectiveRDG.removeObjective();
			}
			quest.setObjectives(new ArrayList<>());
			gateway.remove();
			sendQuestReport();
			return true;
		}
		catch (DatabaseException e)
		{
			return false;
		}
	}

	/**
	 *
	 * @throws DatabaseException if we fail to delete all of the objectives
	 */
	protected void removeAllQuestsObjectives() throws DatabaseException
	{
		ArrayList<QuestRecord> quests = getQuests();
		while (quests.size() != 0)
		{
			int id = quests.get(0).getQuestID();
			try
			{
				deleteQuest(id);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			quests = getQuests();
		}
		sendQuestReport();
	}

	/**
	 * @param questID
	 *            questID
	 * @param playerID
	 *            playerID
	 * @throws DatabaseException
	 *             exception
	 */
	public void triggerQuest(int questID, int playerID) throws DatabaseException
	{
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			this.questStateGateway = QuestStateTableDataGatewayMock.getSingleton();
			this.objectiveStateGateway = ObjectiveStateTableDataGatewayMock.getSingleton();

		}
		else
		{
			this.questStateGateway = QuestStateTableDataGatewayRDS.getSingleton();
			this.objectiveStateGateway = ObjectiveStateTableDataGatewayRDS.getSingleton();
		}
		questStateGateway.udpateState(playerID, questID, QuestStateEnum.TRIGGERED, false);
		ArrayList<ObjectiveRecord> objectiveList = this.getObjectiveTableGateway().getObjectivesForQuest(questID);
		for (ObjectiveRecord objective : objectiveList)
		{
			objectiveStateGateway.updateState(playerID, questID, objective.getObjectiveID(), ObjectiveStateEnum.TRIGGERED,
					false);
		}
	}

	/**
	 * Return the number of quests.  Just used for testing
	 *
	 * @return the size of players
	 * @throws DatabaseException shouldn't
	 */
	protected int getNumberOfQuests() throws DatabaseException
	{
		return this.getTableGateway().getAllQuests().size();
	}

}

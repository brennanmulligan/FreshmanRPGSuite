package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import criteria.AdventureCompletionCriteria;
import criteria.QuestCompletionActionParameter;
import dataENUM.AdventureCompletionType;
import dataENUM.QuestCompletionActionType;
import datasource.AdventureRowDataGateway;
import datasource.AdventureRowDataGatewayMock;
import datasource.AdventureRowDataGatewayRDS;
import datasource.AdventureStateTableDataGateway;
import datasource.AdventureStateTableDataGatewayMock;
import datasource.AdventureStateTableDataGatewayRDS;
import datasource.AdventureTableDataGateway;
import datasource.AdventureTableDataGatewayMock;
import datasource.AdventureTableDataGatewayRDS;
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
import datatypes.AdventureStateEnum;
import datatypes.Position;
import datatypes.QuestStateEnum;
import model.reports.AllQuestsAndAdventuresReport;
import model.reports.PlayersUncompletedAdventuresReport;

/**
 * Controls access to quests and their adventures.
 */
public class GameManagerQuestManager
{

	private static GameManagerQuestManager instance;
	private QuestStateTableDataGateway questStateGateway;
	private AdventureStateTableDataGateway adventureStateGateway;

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
	 * @param adventuresForFulfillment
	 *            - Adventures to fulfill the quest
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
						 int experiencePointsGained, int adventuresForFulfillment, QuestCompletionActionType completionActionType,
						 QuestCompletionActionParameter completionActionParameter, Date startDate, Date endDate)
			throws DatabaseException
	{
		if (!OptionsManager.getSingleton().isUsingMockDataSource())
		{
			new QuestRowDataGatewayRDS(questTitle, questDescription, triggerMapName, triggerPosition,
					experiencePointsGained, adventuresForFulfillment, completionActionType, completionActionParameter,
					startDate, endDate);
		}
		else
		{
			new QuestRowDataGatewayMock(questTitle, questDescription, triggerMapName, triggerPosition,
					experiencePointsGained, adventuresForFulfillment, completionActionType, completionActionParameter,
					startDate, endDate);
		}
		sendQuestReport();
	}

	/**
	 * Update an adventure given it's quest and adventure ids
	 *
	 * @param questId
	 *            it's quest id
	 * @param adventureId
	 *            it's adventure id
	 * @param adventureDescription
	 *            - the updated adventuredescription
	 * @param experiencePointsGained
	 *            - the updated exp gained
	 * @param adventureCompletionType
	 *            - the updated completion type
	 * @param adventureCompletionCriteria
	 *            - the updated criteria
	 * @throws DatabaseException
	 *             - if the database couldn't save the changes
	 */
	public void editAdventure(int questId, int adventureId, String adventureDescription, int experiencePointsGained,
							  AdventureCompletionType adventureCompletionType, AdventureCompletionCriteria adventureCompletionCriteria)
			throws DatabaseException
	{
		AdventureRowDataGateway gateway = null;

		if (!OptionsManager.getSingleton().isUsingMockDataSource())
		{
			gateway = new AdventureRowDataGatewayRDS(questId, adventureId);
		}
		else
		{
			gateway = new AdventureRowDataGatewayMock(questId, adventureId);
		}
		gateway.setAdventureDescription(adventureDescription);
		gateway.setCompletionCriteria(adventureCompletionCriteria);
		gateway.setCompletionType(adventureCompletionType);
		gateway.setExperiencePointsGained(experiencePointsGained);
		gateway.persist();
		sendQuestReport();

	}

	/**
	 * Get the uncompleted adventure for a player
	 *
	 * @param playerID
	 *            the player to retrieve for
	 * @return ArrayList of AdventureStateDTO's
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	public ArrayList<AdventureRecord> getIncompleteAdventures(int playerID) throws DatabaseException
	{
		PlayerMapper mapper = new PlayerMapper(playerID);
		return mapper.getIncompleteAdventures();
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
	 * @param adventuresForFulfillment
	 *            - Adventures to fulfill the quest
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
						  Position triggerPosition, int experiencePointsGained, int adventuresForFulfillment,
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

		gateway.setAdventuresForFulfillment(adventuresForFulfillment);
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
	 * Get a Row data gateway for Adventures
	 *
	 * @param questId
	 *            id of quest
	 * @param adventureId
	 *            id of adventure
	 * @return the gateway
	 * @throws DatabaseException
	 *             will happen if adventure doesn't exist
	 */
	protected AdventureRowDataGateway getAdventureRowDataGateway(int questId, int adventureId) throws DatabaseException
	{
		if (!OptionsManager.getSingleton().isUsingMockDataSource())
		{
			return new AdventureRowDataGatewayRDS(questId, adventureId);
		}
		else
		{
			return new AdventureRowDataGatewayMock(questId, adventureId);
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
	 * @return the appropriate gateway to all of the adventures
	 */
	protected AdventureTableDataGateway getAdventureTableGateway()
	{
		if (!OptionsManager.getSingleton().isUsingMockDataSource())
		{
			return AdventureTableDataGatewayRDS.getSingleton();
		}
		return AdventureTableDataGatewayMock.getSingleton();
	}

	/**
	 * Create an adventure and persist it in the data source.
	 *
	 * @param questID
	 *            - quest ID that this adventure belongs to
	 * @param adventureDescription
	 *            - description of adventure
	 * @param experiencePointsGained
	 *            - experience points gained by completing adventure
	 * @param type
	 *            - adventure completion type
	 * @param criteria
	 *            - adventure completion criteria
	 * @return - true if adventure successfully added
	 * @throws DatabaseException
	 *             - quests unable to be loaded from data source
	 */
	public boolean addAdventure(int questID, String adventureDescription, int experiencePointsGained,
								AdventureCompletionType type, AdventureCompletionCriteria criteria) throws DatabaseException
	{
		final QuestRecord quest = getQuest(questID);
		if (quest == null)
		{
			return false;
		}

		final int adventureId = getAdventureTableGateway().getNextAdventureID(questID);
		final AdventureRecord adventure = new AdventureRecord(questID, adventureId, adventureDescription,
				experiencePointsGained, type, criteria);
		quest.getAdventures().add(adventure);

		if (!OptionsManager.getSingleton().isUsingMockDataSource())
		{
			try
			{
				new AdventureRowDataGatewayRDS(adventureId, adventureDescription, questID, experiencePointsGained, type,
						criteria);
			}
			catch (DatabaseException e)
			{
				return false;
			}
		}
		else
		{
			new AdventureRowDataGatewayMock(adventureId, adventureDescription, questID, experiencePointsGained, type,
					criteria);
		}

		sendQuestReport();

		return true;
	}

	/**
	 * Delete an adventure
	 *
	 * @param questId
	 *            quest
	 * @param adventureId
	 *            adventure
	 * @return True if successful and false if not
	 */
	public boolean deleteAdventure(int questId, int adventureId)
	{
		try
		{
			AdventureRowDataGateway gateway = getAdventureRowDataGateway(questId, adventureId);
			QuestRecord quest = getQuest(questId);
			quest.getAdventures().remove(quest.getAdventureD(adventureId));
			gateway.removeAdventure();
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
		AllQuestsAndAdventuresReport report = new AllQuestsAndAdventuresReport(this.getQuests());
		QualifiedObservableConnector.getSingleton().sendReport(report);
	}

	/**
	 * Sends a report containing adventures that have not been completed based on a
	 * playerID
	 * @param playerID the one we are interested in
	 *
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	public void sendUncompletedAdventuresReport(int playerID) throws DatabaseException
	{
		PlayersUncompletedAdventuresReport report = new PlayersUncompletedAdventuresReport(new PlayerMapper(playerID).getIncompleteAdventures());
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
			ArrayList<AdventureRecord> adventureList = quest.getAdventures();

			for (AdventureRecord a : adventureList)
			{
				AdventureRowDataGateway advGateway = getAdventureRowDataGateway(questID, a.getAdventureID());
				advGateway.removeAdventure();
			}
			quest.setAdventures(new ArrayList<>());
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
	 * @throws DatabaseException if we fail to delete all of the adventures
	 */
	protected void removeAllQuestsAdventures() throws DatabaseException
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
			this.adventureStateGateway = AdventureStateTableDataGatewayMock.getSingleton();

		}
		else
		{
			this.questStateGateway = QuestStateTableDataGatewayRDS.getSingleton();
			this.adventureStateGateway = AdventureStateTableDataGatewayRDS.getSingleton();
		}
		questStateGateway.udpateState(playerID, questID, QuestStateEnum.TRIGGERED, false);
		ArrayList<AdventureRecord> adventureList = this.getAdventureTableGateway().getAdventuresForQuest(questID);
		for (AdventureRecord a : adventureList)
		{
			adventureStateGateway.updateState(playerID, questID, a.getAdventureID(), AdventureStateEnum.TRIGGERED,
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

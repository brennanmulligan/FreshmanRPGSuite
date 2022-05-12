package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import criteria.NPCResponseDTO;
import criteria.ObjectiveCompletionCriteria;
import criteria.CriteriaIntegerDTO;
import criteria.CriteriaStringDTO;
import dataENUM.ObjectiveCompletionType;
import datasource.ObjectiveTableDataGateway;
import datasource.ObjectiveTableDataGatewayMock;
import datasource.ObjectiveTableDataGatewayRDS;
import datasource.DatabaseException;
import datasource.FriendTableDataGateway;
import datasource.FriendTableDataGatewayMock;
import datasource.FriendTableDataGatewayRDS;
import datasource.QuestRowDataGateway;
import datasource.QuestRowDataGatewayMock;
import datasource.QuestRowDataGatewayRDS;
import datatypes.ObjectiveStateEnum;
import datatypes.Position;
import datatypes.QuestStateEnum;
import model.reports.InteractableObjectBuffReport;
import model.reports.InteractableObjectTextReport;
import model.reports.KeyInputRecievedReport;
import model.reports.DoubloonChangeReport;
import model.reports.PlayerConnectionReport;
import model.reports.PlayerLeaveReport;
import model.reports.PlayerMovedReport;
import model.reports.ReceiveTerminalTextReport;
import model.reports.ChatMessageReceivedReport;
import model.reports.FriendConnectionReceivedReport;

/**
 * Retrieves the list of quest and objectives from the database and sends them
 * to the PlayerManager?
 *
 * @author lavonne
 */
public class QuestManager implements QualifiedObserver
{
	private ObjectiveTableDataGateway objectiveGateway;
	private HashMap<Integer, ArrayList<QuestState>> questStates;
	private FriendTableDataGateway friendGateway;

	/**
	 * The method returns a singleton of QuestManager
	 *
	 * @return the only QuestManager in the system
	 * @throws DatabaseException
	 */
	public synchronized static QuestManager getSingleton()
	{
		if (singleton == null)
		{
			singleton = new QuestManager();
		}

		return singleton;
	}

	private QuestManager()
	{
		QualifiedObservableConnector.getSingleton().registerObserver(this, PlayerMovedReport.class);
		QualifiedObservableConnector.getSingleton().registerObserver(this, PlayerLeaveReport.class);
		QualifiedObservableConnector.getSingleton().registerObserver(this, KeyInputRecievedReport.class);
		QualifiedObservableConnector.getSingleton().registerObserver(this, ChatMessageReceivedReport.class);
		QualifiedObservableConnector.getSingleton().registerObserver(this, DoubloonChangeReport.class);
		QualifiedObservableConnector.getSingleton().registerObserver(this, PlayerConnectionReport.class);
		QualifiedObservableConnector.getSingleton().registerObserver(this, InteractableObjectBuffReport.class);
		QualifiedObservableConnector.getSingleton().registerObserver(this, InteractableObjectTextReport.class);
		QualifiedObservableConnector.getSingleton().registerObserver(this, FriendConnectionReceivedReport.class);
		QualifiedObservableConnector.getSingleton().registerObserver(this, ReceiveTerminalTextReport.class);

		questStates = new HashMap<>();
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			this.objectiveGateway = ObjectiveTableDataGatewayMock.getSingleton();
			this.friendGateway = FriendTableDataGatewayMock.getSingleton();
		}
		else
		{
			this.objectiveGateway = ObjectiveTableDataGatewayRDS.getSingleton();
			try
			{
				this.friendGateway = FriendTableDataGatewayRDS.getInstance();
			}
			catch (DatabaseException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Reset the singleton to null
	 */
	public static void resetSingleton()
	{
		if (singleton != null)
		{
			singleton = null;
		}
	}

	private static QuestManager singleton;

	/**
	 * Gets a specific quest from the database. Creates a Quest and passes it on the
	 * player mapper?
	 *
	 * @param questID
	 *            the quest to retrieve
	 * @return quest the retrieved request
	 * @throws DatabaseException
	 *             throw an exception if the quest id isn't found
	 */
	public QuestRecord getQuest(int questID) throws DatabaseException
	{

		QuestRowDataGateway questGateway;

		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			questGateway = new QuestRowDataGatewayMock(questID);
		}
		else
		{
			questGateway = new QuestRowDataGatewayRDS(questID);
		}

		QuestRecord quest = new QuestRecord(questGateway.getQuestID(), questGateway.getQuestTitle(),
				questGateway.getQuestDescription(), questGateway.getTriggerMapName(), questGateway.getTriggerPosition(),
				objectiveGateway.getObjectivesForQuest(questID), questGateway.getExperiencePointsGained(),
				questGateway.getObjectivesForFulfillment(), questGateway.getCompletionActionType(),
				questGateway.getCompletionActionParameter(), questGateway.getStartDate(), questGateway.getEndDate());

		return quest;
	}

	/**
	 * Return the quests that are associated with a certain map and position
	 *
	 * @param pos
	 *            the position of the quest
	 * @param mapName
	 *            the map that the quest is on
	 * @return an array list of questIDs
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public ArrayList<Integer> getQuestsByPosition(Position pos, String mapName) throws DatabaseException
	{
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			return QuestRowDataGatewayMock.findQuestsForMapLocation(mapName, pos);
		}
		else
		{
			return QuestRowDataGatewayRDS.findQuestsForMapLocation(mapName, pos);
		}
	}

	/**
	 * Returns a list of ObjectiveRecord objects based on completion at specified
	 * map and position
	 *
	 * @param pos
	 *            - the position of the objective
	 * @param mapName
	 *            - the map that the objective is on
	 * @return an array list of ObjectiveRecords at this position
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public ArrayList<ObjectiveRecord> getObjectivesByPosition(Position pos, String mapName) throws DatabaseException
	{
		return objectiveGateway.findObjectivesCompletedForMapLocation(mapName, pos);
	}

	/**
	 * Trigger a quest for a given player
	 *
	 * @param playerID
	 *            the player
	 * @param questID
	 *            the quest to be triggered
	 * @throws IllegalObjectiveChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public void triggerQuest(int playerID, int questID)
			throws IllegalObjectiveChangeException, IllegalQuestChangeException, DatabaseException
	{
		QuestState qs = getQuestStateByID(playerID, questID);
		Calendar.getInstance().set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
		Date now = Calendar.getInstance().getTime();
		QuestRecord q = getQuest(questID);

		if ((qs != null) && (qs.getStateValue() != QuestStateEnum.TRIGGERED))
		{
			if (now.after(q.getStartDate()) && now.before(q.getEndDate()))
			{
				qs.trigger();

			}
		}
	}

	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		if (report.getClass() == PlayerMovedReport.class)
		{
			handlePlayerMovement(report);
		}
		else if (report.getClass() == PlayerLeaveReport.class)
		{
			PlayerLeaveReport myReport = (PlayerLeaveReport) report;
			removeQuestStatesForPlayer(myReport.getPlayerID());
		}
		else if (report.getClass() == KeyInputRecievedReport.class)
		{
			handlePlayerInput(report);
		}
		else if (report.getClass() == ChatMessageReceivedReport.class)
		{
			handlePlayerChatCriteriaCompletion(report);
			handlePlayerChatReceivedCriteriaCompletion(report);
		}
		else if (report.getClass() == DoubloonChangeReport.class)
		{
			handleDoubloonsChanged(report);
		}
		else if (report.getClass() == PlayerConnectionReport.class)
		{
			PlayerConnectionReport myReport = (PlayerConnectionReport) report;
			try
			{
				triggerQuestsForPosition(myReport.getPosition(), OptionsManager.getSingleton().getMapName(),
						myReport.getPlayerID());
			}
			catch (DatabaseException e)
			{
				e.printStackTrace();
			}
			catch (IllegalObjectiveChangeException e)
			{
				e.printStackTrace();
			}
			catch (IllegalQuestChangeException e)
			{
				e.printStackTrace();
			}
		}
		else if (report.getClass() == InteractableObjectBuffReport.class)
		{
			handleInteractableObjectBuffReport(report);

		}
		else if (report.getClass() == InteractableObjectTextReport.class)
		{
			handleInteractableObjectTextReport(report);

		}
		else if (report.getClass() == FriendConnectionReceivedReport.class)
		{

			handleFriends(report);

		}
		else if (report.getClass() == ReceiveTerminalTextReport.class)
		{
			handleReceiveTerminalTextReport(report);

		}
	}

	/**
	 * method to handle friends
	 * @param report
	 * @throws DatabaseException
	 */
	public void handleFriends(QualifiedObservableReport report)
	{
		QuestManager qm = QuestManager.getSingleton();
		FriendConnectionReceivedReport friendship = (FriendConnectionReceivedReport) report;
		int senderID = friendship.getSenderID();
		int receiverID = friendship.getReceiverID();

		//check objectives for the sender
		ArrayList<QuestState> questStates = qm.getQuestList(senderID);

		ArrayList<ObjectiveRecord> objectivesForCompletion = new ArrayList<>();

		for (QuestState q : questStates)
		{
			objectivesForCompletion.addAll(getObjectivesByFriend(q.getID(), q.getPlayerID()));
		}

		try
		{
			if (objectivesForCompletion.size() != 0)
			{
				for (ObjectiveRecord a : objectivesForCompletion)
				{
					CriteriaIntegerDTO criteria = (CriteriaIntegerDTO) a.getCompletionCriteria();

					if (criteria.getTarget() == friendGateway.getFriendCounter(senderID))
					{
						qm.completeObjective(senderID, a.getQuestID(), a.getObjectiveID());
					}
				}
			}
		}
		catch (DatabaseException | IllegalObjectiveChangeException | IllegalQuestChangeException e)
		{
			System.out.println("player does not exist or could not find friends");
			e.printStackTrace();
		}

		//check objectives for the receiver
		ArrayList<QuestState> questStates2 = qm.getQuestList(receiverID);

		ArrayList<ObjectiveRecord> objectivesForCompletion2 = new ArrayList<>();

		for (QuestState q : questStates2)
		{
			objectivesForCompletion2.addAll(getObjectivesByFriend(q.getID(), q.getPlayerID()));
		}

		try
		{
			if (objectivesForCompletion2.size() != 0)
			{
				for (ObjectiveRecord a : objectivesForCompletion2)
				{
					CriteriaIntegerDTO criteria2 = (CriteriaIntegerDTO) a.getCompletionCriteria();

					if (criteria2.getTarget() == friendGateway.getFriendCounter(receiverID))
					{
						qm.completeObjective(receiverID, a.getQuestID(), a.getObjectiveID());
					}
				}
			}
		}
		catch (DatabaseException | IllegalObjectiveChangeException | IllegalQuestChangeException e)
		{
			System.out.println("player does not exist or could not find friends");
			e.printStackTrace();
		}
	}

	/**
	 * Iterates through all quests and objectives and validates if it's the correct
	 * criteria for keyboard input.
	 *
	 * @param report
	 */
	private void handlePlayerInput(QualifiedObservableReport report)
	{
		QuestManager qm = QuestManager.getSingleton();
		KeyInputRecievedReport myReport = (KeyInputRecievedReport) report;
		ArrayList<QuestState> questStates = qm.getQuestList(myReport.getPlayerId());
		if (questStates != null)
		{
			for (QuestState qs : questStates)
			{
				for (ObjectiveState as : qs.getObjectiveList())
				{

					validateInputCriteriaForObjectives(myReport.getInput(), myReport.getPlayerId(), qs.getID(),
							as.getID());
				}
			}
		}
	}

	/**
	 * Will listen for SendChatMessageReports and check to see if they help complete
	 * any of the current objective that a player is doing Objective must be off
	 * ObjectiveCompletion type chat and the players must be within a certain
	 * distance of each other
	 */
	private void handlePlayerChatCriteriaCompletion(QualifiedObservableReport report)
	{
		QuestManager qm = QuestManager.getSingleton();
		ChatMessageReceivedReport myReport = (ChatMessageReceivedReport) report;
		try
		{
			int reportPlayerID = myReport.getSenderID();
			ArrayList<QuestState> questStateList = qm.getQuestList(reportPlayerID);

			if (questStateList != null)
			{
				for (QuestState q : questStateList)
				{
					checkAllChatObjectivesForCompletion(reportPlayerID, q);
				}
			}
		}
		catch (PlayerNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Check all chat reports that are received from an NPC and passes along the report
	 * @param report
	 */
	private void handlePlayerChatReceivedCriteriaCompletion(QualifiedObservableReport report)
	{
		ChatMessageReceivedReport myReport = (ChatMessageReceivedReport) report;
		try
		{
			int reportNPCID = myReport.getSenderID();
			int reportPlayerID = myReport.getReceiverID();
			String reportChat = myReport.getChatText();
			ArrayList<QuestState> questStateList = getQuestList(reportPlayerID);

			if (questStateList != null)
			{
				for (QuestState q : questStateList)
				{
					checkAllChatReceivedObjectivesForCompletion(reportPlayerID, reportNPCID, reportChat, q);
				}
			}
		}
		catch (PlayerNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Checks if the received chat report meets the criteria to complete the quest
	 * @param reportPlayerID
	 * @param q
	 */
	private void checkAllChatReceivedObjectivesForCompletion(int reportPlayerID, int reportNPCID, String reportChat, QuestState q) throws PlayerNotFoundException
	{
		PlayerManager PM = PlayerManager.getSingleton();
		for (ObjectiveRecord a : getPendingChatObjectives(q.getID(), reportPlayerID, ObjectiveCompletionType.CHAT_RECEIVED))
		{
			ObjectiveCompletionCriteria criteria = a.getCompletionCriteria();
			if (criteria.getClass().equals(NPCResponseDTO.class))
			{
				NPCResponseDTO castCrit = (NPCResponseDTO) criteria;
				Player npc = PM.getPlayerFromID(reportNPCID);
				Player player = PM.getPlayerFromID(reportPlayerID);
				if (player.canReceiveLocalMessage(npc.getPlayerPosition()))
				{
					if(reportChat.equals(castCrit.getResponse())
							&& PM.getPlayerIDFromPlayerName(castCrit.getNPCName()) == reportNPCID)
					{
						try
						{
							QuestManager.getSingleton().completeObjective(reportPlayerID, q.getID(), a.getObjectiveID());

						}
						catch (DatabaseException | IllegalObjectiveChangeException | IllegalQuestChangeException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * Gets pending chat objectives of chatCompletionType
	 * @param questID the quest to get objectives for
	 * @param reportPlayerID the player who sent the message
	 * @param chatCompletionType the chat completion type
	 * @return ArrayList of ObjectiveRecords of chat completion type
	 */
	public ArrayList<ObjectiveRecord> getPendingChatObjectives(int questID, int reportPlayerID,
															   ObjectiveCompletionType chatCompletionType)
	{
		ArrayList<ObjectiveRecord> questObjectives = new ArrayList<>();
		try
		{

			for (ObjectiveRecord AR : getQuest(questID).getObjectives())
			{
				ObjectiveState objectiveState = getObjectiveStateByID(reportPlayerID, questID, AR.getObjectiveID());
				if (objectiveState != null)
				{
					ObjectiveStateEnum currentObjectivesState = objectiveState.getState();
					if (ObjectiveStateEnum.TRIGGERED == currentObjectivesState)
					{
						if (AR.getCompletionType() == chatCompletionType)
						{
							questObjectives.add(AR);
						}
					}
				}
			}
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}

		return questObjectives;
	}

	/**
	 * @param report
	 */
	protected void handleDoubloonsChanged(QualifiedObservableReport report)
	{
		QuestManager qm = QuestManager.getSingleton();
		DoubloonChangeReport myReport = (DoubloonChangeReport) report;
		PlayerManager PM = PlayerManager.getSingleton();

		ArrayList<QuestState> questStates = qm.getQuestList(myReport.getPlayerID());

		ArrayList<ObjectiveRecord> objectivesForCompletion = new ArrayList<>();

		for (QuestState q : questStates)
		{
			objectivesForCompletion.addAll(getObjectivesByDoubloons(q.getID(), q.getPlayerID()));
		}

		try
		{
			if (objectivesForCompletion.size() != 0)
			{
				for (ObjectiveRecord a : objectivesForCompletion)
				{
					CriteriaIntegerDTO criteria = (CriteriaIntegerDTO) a.getCompletionCriteria();

					if (criteria.getTarget() <= PM.getPlayerFromID(myReport.getPlayerID()).getQuizScore())
					{
						qm.completeObjective(myReport.getPlayerID(), a.getQuestID(), a.getObjectiveID());
					}
				}
			}
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Will listen for ReceiveTerminalTextReport and check to see if they help complete
	 * any of the current objective that a player is doing Objective must be of
	 * ObjectiveCompletion type terminal
	 */
	protected void handleReceiveTerminalTextReport(QualifiedObservableReport report)
	{
		QuestManager qm = QuestManager.getSingleton();
		ReceiveTerminalTextReport myReport = (ReceiveTerminalTextReport) report;

		ArrayList<QuestState> questStates = qm.getQuestList(myReport.getPlayerID());

		ArrayList<ObjectiveRecord> objectivesForCompletion = new ArrayList<>();

		for (QuestState q : questStates)
		{
			objectivesForCompletion.addAll(getObjectivesByTerminal(q.getID(), q.getPlayerID()));
		}

		try
		{
			if (objectivesForCompletion.size() != 0)
			{
				for (ObjectiveRecord a : objectivesForCompletion)
				{
					CriteriaStringDTO criteria = (CriteriaStringDTO) a.getCompletionCriteria();

					if (criteria.getString().equals(myReport.getCommand()))
					{
						qm.completeObjective(myReport.getPlayerID(), a.getQuestID(), a.getObjectiveID());
					}
				}
			}
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void handleInteractableObjectBuffReport(QualifiedObservableReport report)
	{
		QuestManager qm = QuestManager.getSingleton();
		InteractableObjectBuffReport myReport = (InteractableObjectBuffReport) report;
		PlayerManager.getSingleton();

		ArrayList<QuestState> questStates = qm.getQuestList(myReport.getPlayerID());

		ArrayList<ObjectiveRecord> objectivesForCompletion = new ArrayList<>();

		for (QuestState q : questStates)
		{
			objectivesForCompletion.addAll(getObjectivesByInteractableObject(q.getID(), q.getPlayerID()));
		}

		try
		{
			if (objectivesForCompletion.size() != 0)
			{
				for (ObjectiveRecord a : objectivesForCompletion)
				{
					CriteriaIntegerDTO criteria = (CriteriaIntegerDTO) a.getCompletionCriteria();

					if (criteria.getTarget() == InteractObjectManager.getSingleton()
							.objectInRange(myReport.getPlayerID()))
					{
						qm.completeObjective(myReport.getPlayerID(), a.getQuestID(), a.getObjectiveID());
					}
				}
			}
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void handleInteractableObjectTextReport(QualifiedObservableReport report)
	{
		QuestManager qm = QuestManager.getSingleton();
		InteractableObjectTextReport myReport = (InteractableObjectTextReport) report;
		PlayerManager.getSingleton();

		ArrayList<QuestState> questStates = qm.getQuestList(myReport.getPlayerID());

		ArrayList<ObjectiveRecord> objectivesForCompletion = new ArrayList<>();

		for (QuestState q : questStates)
		{
			objectivesForCompletion.addAll(getObjectivesByInteractableObject(q.getID(), q.getPlayerID()));
		}

		try
		{
			if (objectivesForCompletion.size() != 0)
			{
				for (ObjectiveRecord a : objectivesForCompletion)
				{
					CriteriaIntegerDTO criteria = (CriteriaIntegerDTO) a.getCompletionCriteria();

					if (criteria.getTarget() == InteractObjectManager.getSingleton()
							.objectInRange(myReport.getPlayerID()))
					{
						qm.completeObjective(myReport.getPlayerID(), a.getQuestID(), a.getObjectiveID());
					}
				}
			}
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * checks if objective meets chat criteria and completes it if it does
	 *
	 * @param reportPlayerID
	 *            player who sent chat message
	 * @param q
	 *            quest to get objective for
	 * @throws PlayerNotFoundException
	 */
	private void checkAllChatObjectivesForCompletion(int reportPlayerID, QuestState q) throws PlayerNotFoundException
	{
		PlayerManager PM = PlayerManager.getSingleton();
		for (ObjectiveRecord a : getPendingChatObjectives(q.getID(), reportPlayerID, ObjectiveCompletionType.CHAT))
		{
			ObjectiveCompletionCriteria criteria = a.getCompletionCriteria();
			if (criteria.getClass().equals(CriteriaStringDTO.class))
			{
				CriteriaStringDTO castCrit = (CriteriaStringDTO) criteria;
				Player npc = PM.getPlayerFromID(PM.getPlayerIDFromPlayerName(castCrit.getString()));
				if (PM.getPlayerFromID(reportPlayerID).canReceiveLocalMessage(npc.getPlayerPosition()))
				{
					try
					{
						QuestManager.getSingleton().completeObjective(reportPlayerID, q.getID(), a.getObjectiveID());

					}
					catch (DatabaseException | IllegalObjectiveChangeException | IllegalQuestChangeException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * @param questID
	 *            the id of the quest we're checking
	 * @param reportPlayerId
	 *            the player's id
	 * @return the list of points objectives ready for completion
	 */
	public ArrayList<ObjectiveRecord> getObjectivesByDoubloons(int questID, int reportPlayerId)
	{
		ArrayList<ObjectiveRecord> questObjectives = new ArrayList<>();
		ArrayList<ObjectiveRecord> pointsObjectives = new ArrayList<>();

		try
		{
			questObjectives = QuestManager.getSingleton().getQuest(questID).getObjectives();
			if (questObjectives.size() != 0)
			{
				for (ObjectiveRecord A : questObjectives)
				{
					ObjectiveState currentState = QuestManager.getSingleton().getObjectiveStateByID(reportPlayerId,
							questID, A.getObjectiveID());

					if (A.getCompletionType() == ObjectiveCompletionType.DOUBLOONS && currentState != null
							&& currentState.getState() == ObjectiveStateEnum.TRIGGERED)
					{
						pointsObjectives.add(A);
					}
				}
			}
		}

		catch (DatabaseException e)
		{
			e.printStackTrace();
		}

		return pointsObjectives;
	}


	/**
	 * @param questID - the id of the quest we're checking
	 * @param reportPlayerID - the player's id
	 * @return questObjectives - all objectives that have completionType chat
	 */
	public ArrayList<ObjectiveRecord> getObjectivesByTerminal(int questID, int reportPlayerID)
	{
		ArrayList<ObjectiveRecord> questObjectives = new ArrayList<>();
		try
		{

			for (ObjectiveRecord AR : getQuest(questID).getObjectives())
			{
				ObjectiveState objectiveState = getObjectiveStateByID(reportPlayerID, questID, AR.getObjectiveID());
				if (objectiveState != null)
				{
					ObjectiveStateEnum currentObjectivesState = objectiveState.getState();
					if (ObjectiveStateEnum.TRIGGERED == currentObjectivesState)
					{
						if (AR.getCompletionType() == ObjectiveCompletionType.TERMINAL)
						{
							questObjectives.add(AR);
						}
					}
				}
			}
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}

		return questObjectives;
	}

	private ArrayList<ObjectiveRecord> getObjectivesByInteractableObject(int questID, int reportPlayerId)
	{
		ArrayList<ObjectiveRecord> questObjectives = new ArrayList<>();
		ArrayList<ObjectiveRecord> pointsObjectives = new ArrayList<>();

		try
		{
			questObjectives = QuestManager.getSingleton().getQuest(questID).getObjectives();
			if (questObjectives.size() != 0)
			{
				for (ObjectiveRecord A : questObjectives)
				{
					ObjectiveState currentState = QuestManager.getSingleton().getObjectiveStateByID(reportPlayerId,
							questID, A.getObjectiveID());

					if (A.getCompletionType() == ObjectiveCompletionType.INTERACT && currentState != null
							&& currentState.getState() == ObjectiveStateEnum.TRIGGERED)
					{
						pointsObjectives.add(A);
					}
				}
			}
		}

		catch (DatabaseException e)
		{
			e.printStackTrace();
		}

		return pointsObjectives;
	}

	/**
	 * added for friends 
	 * @param questID
	 * @param reportPlayerId
	 * @return
	 */
	public ArrayList<ObjectiveRecord> getObjectivesByFriend(int questID, int reportPlayerId)
	{
		ArrayList<ObjectiveRecord> questObjectives = new ArrayList<>();
		ArrayList<ObjectiveRecord> pointsObjectives = new ArrayList<>();

		try
		{
			questObjectives = QuestManager.getSingleton().getQuest(questID).getObjectives();
			if (questObjectives.size() != 0)
			{
				for (ObjectiveRecord A : questObjectives)
				{
					ObjectiveState currentState = QuestManager.getSingleton().getObjectiveStateByID(reportPlayerId,
							questID, A.getObjectiveID());

					if (A.getCompletionType() == ObjectiveCompletionType.FRIENDS && currentState != null
							&& currentState.getState() == ObjectiveStateEnum.TRIGGERED)
					{
						pointsObjectives.add(A);
					}
				}
			}
		}

		catch (DatabaseException e)
		{
			e.printStackTrace();
		}

		return pointsObjectives;
	}

	/**
	 * returns all ObjectiveRecords which the type is chat
	 *
	 * @param questID
	 *            the quest to get objectives for
	 * @param reportPlayerID
	 *            the player who sent a chat message
	 * @return all objectives that have completionType chat
	 */
	public ArrayList<ObjectiveRecord> getPendingChatObjectives(int questID, int reportPlayerID)
	{
		ArrayList<ObjectiveRecord> questObjectives = new ArrayList<>();
		try
		{

			for (ObjectiveRecord AR : getQuest(questID).getObjectives())
			{
				ObjectiveState objectiveState = getObjectiveStateByID(reportPlayerID, questID, AR.getObjectiveID());
				if (objectiveState != null)
				{
					ObjectiveStateEnum currentObjectivesState = objectiveState.getState();
					if (ObjectiveStateEnum.TRIGGERED == currentObjectivesState)
					{
						if (AR.getCompletionType() == ObjectiveCompletionType.CHAT)
						{
							questObjectives.add(AR);
						}
					}
				}
			}
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}

		return questObjectives;
	}

	/**
	 * Validates if an input string matches the criteria string.
	 *
	 * @param input
	 * @param playerId
	 * @param questId
	 * @param objectiveId
	 */
	private void validateInputCriteriaForObjectives(String input, int playerId, int questId, int objectiveId)
	{
		QuestManager qm = QuestManager.getSingleton();
		try
		{
			ObjectiveRecord ar = getObjective(questId, objectiveId);
			if (ar.getCompletionType() == ObjectiveCompletionType.KEYSTROKE && qm
					.getObjectiveStateByID(playerId, questId, objectiveId).getState() == ObjectiveStateEnum.TRIGGERED)
			{
				CriteriaStringDTO cs = (CriteriaStringDTO) ar.getCompletionCriteria();
				if (cs.getString().equalsIgnoreCase(input))
				{
					qm.completeObjective(playerId, questId, objectiveId);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void handlePlayerMovement(QualifiedObservableReport report)
	{
		PlayerMovedReport myReport = (PlayerMovedReport) report;
		Position position = myReport.getNewPosition();
		String mapName = myReport.getMapName();
		int playerID = myReport.getPlayerID();
		try
		{
			triggerQuestsForPosition(position, mapName, playerID);

			ArrayList<ObjectiveRecord> objectives = getObjectivesByPosition(position, mapName);
			for (ObjectiveRecord a : objectives)
			{
				ObjectiveState objectiveStateByID = getObjectiveStateByID(playerID, a.getQuestID(), a.getObjectiveID());
				if (objectiveStateByID != null && objectiveStateByID.getState() != ObjectiveStateEnum.COMPLETED)
				{
					this.completeObjective(playerID, a.getQuestID(), a.getObjectiveID());
				}
			}

		}
		catch (DatabaseException | IllegalObjectiveChangeException | IllegalQuestChangeException e)
		{
			e.printStackTrace();
		}
	}

	private void triggerQuestsForPosition(Position position, String mapName, int playerID)
			throws DatabaseException, IllegalObjectiveChangeException, IllegalQuestChangeException
	{
		QuestManager qm = QuestManager.getSingleton();
		ArrayList<Integer> questIDs = new ArrayList<>();

		questIDs = qm.getQuestsByPosition(position, mapName);

		for (Integer q : questIDs)
		{
			this.triggerQuest(playerID, q);
		}
	}

	/**
	 * Add a quest to the player's questList
	 *
	 * @param playerID
	 *            the player we are adding the quest id
	 * @param quest
	 *            the quest being added
	 */
	public void addQuestState(int playerID, QuestState quest)
	{
		ArrayList<QuestState> questStateList;
		if (!questStates.containsKey(playerID))
		{
			questStateList = new ArrayList<>();
			questStates.put(playerID, questStateList);
		}
		else
		{
			questStateList = questStates.get(playerID);
		}
		questStateList.add(quest);
		quest.setPlayerID(playerID);
	}

	/**
	 * Go through the questList and get the state of the quest based on the id
	 *
	 * @param playerID
	 *            the id of the player
	 * @param questID
	 *            the id of the quest we are interested in
	 * @return the state of the quest
	 */
	QuestState getQuestStateByID(int playerID, int questID)
	{

		ArrayList<QuestState> questStateList = QuestManager.getSingleton().getQuestList(playerID);
		QuestRecord quest = null;
		try
		{
			quest = QuestManager.getSingleton().getQuest(questID);
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}

		QuestState qState = null;

		if (questStateList != null)
		{
			for (QuestState q : questStateList)
			{
				if (q.getID() == questID)
				{
					qState = q;
				}
			}
		}
		if (qState == null)
		{
			Date now = Calendar.getInstance().getTime();
			if (now.after(quest.getStartDate()))
			{
				qState = new QuestState(playerID, questID, QuestStateEnum.AVAILABLE, true);
				try
				{
					quest = getQuest(questID);
					ArrayList<ObjectiveState> advStates = new ArrayList<>();
					for (ObjectiveRecord objective : quest.getObjectives())
					{
						advStates.add(new ObjectiveState(objective.getObjectiveID(), ObjectiveStateEnum.HIDDEN, false));
					}
					qState.addObjectives(advStates);
					addQuestState(playerID, qState);
				}
				catch (DatabaseException e)
				{
					e.printStackTrace();
				}

			}
		}
		if (quest != null && qState != null)
		{
			Date now = Calendar.getInstance().getTime();
			if (now.after(quest.getEndDate()) && qState.getStateValue() != QuestStateEnum.EXPIRED
					&& qState.getStateValue() != QuestStateEnum.COMPLETED)
			{
				try
				{
					qState.changeState(QuestStateEnum.EXPIRED, qState.isNeedingNotification());
				}
				catch (IllegalQuestChangeException | DatabaseException e)
				{
					e.printStackTrace();
				}
			}
		}

		return qState;
	}

	/**
	 * Use the id's of the player quest and objective to find a specified objective
	 * state
	 *
	 * @param playerID
	 *            the id of the player
	 * @param questID
	 *            the id of the quest
	 * @param objectiveID
	 *            the id of the objective
	 * @return the state of the objectivenew Grego
	 */
	ObjectiveState getObjectiveStateByID(int playerID, int questID, int objectiveID)
	{

		ArrayList<QuestState> questStateList = questStates.get(playerID);

		for (QuestState q : questStateList)
		{

			if (q.getID() == questID)
			{

				ArrayList<ObjectiveState> objectiveList = q.getObjectiveList();

				for (ObjectiveState a : objectiveList)
				{
					if (a.getID() == objectiveID)
					{

						return a;
					}
				}

			}
		}

		return null;
	}

	/**
	 * Get a list of all of the current quest states for a given player
	 *
	 * @param playerID
	 *            the player's id
	 * @return the states
	 */
	public ArrayList<QuestState> getQuestList(int playerID)
	{
		return questStates.get(playerID);
	}

	/**
	 * Remove all of the quest states for a given player
	 *
	 * @param playerID
	 *            the player we are removing
	 */
	public void removeQuestStatesForPlayer(int playerID)
	{
		questStates.remove(playerID);
	}

	/**
	 * Get the information about a specific objective
	 *
	 * @param questID
	 *            the quest that contains the objective
	 * @param objectiveID
	 *            the objective ID within that quest
	 * @return the requested details
	 * @throws DatabaseException
	 *             if the data source can't respond well
	 */
	public ObjectiveRecord getObjective(int questID, int objectiveID) throws DatabaseException
	{
		return this.objectiveGateway.getObjective(questID, objectiveID);
	}

	/**
	 * If quest state is not null, finish
	 *
	 * @param playerID
	 *            the player ID
	 * @param questID
	 *            the quest ID
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public void completeQuest(int playerID, int questID) throws IllegalQuestChangeException, DatabaseException
	{
		QuestState qs = getQuestStateByID(playerID, questID);
		if (qs != null)
		{
			qs.complete();
		}
	}

	/**
	 * Get the objective state from the id's and set the objective state to complete
	 * if it is pending
	 *
	 * @param playerID
	 *            the id of the player
	 * @param questID
	 *            the id of the quest
	 * @param objectiveID
	 *            the id of the objective
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalObjectiveChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 */
	public void completeObjective(int playerID, int questID, int objectiveID)
			throws DatabaseException, IllegalObjectiveChangeException, IllegalQuestChangeException
	{
		ObjectiveState objectiveStateByID = getObjectiveStateByID(playerID, questID, objectiveID);


		if (objectiveStateByID != null)
		{

			objectiveStateByID.complete();
		}

	}

	/**
	 * Set needing notification to false
	 *
	 * @param playerID
	 *            the id of the player
	 * @param questID
	 *            the id of the quest
	 * @param objectiveID
	 *            the id of the objective
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalObjectiveChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 */
	public void turnOffNotification(int playerID, int questID, int objectiveID)
			throws DatabaseException, IllegalObjectiveChangeException, IllegalQuestChangeException
	{
		getObjectiveStateByID(playerID, questID, objectiveID).turnOffNotification();
	}

	/**
	 * Called when the user no longer needs to be notified about the current state
	 * of a quest
	 *
	 * @param playerID
	 *            the player
	 * @param questID
	 *            the quest that should be quieted
	 */
	public void turnOffQuestNotification(int playerID, int questID)
	{
		QuestState qs = getQuestStateByID(playerID, questID);
		qs.setNeedingNotification(false);
	}
}

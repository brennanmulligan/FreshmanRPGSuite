package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import datatypes.FriendStatusEnum;
import datatypes.PlayersForTest;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import criteria.GameLocationDTO;
import datasource.DatabaseException;
import datasource.FriendDBMock;
import datasource.FriendTableDataGateway;
import datasource.FriendTableDataGatewayMock;
import datasource.QuestStateTableDataGatewayMock;
import datatypes.AdventureStateEnum;
import datatypes.ChatType;
import datatypes.Position;
import datatypes.QuestStateEnum;
import model.reports.KeyInputRecievedReport;
import model.reports.KnowledgePointsChangeReport;
import model.reports.PlayerLeaveReport;
import model.reports.ReceiveTerminalTextReport;
import model.reports.ChatMessageReceivedReport;
import model.reports.FriendConnectionReceivedReport;
import model.reports.TeleportOnQuestCompletionReport;
import datatypes.AdventuresForTest;
import datatypes.InteractableItemsForTest;
import datatypes.QuestStatesForTest;
import datatypes.QuestsForTest;

/**
 * Test for the quest manager getting quests and adventures from database
 *
 * @author lavonnediller
 *
 */
public class QuestManagerTest
{

	/**
	 * Set up Options Manager, run in TestMode
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		QualifiedObservableConnector.resetSingleton();
		PlayerManager.resetSingleton();
		playerManager = PlayerManager.getSingleton();
		QuestStateTableDataGatewayMock.getSingleton().resetData();
		QuestManager.resetSingleton();
		QuestManager.getSingleton();
		InteractObjectManager.resetSingleton();
		new FriendDBMock();
	}

	/**
	 * Test initializing a quest manager
	 */
	@Test
	public void testInitialization()
	{
		QuestManager.resetSingleton();
		QuestManager questManager = QuestManager.getSingleton();
		assertNotNull(questManager);
	}

	/**
	 * Make sure QuestManager is a resetable singleton
	 */
	@Test
	public void testIsSingleton()
	{
		QuestManager qm1 = QuestManager.getSingleton();
		QuestManager qm2 = QuestManager.getSingleton();

		assertSame(qm1, qm2);

		QuestManager.resetSingleton();
		assertNotSame(qm1, QuestManager.getSingleton());
	}

	/**
	 * Test getting a quest from the database and storing it into our Quest Manager,
	 * using a Quest Row Data Gateway Mock
	 *
	 * @throws DatabaseException
	 *             throw an exception if the quest id isn't found
	 */
	@Test
	public void testGettingOneQuest() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();
		QuestsForTest expected = QuestsForTest.ONE_BIG_QUEST;
		QuestRecord actual = qm.getQuest(1);
		assertEquals(expected.getQuestID(), actual.getQuestID());
		assertEquals(expected.getQuestDescription(), actual.getDescription());
		assertEquals(expected.getMapName(), actual.getMapName());
		assertEquals(expected.getAdventuresForFulfillment(), actual.getAdventuresForFulfillment());
		assertEquals(expected.getExperienceGained(), actual.getExperiencePointsGained());
	}

	/**
	 * We should be able to retrieve data about a specific adventure
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testGettingOneAdventure() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();
		AdventuresForTest expected = AdventuresForTest.QUEST3_ADVENTURE1;
		AdventureRecord actual = qm.getAdventure(expected.getQuestID(), expected.getAdventureID());
		assertEquals(AdventuresForTest.QUEST3_ADVENTURE1.getAdventureDescription(), actual.getAdventureDescription());
		assertEquals(AdventuresForTest.QUEST3_ADVENTURE1.getAdventureID(), actual.getAdventureID());
		assertEquals(AdventuresForTest.QUEST3_ADVENTURE1.getExperiencePointsGained(),
				actual.getExperiencePointsGained());
		assertEquals(AdventuresForTest.QUEST3_ADVENTURE1.getQuestID(), actual.getQuestID());
	}

	/**
	 * If we ask for an adventure that doesn't exist, we should get null
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testGettingMissingAdventure() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();
		AdventureRecord actual = qm.getAdventure(42, 16);
		assertNull(actual);
	}

	/**
	 * Test getting two quests from the database
	 *
	 * @throws DatabaseException
	 *             throw an exception if the quest id isn't found
	 */
	@Test
	public void testGettingTwoQuests() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();

		assertEquals(1, qm.getQuest(1).getQuestID());
		assertEquals(QuestsForTest.ONE_BIG_QUEST.getQuestDescription(), qm.getQuest(1).getDescription());

		assertEquals(2, qm.getQuest(2).getQuestID());
		assertEquals(QuestsForTest.THE_OTHER_QUEST.getQuestDescription(), qm.getQuest(2).getDescription());
	}

	/**
	 * Test that we can get a specific quests adventures
	 *
	 * @throws DatabaseException
	 *             throw an exception if the quest id isn't found
	 */
	@Test
	public void testGettingAQuestsAdventures() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();
		QuestRecord quest = qm.getQuest(1);

		int i = 1;
		for (AdventureRecord a : quest.getAdventures())
		{
			if (i == 1)
			{
				assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureID(), a.getAdventureID());
				assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureDescription(),
						a.getAdventureDescription());
			}
			if (i == 2)
			{
				assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getAdventureID(), a.getAdventureID());
				assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getAdventureDescription(),
						a.getAdventureDescription());
			}
			i++;
		}
	}

	/**
	 * Test getting two quests and their two adventures from the db
	 *
	 * @throws DatabaseException
	 *             throw an exception if the quest id isn't found
	 */
	@Test
	public void testGettingTwoQuestsAndTheirAdventures() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();
		QuestRecord quest1 = qm.getQuest(1);
		QuestRecord quest2 = qm.getQuest(2);

		int i = 1;
		for (AdventureRecord a : quest1.getAdventures())
		{
			if (i == 1)
			{
				assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureID(), a.getAdventureID());
				assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureDescription(),
						a.getAdventureDescription());
			}
			if (i == 2)
			{
				assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getAdventureID(), a.getAdventureID());
				assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getAdventureDescription(),
						a.getAdventureDescription());
			}
			i++;
		}

		i = 1;
		for (AdventureRecord a : quest2.getAdventures())
		{
			if (i == 1)
			{
				assertEquals(AdventuresForTest.QUEST2_ADVENTURE1.getAdventureID(), a.getAdventureID());
				assertEquals(AdventuresForTest.QUEST2_ADVENTURE1.getAdventureDescription(),
						a.getAdventureDescription());
			}
			if (i == 2)
			{
				assertEquals(AdventuresForTest.QUEST2_ADVENTURE2.getAdventureID(), a.getAdventureID());
				assertEquals(AdventuresForTest.QUEST2_ADVENTURE2.getAdventureDescription(),
						a.getAdventureDescription());
			}
			i++;
		}
	}

	/**
	 * A Database Exception should be thrown if a quest id does not exist
	 *
	 * @throws DatabaseException
	 *             throw an exception if the quest id isn't found
	 */
	@SuppressWarnings("unused")
	@Test(expected = DatabaseException.class)
	public void testQuestDoesNotExits() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();
		QuestRecord quest1 = qm.getQuest(1000);
	}

	/**
	 * Test getting quests by a position and map name
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testGetQuestsPosition() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();

		Position pos = QuestsForTest.ONE_BIG_QUEST.getPosition();

		int index = 1;
		for (Integer i : qm.getQuestsByPosition(pos, QuestsForTest.ONE_BIG_QUEST.getMapName()))
		{
			if (index == 1)
			{
				assertEquals((int) i, QuestsForTest.ONE_BIG_QUEST.getQuestID());
			}
			if (index == 3)
			{
				assertEquals((int) i, QuestsForTest.ONE_SAME_LOCATION_QUEST.getQuestID());
			}
			index++;
		}
	}

	private PlayerManager playerManager;

	/**
	 * If there are no quests, we should get an empty arraylist - not null
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void notNullIfThereAreNoQuestsAtAPosition() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();

		Position pos = new Position(4, 4);
		ArrayList<Integer> actual = qm.getQuestsByPosition(pos, QuestsForTest.ONE_BIG_QUEST.getMapName());
		assertEquals(0, actual.size());
	}

	/**
	 * Test simple functionality of setting quests to a player.
	 *
	 * @throws IllegalQuestChangeException
	 *             the state changed illegally
	 */
	@Test
	public void testAddQuests() throws IllegalQuestChangeException
	{
		Player p = playerManager.addPlayer(1);
		QuestState quest = new QuestState(1, 1, QuestStateEnum.AVAILABLE, false);
		QuestManager.getSingleton().addQuestState(p.getPlayerID(), quest);

		QuestState questStateByID = QuestManager.getSingleton().getQuestStateByID(1, 1);
		assertEquals(QuestStateEnum.AVAILABLE, questStateByID.getStateValue());
		assertFalse(questStateByID.isNeedingNotification());

	}

	/**
	 * Completes an adventure that has the completion criteria of talking to a NPC.
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalAdventureChangeException
	 *             Adventure state changed when it shouldn't have
	 * @throws IllegalQuestChangeException
	 *             Quest state changed when it shouldn't have
	 */
	@Test
	public void testCompleteAdventureByChatting()
			throws DatabaseException, IllegalAdventureChangeException, IllegalQuestChangeException
	{
		int playerToTest = PlayersForTest.MARTY.getPlayerID();
		Player playerOne = playerManager.addPlayer(playerToTest);

		Player toTalkTo = playerManager.addPlayer(PlayersForTest.QUIZBOT.getPlayerID());
		Position playerOnePosition = new Position(toTalkTo.getPlayerPosition().getRow() + 1,
				toTalkTo.getPlayerPosition().getColumn() + 1);
		playerOne.setPlayerPosition(playerOnePosition);

		ChatMessageReceivedReport csmr = new ChatMessageReceivedReport(playerOne.getPlayerID(), 0, "Hello",
				playerOne.getPlayerPosition(), ChatType.Local);

		QualifiedObservableConnector.getSingleton().sendReport(csmr);

		assertEquals(AdventureStateEnum.COMPLETED,
				QuestManager.getSingleton().getQuestStateByID(playerToTest, 5).getAdventureList().get(0).getState());
	}

	/**
	 * Adventure isn't completed because the person is outside the range of who they
	 * are supposed to talk to
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalAdventureChangeException
	 *             Adventure state changed when it shouldn't have
	 * @throws IllegalQuestChangeException
	 *             Quest state changed when it shouldn't have
	 */
	@Test
	public void testNotCompleteAdventureByChattingOutsideOfRange()
			throws DatabaseException, IllegalAdventureChangeException, IllegalQuestChangeException
	{
		int playerToTest = PlayersForTest.MARTY.getPlayerID();
		Player playerOne = playerManager.addPlayer(playerToTest);

		Player toTalkTo = playerManager.addPlayer(PlayersForTest.QUIZBOT.getPlayerID());
		Position playerOnePosition = new Position(toTalkTo.getPlayerPosition().getRow() + 6,
				toTalkTo.getPlayerPosition().getColumn() + 6);
		playerOne.setPlayerPosition(playerOnePosition);

		ChatMessageReceivedReport csmr = new ChatMessageReceivedReport(playerOne.getPlayerID(), 0, "Hello",
				playerOne.getPlayerPosition(), ChatType.Local);

		QualifiedObservableConnector.getSingleton().sendReport(csmr);

		assertEquals(AdventureStateEnum.TRIGGERED,
				QuestManager.getSingleton().getQuestStateByID(playerToTest, 5).getAdventureList().get(0).getState());
	}

	/**
	 * Make sure quest is triggered if it walks onto a location that has a quest
	 *
	 * @throws DatabaseException
	 *             QuestManager works with DB
	 * @throws IllegalQuestChangeException
	 *             the state changed illegally
	 */
	@Test
	public void testPlayerTriggerOnMovement() throws DatabaseException, IllegalQuestChangeException
	{
		Position pos1 = new Position(1, 1);
		Position pos2 = QuestsForTest.THE_LITTLE_QUEST.getPosition();
		Player p = playerManager.addPlayer(4);
		p.setMapName(QuestsForTest.THE_LITTLE_QUEST.getMapName());
		p.setPlayerPosition(pos1);
		QuestManager one = QuestManager.getSingleton();
		assertEquals(QuestStatesForTest.PLAYER4_QUEST4.getState(), QuestManager.getSingleton()
				.getQuestStateByID(p.getPlayerID(), QuestsForTest.THE_LITTLE_QUEST.getQuestID()).getStateValue());
		QuestManager two = QuestManager.getSingleton();
		assertSame(one, two);
		p.setPlayerPosition(pos2);

		assertEquals(QuestStateEnum.TRIGGERED, QuestManager.getSingleton()
				.getQuestStateByID(p.getPlayerID(), QuestsForTest.THE_LITTLE_QUEST.getQuestID()).getStateValue());
	}

	/**
	 * Make sure quest is triggered within player when quest is available
	 *
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 * @throws DatabaseException
	 *             the state changed illegally
	 */
	@Test
	public void testPlayerTriggersQuest()
			throws IllegalAdventureChangeException, IllegalQuestChangeException, DatabaseException
	{
		Player p = playerManager.addPlayer(7);
		assertEquals(QuestStateEnum.AVAILABLE, QuestManager.getSingleton()
				.getQuestStateByID(p.getPlayerID(), QuestStatesForTest.PLAYER7_QUEST2.getQuestID()).getStateValue());

		QuestManager.getSingleton().triggerQuest(7, QuestStatesForTest.PLAYER7_QUEST2.getQuestID());
		assertEquals(QuestStateEnum.TRIGGERED, QuestManager.getSingleton()
				.getQuestStateByID(p.getPlayerID(), QuestStatesForTest.PLAYER7_QUEST2.getQuestID()).getStateValue());
	}

	/**
	 * When a player moves to the right place, we should trigger the quest
	 *
	 * @throws IllegalQuestChangeException
	 *             the state changed illegally
	 */
	@Test
	public void triggersOnPlayerMovement() throws IllegalQuestChangeException
	{
		Player p = playerManager.addPlayer(1);
		p.setPlayerPosition(QuestsForTest.ONE_BIG_QUEST.getPosition());
		assertEquals(QuestStateEnum.TRIGGERED, QuestManager.getSingleton()
				.getQuestStateByID(p.getPlayerID(), QuestStatesForTest.PLAYER1_QUEST1.getQuestID()).getStateValue());
	}

	/**
	 * When a player moves to the right place, we should get a list of adventures
	 * completed at that location
	 *
	 * @throws IllegalQuestChangeException
	 *             the state changed illegally
	 * @throws DatabaseException
	 *             the state changed illegally
	 */
	@Test
	public void getCompletedAdventuresOnPlayerMovement() throws IllegalQuestChangeException, DatabaseException
	{
		GameLocationDTO location = (GameLocationDTO) (AdventuresForTest.QUEST2_ADVENTURE2.getCompletionCriteria());
		String mapName = location.getMapName();
		Position pos = location.getPosition();

		ArrayList<AdventureRecord> list = QuestManager.getSingleton().getAdventuresByPosition(pos, mapName);
		assertEquals(AdventuresForTest.QUEST2_ADVENTURE2.getAdventureDescription(),
				list.get(0).getAdventureDescription());
	}

	/**
	 * When we add a quest state to a player, we should tell it which player it
	 * belongs to
	 */
	@Test
	public void setPlayerIDWhenQuestStateAdded()
	{
		QuestState questState = new QuestState(4, 1, QuestStateEnum.AVAILABLE, false);
		QuestManager.getSingleton().addQuestState(4, questState);
		assertEquals(4, questState.getPlayerID());
	}

	QuestManager qm = QuestManager.getSingleton();

	/**
	 * We should be able to clear out all of the quest states for a given player
	 */
	@Test
	public void canRemoveAPlayersQuestStates()
	{
		QuestManager.getSingleton().addQuestState(4, new QuestState(4, 1, QuestStateEnum.AVAILABLE, false));
		QuestManager.getSingleton().removeQuestStatesForPlayer(4);
		assertNull(QuestManager.getSingleton().getQuestList(4));
	}

	/**
	 * We should be able to clear out all of the quest states for a given player
	 */
	@Test
	public void removeAPlayersQuestStatesWhenLeaves()
	{
		QuestManager.getSingleton().addQuestState(4, new QuestState(4, 1, QuestStateEnum.AVAILABLE, false));
		QualifiedObservableConnector.getSingleton().sendReport(new PlayerLeaveReport(4));
		assertNull(QuestManager.getSingleton().getQuestList(4));
	}

	/**
	 * Should be able to change the state of a quest to fulfilled if enough
	 * adventures are completed. Player 4 quest 3 in the mock data has enough
	 * adventures to be fulfilled, but still looks pending. We just want to test the
	 * behavior of checking for fulfillment (without an associated adventure state
	 * change)
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 */
	@Test
	public void testFulfillQuest() throws DatabaseException, IllegalQuestChangeException
	{
		int playerID = PlayersForTest.JOSH.getPlayerID();
		int questID = 3;
		Player p = playerManager.addPlayer(playerID);
		int initialExp = p.getExperiencePoints();
		assertEquals(initialExp, PlayersForTest.JOSH.getExperiencePoints());
		int expGain = QuestManager.getSingleton().getQuest(questID).getExperiencePointsGained();

		QuestState x = QuestManager.getSingleton().getQuestStateByID(playerID, questID);
		x.checkForFulfillmentOrFinished();
		assertEquals(QuestStateEnum.FULFILLED, x.getStateValue());
		assertEquals(initialExp + expGain, p.getExperiencePoints());
	}

	/**
	 * This test makes sure that, at the moment an adventure completes, the quest
	 * state changes to fulfilled and the experience points are updated when that is
	 * appropriate
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 *
	 */
	@Test
	public void testCompletingAdventure()
			throws DatabaseException, IllegalAdventureChangeException, IllegalQuestChangeException
	{
		int playerID = PlayersForTest.JOSH.getPlayerID();
		int questID = 4;
		Player p = playerManager.addPlayer(playerID);
		int initialExp = p.getExperiencePoints();

		AdventureState as = new AdventureState(2, AdventureStateEnum.TRIGGERED, false);
		ArrayList<AdventureState> adventures = new ArrayList<>();
		adventures.add(as);

		QuestState qs = new QuestState(playerID, questID, QuestStateEnum.FULFILLED, false);
		qs.addAdventures(adventures);
		QuestManager.getSingleton().addQuestState(playerID, qs);
		as.complete();

		int adventureExperienceGained = QuestManager.getSingleton().getQuest(questID).getAdventureXP(1);

		assertEquals(QuestStateEnum.COMPLETED, qs.getStateValue());
		assertEquals(initialExp + adventureExperienceGained, p.getExperiencePoints());
	}

	/**
	 * Tests completing an adventure by user input
	 */
	@Test
	public void testCompletingAdventureByInput()
	{
		int playerID = PlayersForTest.NEWBIE.getPlayerID();
		int questID = AdventuresForTest.ONRAMPING_PRESS_Q.getQuestID();
		int adventureID = AdventuresForTest.ONRAMPING_PRESS_Q.getAdventureID();

		Player p = playerManager.addPlayer(playerID);
		QuestState qs = QuestManager.getSingleton().getQuestStateByID(p.getPlayerID(), questID);
		qs.setPlayerID(playerID);

		assertEquals(AdventureStateEnum.TRIGGERED,
				QuestManager.getSingleton().getAdventureStateByID(playerID, questID, adventureID).getState());
		CommandKeyInputMessageReceived command = new CommandKeyInputMessageReceived("q", playerID);
		command.execute();

		assertEquals(AdventureStateEnum.COMPLETED,
				QuestManager.getSingleton().getAdventureStateByID(playerID, questID, adventureID).getState());
	}

	/**
	 * Tests that we finishing a quest changes its state to finished
	 *
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 * @throws DatabaseException
	 *             the state changed illegally
	 */
	@Test
	public void testFinishQuest() throws IllegalQuestChangeException, DatabaseException
	{
		int playerID = 2;
		int questID = 4;
		Player p = playerManager.addPlayer(playerID);
		QuestState qs = QuestManager.getSingleton().getQuestStateByID(playerID, questID);
		qs.setPlayerID(playerID);

		GameLocationDTO gl = (GameLocationDTO) QuestsForTest.THE_LITTLE_QUEST.getCompletionActionParameter();
		MapToServerMapping mapping = new MapToServerMapping(gl.getMapName());

		TeleportOnQuestCompletionReport report = new TeleportOnQuestCompletionReport(playerID, questID, gl,
				mapping.getHostName(), mapping.getPortNumber());
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);

		QualifiedObservableConnector.getSingleton().registerObserver(obs, TeleportOnQuestCompletionReport.class);

		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		QuestManager.getSingleton().completeQuest(p.getPlayerID(), qs.getID());

		assertEquals(QuestStateEnum.COMPLETED, qs.getStateValue());

		EasyMock.verify(obs);
	}

	/**
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 *
	 */
	@Test
	public void testCompleteAdventure()
			throws DatabaseException, IllegalAdventureChangeException, IllegalQuestChangeException
	{
		int playerID = 1;
		int questID = 3;
		int adventureID = 1;

		Player p = playerManager.addPlayer(playerID);
		QuestState qs = QuestManager.getSingleton().getQuestStateByID(p.getPlayerID(), questID);
		AdventureState as = QuestManager.getSingleton().getAdventureStateByID(p.getPlayerID(), questID, adventureID);
		ArrayList<AdventureState> adventureList = new ArrayList<>();
		adventureList.add(as);
		qs.addAdventures(adventureList);
		qs.setPlayerID(playerID);
		QuestManager.getSingleton().completeAdventure(playerID, questID, adventureID);
		assertEquals(AdventureStateEnum.COMPLETED,
				QuestManager.getSingleton().getAdventureStateByID(playerID, questID, adventureID).getState());
	}

	/**
	 * When a player moves to the right place, should complete adventure
	 *
	 * @throws IllegalQuestChangeException
	 *             the state changed illegally
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void completeAdventureOnPlayerMovement() throws IllegalQuestChangeException, DatabaseException
	{
		// We need to add a quest and adventure that are triggered on location
		// but not already completed
		int questID = AdventuresForTest.QUEST2_ADVENTURE2.getQuestID();
		int advID = AdventuresForTest.QUEST2_ADVENTURE2.getAdventureID();

		GameLocationDTO location = (GameLocationDTO) (AdventuresForTest.QUEST2_ADVENTURE2.getCompletionCriteria());
		Position pos = location.getPosition();
		Player paul = playerManager.addPlayer(8);
		paul.setPlayerPosition(pos);
		assertEquals(AdventureStateEnum.COMPLETED,
				QuestManager.getSingleton().getAdventureStateByID(paul.getPlayerID(), questID, advID).getState());
	}

	/**
	 * When a player moves to the right place, should complete adventure
	 *
	 * @throws IllegalQuestChangeException
	 *             the state changed illegally
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void alreadyCompletedAdventureOnPlayerMovement() throws IllegalQuestChangeException, DatabaseException
	{
		// We need to add a quest and adventure that are triggered on location
		// but not already completed
		int questID = AdventuresForTest.QUEST2_ADVENTURE2.getQuestID();
		int advID = AdventuresForTest.QUEST2_ADVENTURE2.getAdventureID();

		GameLocationDTO location = (GameLocationDTO) (AdventuresForTest.QUEST2_ADVENTURE2.getCompletionCriteria());
		Position pos = location.getPosition();
		Player paul = playerManager.addPlayer(8);
		paul.setPlayerPosition(pos);
		paul.setPlayerPosition(new Position(0, 0));
		paul.setPlayerPosition(pos);

		assertEquals(AdventureStateEnum.COMPLETED,
				QuestManager.getSingleton().getAdventureStateByID(paul.getPlayerID(), questID, advID).getState());

	}


	/**
	 * This tests simulates taking a quest from triggered to fulfilled to finished
	 */
	@Test
	public void simulateCompletingAQuest()
	{
		Player hersh = playerManager.addPlayer(PlayersForTest.HERSH.getPlayerID());

		// completing the first adventure will fulfill the quest
		GameLocationDTO completionCriteria = (GameLocationDTO) AdventuresForTest.QUEST6_ADVENTURE_1
				.getCompletionCriteria();
		hersh.setPlayerPosition(completionCriteria.getPosition());
		AdventureState adventureState = QuestManager.getSingleton().getAdventureStateByID(hersh.getPlayerID(),
				AdventuresForTest.QUEST6_ADVENTURE_1.getQuestID(),
				AdventuresForTest.QUEST6_ADVENTURE_1.getAdventureID());
		assertEquals(AdventureStateEnum.COMPLETED, adventureState.getState());
		QuestState questState = QuestManager.getSingleton().getQuestStateByID(hersh.getPlayerID(),
				AdventuresForTest.QUEST6_ADVENTURE_1.getQuestID());
		assertEquals(QuestStateEnum.FULFILLED, questState.getStateValue());
		assertEquals(
				QuestsForTest.TELEPORT_QUEST.getExperienceGained()
						+ AdventuresForTest.QUEST6_ADVENTURE_1.getExperiencePointsGained(),
				hersh.getExperiencePoints());

		// completing the second adventure will finish the quest
		completionCriteria = (GameLocationDTO) AdventuresForTest.QUEST6_ADVENTURE_2.getCompletionCriteria();
		hersh.setPlayerPosition(completionCriteria.getPosition());

		adventureState = QuestManager.getSingleton().getAdventureStateByID(hersh.getPlayerID(),
				AdventuresForTest.QUEST6_ADVENTURE_2.getQuestID(),
				AdventuresForTest.QUEST6_ADVENTURE_2.getAdventureID());
		assertEquals(AdventureStateEnum.COMPLETED, adventureState.getState());

		questState = QuestManager.getSingleton().getQuestStateByID(hersh.getPlayerID(),
				AdventuresForTest.QUEST6_ADVENTURE_2.getQuestID());
		assertEquals(QuestStateEnum.COMPLETED, questState.getStateValue());
		assertEquals(
				QuestsForTest.TELEPORT_QUEST.getExperienceGained()
						+ AdventuresForTest.QUEST6_ADVENTURE_1.getExperiencePointsGained()
						+ AdventuresForTest.QUEST6_ADVENTURE_2.getExperiencePointsGained(),
				hersh.getExperiencePoints());
	}

	/**
	 * A finished quest should be marked as finished not expired
	 *
	 * @throws IllegalQuestChangeException
	 *             thrown if changing to a wrong state
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testGetQuestStateByIDWhenQuestStateIsExpired() throws IllegalQuestChangeException, DatabaseException
	{
		playerManager.addPlayer(19);
		QuestState questState = QuestManager.getSingleton().getQuestStateByID(19, 8);
		assertEquals(8, questState.getID());
		assertEquals(QuestStateEnum.EXPIRED, questState.getStateValue());
	}

	/**
	 *
	 */
	@Test
	public void testGetQuestStateByIDWhenQuestStateIsAvilable()
	{
		playerManager.addPlayer(19);
		QuestState questState = QuestManager.getSingleton().getQuestStateByID(19, 7);
		assertEquals(7, questState.getID());
		assertEquals(QuestStateEnum.EXPIRED, questState.getStateValue());
	}

	/**
	 *
	 */
	@Test
	public void testGetQuestStateByIDWhenQuestStateIsCompleted()
	{
		playerManager.addPlayer(19);
		QuestState questState = QuestManager.getSingleton().getQuestStateByID(19, 9);
		assertEquals(9, questState.getID());
		assertEquals(QuestStateEnum.COMPLETED, questState.getStateValue());
	}

	/**
	 *
	 * Test that the Quest is completed when the player has enough knowledge points.
	 *
	 * @throws IllegalQuestChangeException
	 *             - shouldn't
	 * @throws IllegalAdventureChangeException
	 *             - shouldn't
	 * @throws DatabaseException
	 *             - shouldn't
	 *
	 */
	@Test
	public void testHandleKnowledgePointsIsChanged()
			throws DatabaseException, IllegalAdventureChangeException, IllegalQuestChangeException
	{

		int playerID = 19;
		int questID = 10;
		int adventureID = 1;

		Player p = playerManager.addPlayer(playerID);

		QuestState qs = new QuestState(playerID, questID, QuestStateEnum.TRIGGERED, true);
		QuestManager.getSingleton().addQuestState(playerID, qs);

		AdventureState as = new AdventureState(adventureID, AdventureStateEnum.TRIGGERED, true);

		ArrayList<AdventureState> adventureList = new ArrayList<>();
		adventureList.add(as);

		qs.addAdventures(adventureList);

		AdventureState adventureStateByID = QuestManager.getSingleton().getAdventureStateByID(playerID, questID,
				adventureID);

		assertEquals(AdventureStateEnum.TRIGGERED, adventureStateByID.getState());

		p.setQuizScore(7);

		KnowledgePointsChangeReport report = new KnowledgePointsChangeReport(playerID, 80, p.getBuffPool());

		QuestManager.getSingleton().handleKnowledgePointsChanged(report);

		assertEquals(AdventureStateEnum.COMPLETED,
				QuestManager.getSingleton().getAdventureStateByID(playerID, questID, adventureID).getState());

	}


	/**
	 * Tests Adventure completion when a player types the proper command in the terminal
	 *
	 */
	@Test
	public void testHandleReceiveTerminalTextReport()
	{
		int playerID = 19;
		int questID = AdventuresForTest.ONRAMPING_TERMINAL_MAN.getQuestID();
		int adventureID = AdventuresForTest.ONRAMPING_TERMINAL_MAN.getAdventureID();

		playerManager.addPlayer(playerID);

		QuestState qs = new QuestState(playerID, questID, QuestStateEnum.TRIGGERED, true);
		QuestManager.getSingleton().addQuestState(playerID, qs);

		AdventureState as = new AdventureState(adventureID, AdventureStateEnum.TRIGGERED, true);

		ArrayList<AdventureState> adventureList = new ArrayList<>();
		adventureList.add(as);

		qs.addAdventures(adventureList);

		AdventureState adventureStateByID = QuestManager.getSingleton().getAdventureStateByID(playerID, questID, adventureID);

		assertEquals(AdventureStateEnum.TRIGGERED, adventureStateByID.getState());

		String terminalText = "man";
		ReceiveTerminalTextReport report = new ReceiveTerminalTextReport(playerID, "", terminalText);
		QualifiedObservableConnector.getSingleton().sendReport(report);

		assertEquals(AdventureStateEnum.COMPLETED, QuestManager.getSingleton().getAdventureStateByID(playerID, questID, adventureID).getState());
	}

	/**
	 * Tests that an interactable object can complete an adventure
	 */
	@Test
	public void testHandleInteractableObjectUsed()
	{
		// Set up
		int playerID = PlayersForTest.NICK.getPlayerID();
		int questID = AdventuresForTest.QUEST12_ADVENTURE_1.getQuestID();
		int adventureID = AdventuresForTest.QUEST12_ADVENTURE_1.getAdventureID();
		Player nick = playerManager.addPlayer(playerID);

		// Sets the quest state
		QuestState qs = new QuestState(playerID, questID, QuestStateEnum.TRIGGERED, true);
		QuestManager.getSingleton().addQuestState(playerID, qs);

		// Sets up and adds the interact adventure
		AdventureState as = new AdventureState(adventureID, AdventureStateEnum.TRIGGERED, true);
		ArrayList<AdventureState> adventureList = new ArrayList<>();
		adventureList.add(as);
		qs.addAdventures(adventureList);
		AdventureState adventureStateByID = QuestManager.getSingleton().getAdventureStateByID(playerID, questID,
				adventureID);

		// Verified that the adventure state is still set to triggered
		assertEquals(AdventureStateEnum.TRIGGERED, adventureStateByID.getState());

		// Set up the interact object manager
		InteractObjectManager.getSingleton();

		// Places Nick next to the object and interacts with it
		nick.setPlayerPosition(InteractableItemsForTest.MSG_TEST.getPosition());
		KeyInputRecievedReport report = new KeyInputRecievedReport("e", playerID);
		QualifiedObservableConnector.getSingleton().sendReport(report);

		// Verifies that the adventure is now completed
		assertEquals(AdventureStateEnum.COMPLETED,
				QuestManager.getSingleton().getAdventureStateByID(playerID, questID, adventureID).getState());

	}

	/**
	 *
	 * @throws DatabaseException
	 */
	@Test
	public void testHandleFriends() throws DatabaseException
	{
		int playerID = PlayersForTest.MERLIN.getPlayerID();
		int playerIDD = PlayersForTest.JOHN.getPlayerID();
		int questID = AdventuresForTest.QUEST_14_ADVENTURE_1.getQuestID();
		int adventureID = AdventuresForTest.QUEST_14_ADVENTURE_1.getAdventureID();
		Player merlin = playerManager.addPlayer(playerID);
		Player john = playerManager.addPlayer(playerIDD);

		// Sets the quest state
		QuestState qs = new QuestState(playerID, questID, QuestStateEnum.TRIGGERED, true);
		QuestManager.getSingleton().addQuestState(playerID, qs);

		// Sets up and adds the friend adventure
		AdventureState as = new AdventureState(adventureID, AdventureStateEnum.TRIGGERED, true);
		ArrayList<AdventureState> adventureList = new ArrayList<>();
		adventureList.add(as);
		qs.addAdventures(adventureList);
		AdventureState adventureStateByID = QuestManager.getSingleton().getAdventureStateByID(playerID, questID,
				adventureID);

		// Verified that the adventure state is still set to triggered
		assertEquals(AdventureStateEnum.TRIGGERED, adventureStateByID.getState());

		// Sets the quest state
		QuestState qs2 = new QuestState(playerIDD, questID, QuestStateEnum.TRIGGERED, true);
		QuestManager.getSingleton().addQuestState(playerIDD, qs);

		// Sets up and adds the friend adventure
		AdventureState as2 = new AdventureState(adventureID, AdventureStateEnum.TRIGGERED, true);
		ArrayList<AdventureState> adventureList2 = new ArrayList<>();
		adventureList2.add(as2);
		qs2.addAdventures(adventureList2);
		AdventureState adventureStateByID2 = QuestManager.getSingleton().getAdventureStateByID(playerIDD, questID,
				adventureID);
		// Verified that the adventure state is still set to triggered
		assertEquals(AdventureStateEnum.TRIGGERED, adventureStateByID2.getState());

		FriendTableDataGateway gateway = FriendTableDataGatewayMock.getSingleton();
		//merlin and john became friends
		gateway.add(playerID, "John", FriendStatusEnum.ACCEPTED);
		//gateway.accept(playerID, "John");
		FriendConnectionReceivedReport report = new FriendConnectionReceivedReport(playerID, playerIDD);

		QualifiedObservableConnector.getSingleton().sendReport(report);
		QuestManager.getSingleton().handleFriends(report);

		// Verifies that the adventure is now completed
		//Player 1 : Sender 
		assertEquals(AdventureStateEnum.COMPLETED,
				QuestManager.getSingleton().getAdventureStateByID(playerID, questID, adventureID).getState());
//		Player 2 Receiver 
		assertEquals(AdventureStateEnum.COMPLETED,
				QuestManager.getSingleton().getAdventureStateByID(playerIDD, questID, adventureID).getState());
	}

	public void testHandleMultipleFriends() throws DatabaseException
	{
		int playerID = PlayersForTest.MERLIN.getPlayerID();
		int playerIDD = PlayersForTest.JOHN.getPlayerID();
		int playerIDT = PlayersForTest.JOSH.getPlayerID();
		int questID = AdventuresForTest.QUEST_15_ADVENTURE_1.getQuestID();
		int adventureID = AdventuresForTest.QUEST_15_ADVENTURE_1.getAdventureID();
		Player merlin = playerManager.addPlayer(playerID);
		Player john = playerManager.addPlayer(playerIDD);
		Player josh = playerManager.addPlayer(playerIDT);
		// Sets the quest state
		QuestState qs = new QuestState(playerID, questID, QuestStateEnum.TRIGGERED, true);
		QuestManager.getSingleton().addQuestState(playerID, qs);

		// Sets up and adds the friend adventure
		AdventureState as = new AdventureState(adventureID, AdventureStateEnum.TRIGGERED, true);
		ArrayList<AdventureState> adventureList = new ArrayList<>();
		adventureList.add(as);
		qs.addAdventures(adventureList);
		AdventureState adventureStateByID = QuestManager.getSingleton().getAdventureStateByID(playerID, questID,
				adventureID);

		// Verified that the adventure state is still set to triggered
		assertEquals(AdventureStateEnum.TRIGGERED, adventureStateByID.getState());

		FriendTableDataGateway gateway = FriendTableDataGatewayMock.getSingleton();
		//merlin and john became friends
		gateway.add(playerID, "John", FriendStatusEnum.ACCEPTED);


		//gateway.accept(playerID, "John");
		FriendConnectionReceivedReport report = new FriendConnectionReceivedReport(playerID, playerIDD);

		QualifiedObservableConnector.getSingleton().sendReport(report);
		QuestManager.getSingleton().handleFriends(report);
		gateway.add(playerID, "Josh", FriendStatusEnum.ACCEPTED);

		gateway.getFriendCounter(playerID);
		FriendConnectionReceivedReport report2 = new FriendConnectionReceivedReport(playerID, playerIDD);

		QualifiedObservableConnector.getSingleton().sendReport(report2);
		QuestManager.getSingleton().handleFriends(report2);
		assertTrue(gateway.getFriendCounter(playerID) == 2);
		// Verifies that the adventure is now completed

		assertEquals(AdventureStateEnum.COMPLETED,
				QuestManager.getSingleton().getAdventureStateByID(playerID, questID, adventureID).getState());
//		
	}
}

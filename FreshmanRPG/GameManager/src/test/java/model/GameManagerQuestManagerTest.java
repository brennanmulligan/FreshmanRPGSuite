package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import criteria.AdventureCompletionCriteria;
import criteria.GameLocationDTO;
import criteria.QuestCompletionActionParameter;
import dataDTO.AdventureStateRecordDTO;
import dataENUM.AdventureCompletionType;
import dataENUM.QuestCompletionActionType;
import datasource.AdventureStateTableDataGateway;
import datasource.AdventureStateTableDataGatewayMock;
import datasource.DatabaseException;
import datasource.DatabaseTest;
import datatypes.Position;
import model.reports.AllQuestsAndAdventuresReport;
import datatypes.AdventuresForTest;
import datatypes.PlayersForTest;
import datatypes.QuestsForTest;

/**
 * Test the behavior of GameManagerQuestManager.
 */
public class GameManagerQuestManagerTest extends DatabaseTest
{

	/**
	 * Set testing mode.
	 *
	 * @throws DatabaseException
	 *             - Shouldn't
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		super.setUp();
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton().setUsingMocKDataSource(false);
		OptionsManager.getSingleton().setUsingTestDB(true);
		GameManagerQuestManager.resetSingleton();

	}


	/**
	 * Checks to see if two singletons are the same instance.
	 */
	@Test
	public void testIsSingleton()
	{
		GameManagerQuestManager a = GameManagerQuestManager.getInstance();
		GameManagerQuestManager b = GameManagerQuestManager.getInstance();
		assertNotNull(a);
		assertNotNull(b);
		assertSame(a, b);
	}

	/**
	 * @throws DatabaseException
	 *             if it fails to access the db
	 */
	@Test
	public void testAddQuest() throws DatabaseException
	{
		GameManagerQuestManager manager = GameManagerQuestManager.getInstance();
		QuestRecord quest = createQuest();

		manager.addQuest(quest.getTitle(), quest.getDescription(), quest.getMapName(), quest.getPos(),
				quest.getExperiencePointsGained(), quest.getAdventuresForFulfillment(), quest.getCompletionActionType(),
				quest.getCompletionActionParameter(), quest.getStartDate(), quest.getEndDate());

		assertContainsQuest(manager.getQuests(), quest);
	}

	/**
	 * @throws DatabaseException
	 *             if it fails to go add quest to the db
	 */
	@Test
	public void testAddQuestGoesToDatabase() throws DatabaseException
	{
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton().setUsingMocKDataSource(false);
		GameManagerQuestManager manager = GameManagerQuestManager.getInstance();
		QuestRecord quest = createQuest();

		manager.addQuest(quest.getTitle(), quest.getDescription(), quest.getMapName(), quest.getPos(),
				quest.getExperiencePointsGained(), quest.getAdventuresForFulfillment(), quest.getCompletionActionType(),
				quest.getCompletionActionParameter(), quest.getStartDate(), quest.getEndDate());
		assertContainsQuest(manager.getQuests(), quest);
	}

	/**
	 * Test that updating adventure stores the updates
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testUpdateAdventure() throws DatabaseException
	{
		GameManagerQuestManager manager = GameManagerQuestManager.getInstance();
		AdventuresForTest adventure = AdventuresForTest.QUEST1_ADVENTURE_1;

		manager.editAdventure(adventure.getQuestID(), adventure.getAdventureID(), "New Adventure Description",
				adventure.getExperiencePointsGained(), adventure.getCompletionType(),
				adventure.getCompletionCriteria());

		ArrayList<QuestRecord> quests = manager.getQuests();
		QuestRecord afterQuest = quests.get(0);
		AdventureRecord afterAdventure = afterQuest.getAdventureD(adventure.getAdventureID());
		assertEquals("New Adventure Description", afterAdventure.getAdventureDescription());

	}

	/**
	 * make sure edits are saved into database
	 *
	 * @throws DatabaseException
	 *             if it fails to access the database
	 */
	@Test
	public void testEditQuest() throws DatabaseException
	{
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton().setUsingMocKDataSource(false);

		GameManagerQuestManager manager = GameManagerQuestManager.getInstance();
		QuestsForTest quest = QuestsForTest.ONE_BIG_QUEST;

		final String questTitle = "f";
		final String questDescription = "testDescription";
		final String triggerMapName = "test.tmx";
		final Position triggerPosition = new Position(2, 32);
		final int adventuresForFulfillment = 7;
		final int experienceGained = 7;
		final QuestCompletionActionType completionActionType = QuestCompletionActionType.NO_ACTION;
		final QuestCompletionActionParameter completionActionParameter = null;
		final Date startDate = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
		final Date endDate = new GregorianCalendar(9999, Calendar.MARCH, 23).getTime();

		manager.editQuest(quest.getQuestID(), questTitle, questDescription, triggerMapName, triggerPosition,
				experienceGained, adventuresForFulfillment, completionActionType, completionActionParameter, startDate,
				endDate);

		ArrayList<QuestRecord> quests = manager.getQuests();
		QuestRecord afterQuest = quests.get(0);

		assertEquals(7, afterQuest.getAdventuresForFulfillment());
		assertNull(afterQuest.getCompletionActionParameter());
		assertEquals(afterQuest.getCompletionActionType(), QuestCompletionActionType.NO_ACTION);
		assertEquals(afterQuest.getEndDate(), new GregorianCalendar(9999, Calendar.MARCH, 23).getTime());
		assertEquals(afterQuest.getExperiencePointsGained(), 7);
		assertEquals(afterQuest.getDescription(), "testDescription");
		assertEquals(afterQuest.getTitle(), "f");
		assertEquals(afterQuest.getStartDate(), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime());
		assertEquals(afterQuest.getMapName(), "test.tmx");
		assertEquals(afterQuest.getPos(), new Position(2, 32));
	}

	/**
	 * Should get a specific quest.
	 *
	 * @throws DatabaseException
	 *             - if quests not able to be loaded from data source
	 */
	@Test
	public void testGetQuest() throws DatabaseException
	{
		final QuestsForTest quest = QuestsForTest.CHAT_TO_AN_NPC_QUEST;
		final QuestRecord record = GameManagerQuestManager.getInstance().getQuest(quest.getQuestID());
		assertNotNull(record);

		assertEquals(quest.getQuestTitle(), record.getTitle());
		assertEquals(quest.getQuestDescription(), record.getDescription());
		assertEquals(quest.getQuestID(), record.getQuestID());
		assertEquals(quest.getMapName(), record.getMapName());
		assertEquals(quest.getPosition(), record.getPos());
		assertEquals(quest.getExperienceGained(), record.getExperiencePointsGained());
		assertEquals(quest.getAdventuresForFulfillment(), record.getAdventuresForFulfillment());
		assertEquals(quest.getCompletionActionType(), record.getCompletionActionType());
		assertEquals(quest.getCompletionActionParameter(), record.getCompletionActionParameter());
		assertEquals(quest.getStartDate(), record.getStartDate());
		assertEquals(quest.getEndDate(), record.getEndDate());
	}

	/**
	 * Add an adventure to an existing quest.
	 *
	 * @throws DatabaseException
	 *             - if adventure not found in data source
	 */
	@Test
	public void testAddAdventure() throws DatabaseException
	{
		final String adventureDescription = "New Adventure";
		final int questId = QuestsForTest.EXPLORATION_QUEST.getQuestID();
		final int experiencePointsGained = 87;
		final AdventureCompletionType type = AdventuresForTest.EXPLORING_FIND_QUIZNASIUM.getCompletionType();
		final AdventureCompletionCriteria criteria = AdventuresForTest.EXPLORING_FIND_QUIZNASIUM
				.getCompletionCriteria();

		final int adventureId = GameManagerQuestManager.getInstance().getAdventureTableGateway()
				.getNextAdventureID(questId);
		final AdventureRecord record = new AdventureRecord(questId, adventureId, adventureDescription,
				experiencePointsGained, type, criteria);

		assertTrue(GameManagerQuestManager.getInstance().addAdventure(questId, adventureDescription,
				experiencePointsGained, type, criteria));

		final QuestRecord quest = GameManagerQuestManager.getInstance().getQuest(questId);

		assertTrue(quest.getAdventures().contains(record));
	}

	/**
	 * Should persist the adventure in the data source.
	 *
	 * @throws DatabaseException
	 *             - if unable to add adventure in data source
	 */
	@Test
	public void testAddAdventureGoesToDataSource() throws DatabaseException
	{
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton().setUsingMocKDataSource(false);

		final String adventureDescription = "New Adventure";
		final int questId = QuestsForTest.EXPLORATION_QUEST.getQuestID();
		final int experiencePointsGained = 87;
		final AdventureCompletionType type = AdventuresForTest.EXPLORING_FIND_QUIZNASIUM.getCompletionType();
		final AdventureCompletionCriteria criteria = AdventuresForTest.EXPLORING_FIND_QUIZNASIUM
				.getCompletionCriteria();

		final int adventureId = GameManagerQuestManager.getInstance().getAdventureTableGateway()
				.getNextAdventureID(questId);
		final AdventureRecord record = new AdventureRecord(questId, adventureId, adventureDescription,
				experiencePointsGained, type, criteria);

		assertTrue(GameManagerQuestManager.getInstance().addAdventure(questId, adventureDescription,
				experiencePointsGained, type, criteria));

		final QuestRecord quest = GameManagerQuestManager.getInstance().getQuest(questId);

		assertTrue(quest.getAdventures().contains(record));
	}

	/**
	 * Adding an adventure to a quest that does not exist should fail.
	 *
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	@Test
	public void testAddAdventureInvalidQuest() throws DatabaseException
	{
		final String adventureDescription = "New Adventure";
		final int questId = -877;
		final int experiencePointsGained = 87;
		final AdventureCompletionType type = AdventuresForTest.EXPLORING_FIND_QUIZNASIUM.getCompletionType();
		final AdventureCompletionCriteria criteria = AdventuresForTest.EXPLORING_FIND_QUIZNASIUM
				.getCompletionCriteria();

		assertFalse(GameManagerQuestManager.getInstance().addAdventure(questId, adventureDescription,
				experiencePointsGained, type, criteria));

		assertNull(GameManagerQuestManager.getInstance().getQuest(questId));
	}

	/**
	 * Test that we can delete the from the mock
	 *
	 * @throws DatabaseException,
	 *             shouldn't
	 */
	@Test
	public void testDeleteAdventureWithMock() throws DatabaseException
	{
		GameManagerQuestManager manager = GameManagerQuestManager.getInstance();
		int adventureId = AdventuresForTest.EXPLORING_FIND_QUIZNASIUM.getAdventureID();
		int questId = AdventuresForTest.EXPLORING_FIND_QUIZNASIUM.getQuestID();
		// Should return true because it found the adventure for removal
		assertTrue(manager.deleteAdventure(questId, adventureId));
		// Should return false because it did not find the adventure
		assertFalse(manager.deleteAdventure(questId, adventureId));
	}

	/**
	 * Tests removing a quest with Mock
	 *
	 * @throws SQLException
	 *             - shouldn't
	 */
	@Test
	public void testRemovingQuest() throws SQLException
	{
		GameManagerQuestManager manager = GameManagerQuestManager.getInstance();
		int questID = QuestsForTest.ONRAMPING_QUEST.getQuestID();
		// Returns true after deleting correct quest
		assertTrue(manager.deleteQuest(questID));
		// returns false because quest has already been deleted
		assertFalse(manager.deleteQuest(questID));
	}

	/**
	 * Tests getting incomplete adventures for a player
	 *
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	@Test
	public void testGettingIncompleteAdventures() throws DatabaseException
	{
		GameManagerQuestManager manager = GameManagerQuestManager.getInstance();
		int playerID = PlayersForTest.MARTY.getPlayerID();

		ArrayList<AdventureRecord> recordList = manager.getIncompleteAdventures(playerID);
		AdventureStateTableDataGateway gateway = AdventureStateTableDataGatewayMock.getSingleton();
		ArrayList<AdventureStateRecordDTO> gatewayList = gateway.getUncompletedAdventuresForPlayer(playerID);

		boolean found = false;
		for (int i = 0; i < recordList.size(); i++)
		{
			if (recordList.get(i).getAdventureID() == gatewayList.get(i).getAdventureID())
			{
				found = true;
			}
			else
			{
				found = false;
			}
		}

		assertTrue(found);
	}

	/**
	 * Test that a report is sent when sendReport is called
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testSendQuestReport() throws DatabaseException
	{

		MockQualifiedObserver obs = new MockQualifiedObserver(AllQuestsAndAdventuresReport.class);

		GameManagerQuestManager manager = GameManagerQuestManager.getInstance();
		ArrayList<QuestRecord> list = manager.getQuests();
		manager.sendQuestReport();

		assertEquals(list, ((AllQuestsAndAdventuresReport) obs.getReport()).getQuestInfo());
	}

	private void assertContainsQuest(List<QuestRecord> quests, QuestRecord actual)
	{
		for (QuestRecord expected : quests)
		{
			if (questsAreEqual(expected, actual))
			{
				return;
			}
		}

		fail("None of the quests were equal.");
	}

	private boolean questsAreEqual(QuestRecord expected, QuestRecord actual)
	{
		return expected.getTitle().equals(actual.getTitle())
				&& expected.getDescription().equals(actual.getDescription())
				&& expected.getMapName().equals(actual.getMapName()) && expected.getPos().equals(actual.getPos())
				&& expected.getExperiencePointsGained() == actual.getExperiencePointsGained()
				&& expected.getAdventuresForFulfillment() == actual.getAdventuresForFulfillment()
				&& expected.getCompletionActionType().equals(actual.getCompletionActionType())
				&& expected.getCompletionActionParameter().equals(actual.getCompletionActionParameter())
				&& expected.getStartDate().equals(actual.getStartDate())
				&& expected.getEndDate().equals(actual.getEndDate());
	}

	private QuestRecord createQuest()
	{
		GameLocationDTO location = new GameLocationDTO("sorting.room", new Position(6, 8));

		return new QuestRecord(-1, "Title", "Description", "sorting.room", new Position(0, 0),
				new ArrayList<>(), 10, 10, QuestCompletionActionType.TELEPORT, location,
				new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(),
				new GregorianCalendar(9999, Calendar.MARCH, 28).getTime());
	}

}

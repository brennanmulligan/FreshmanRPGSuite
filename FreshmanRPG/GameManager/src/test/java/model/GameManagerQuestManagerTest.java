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

import criteria.ObjectiveCompletionCriteria;
import criteria.GameLocationDTO;
import criteria.QuestCompletionActionParameter;
import dataDTO.ObjectiveStateRecordDTO;
import dataENUM.ObjectiveCompletionType;
import dataENUM.QuestCompletionActionType;
import datasource.ObjectiveStateTableDataGateway;
import datasource.ObjectiveStateTableDataGatewayMock;
import datasource.DatabaseException;
import datasource.DatabaseTest;
import datatypes.Position;
import model.reports.AllQuestsAndObjectivesReport;
import datatypes.ObjectivesForTest;
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
				quest.getExperiencePointsGained(), quest.getObjectivesForFulfillment(), quest.getCompletionActionType(),
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
				quest.getExperiencePointsGained(), quest.getObjectivesForFulfillment(), quest.getCompletionActionType(),
				quest.getCompletionActionParameter(), quest.getStartDate(), quest.getEndDate());
		assertContainsQuest(manager.getQuests(), quest);
	}

	/**
	 * Test that updating objective stores the updates
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testUpdateObjective() throws DatabaseException
	{
		GameManagerQuestManager manager = GameManagerQuestManager.getInstance();
		ObjectivesForTest objective = ObjectivesForTest.QUEST1_OBJECTIVE_1;

		manager.editObjective(objective.getQuestID(), objective.getObjectiveID(), "New Objective Description",
				objective.getExperiencePointsGained(), objective.getCompletionType(),
				objective.getCompletionCriteria());

		ArrayList<QuestRecord> quests = manager.getQuests();
		QuestRecord afterQuest = quests.get(0);
		ObjectiveRecord afterObjective = afterQuest.getObjectiveID(objective.getObjectiveID());
		assertEquals("New Objective Description", afterObjective.getObjectiveDescription());

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
		final int objectivesForFulfillment = 7;
		final int experienceGained = 7;
		final QuestCompletionActionType completionActionType = QuestCompletionActionType.NO_ACTION;
		final QuestCompletionActionParameter completionActionParameter = null;
		final Date startDate = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
		final Date endDate = new GregorianCalendar(9999, Calendar.MARCH, 23).getTime();

		manager.editQuest(quest.getQuestID(), questTitle, questDescription, triggerMapName, triggerPosition,
				experienceGained, objectivesForFulfillment, completionActionType, completionActionParameter, startDate,
				endDate);

		ArrayList<QuestRecord> quests = manager.getQuests();
		QuestRecord afterQuest = quests.get(0);

		assertEquals(7, afterQuest.getObjectivesForFulfillment());
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
		assertEquals(quest.getObjectiveForFulfillment(), record.getObjectivesForFulfillment());
		assertEquals(quest.getCompletionActionType(), record.getCompletionActionType());
		assertEquals(quest.getCompletionActionParameter(), record.getCompletionActionParameter());
		assertEquals(quest.getStartDate(), record.getStartDate());
		assertEquals(quest.getEndDate(), record.getEndDate());
	}

	/**
	 * Add an objective to an existing quest.
	 *
	 * @throws DatabaseException
	 *             - if objective not found in data source
	 */
	@Test
	public void testAddObjective() throws DatabaseException
	{
		final String objectiveDescription = "New Objective";
		final int questId = QuestsForTest.EXPLORATION_QUEST.getQuestID();
		final int experiencePointsGained = 87;
		final ObjectiveCompletionType type = ObjectivesForTest.EXPLORING_FIND_REC_CENTER.getCompletionType();
		final ObjectiveCompletionCriteria criteria = ObjectivesForTest.EXPLORING_FIND_REC_CENTER
				.getCompletionCriteria();

		final int objectiveId = GameManagerQuestManager.getInstance().getObjectiveTableGateway()
				.getNextObjectiveID(questId);
		final ObjectiveRecord record = new ObjectiveRecord(questId, objectiveId, objectiveDescription,
				experiencePointsGained, type, criteria);

		assertTrue(GameManagerQuestManager.getInstance().addObjective(questId, objectiveDescription,
				experiencePointsGained, type, criteria));

		final QuestRecord quest = GameManagerQuestManager.getInstance().getQuest(questId);

		assertTrue(quest.getObjectives().contains(record));
	}

	/**
	 * Should persist the objective in the data source.
	 *
	 * @throws DatabaseException
	 *             - if unable to add objective in data source
	 */
	@Test
	public void testAddObjectiveGoesToDataSource() throws DatabaseException
	{
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton().setUsingMocKDataSource(false);

		final String objectiveDescription = "New Objective";
		final int questId = QuestsForTest.EXPLORATION_QUEST.getQuestID();
		final int experiencePointsGained = 87;
		final ObjectiveCompletionType type = ObjectivesForTest.EXPLORING_FIND_REC_CENTER.getCompletionType();
		final ObjectiveCompletionCriteria criteria = ObjectivesForTest.EXPLORING_FIND_REC_CENTER
				.getCompletionCriteria();

		final int objectiveId = GameManagerQuestManager.getInstance().getObjectiveTableGateway()
				.getNextObjectiveID(questId);
		final ObjectiveRecord record = new ObjectiveRecord(questId, objectiveId, objectiveDescription,
				experiencePointsGained, type, criteria);

		assertTrue(GameManagerQuestManager.getInstance().addObjective(questId, objectiveDescription,
				experiencePointsGained, type, criteria));

		final QuestRecord quest = GameManagerQuestManager.getInstance().getQuest(questId);

		assertTrue(quest.getObjectives().contains(record));
	}

	/**
	 * Adding an objective to a quest that does not exist should fail.
	 *
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	@Test
	public void testAddObjectiveInvalidQuest() throws DatabaseException
	{
		final String objectiveDescription = "New Objective";
		final int questId = -877;
		final int experiencePointsGained = 87;
		final ObjectiveCompletionType type = ObjectivesForTest.EXPLORING_FIND_REC_CENTER.getCompletionType();
		final ObjectiveCompletionCriteria criteria = ObjectivesForTest.EXPLORING_FIND_REC_CENTER
				.getCompletionCriteria();

		assertFalse(GameManagerQuestManager.getInstance().addObjective(questId, objectiveDescription,
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
	public void testDeleteObjectiveWithMock() throws DatabaseException
	{
		GameManagerQuestManager manager = GameManagerQuestManager.getInstance();
		int objectiveId = ObjectivesForTest.EXPLORING_FIND_REC_CENTER.getObjectiveID();
		int questId = ObjectivesForTest.EXPLORING_FIND_REC_CENTER.getQuestID();
		// Should return true because it found the objective for removal
		assertTrue(manager.deleteObjective(questId, objectiveId));
		// Should return false because it did not find the objective
		assertFalse(manager.deleteObjective(questId, objectiveId));
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
	 * Tests getting incomplete objectives for a player
	 *
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	@Test
	public void testGettingIncompleteObjectives() throws DatabaseException
	{
		GameManagerQuestManager manager = GameManagerQuestManager.getInstance();
		int playerID = PlayersForTest.MARTY.getPlayerID();

		ArrayList<ObjectiveRecord> recordList = manager.getIncompleteObjectives(playerID);
		ObjectiveStateTableDataGateway gateway = ObjectiveStateTableDataGatewayMock.getSingleton();
		ArrayList<ObjectiveStateRecordDTO> gatewayList = gateway.getUncompletedObjectivesForPlayer(playerID);

		boolean found = false;
		for (int i = 0; i < recordList.size(); i++)
		{
			if (recordList.get(i).getObjectiveID() == gatewayList.get(i).getObjectiveID())
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

		MockQualifiedObserver obs = new MockQualifiedObserver(AllQuestsAndObjectivesReport.class);

		GameManagerQuestManager manager = GameManagerQuestManager.getInstance();
		ArrayList<QuestRecord> list = manager.getQuests();
		manager.sendQuestReport();

		assertEquals(list, ((AllQuestsAndObjectivesReport) obs.getReport()).getQuestInfo());
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
				&& expected.getObjectivesForFulfillment() == actual.getObjectivesForFulfillment()
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

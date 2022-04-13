package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datasource.DatabaseTest;
import model.reports.AllQuestsAndAdventuresReport;

/**
 * Tests the Command to delete a quest
 */
public class CommandDeleteQuestTest extends DatabaseTest
{
	private QuestRecord someQuest;
	private GameManagerQuestManager manager;

	/**
	 * Sets up the tests
	 * @throws DatabaseException shouldn't
	 *
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		super.setUp();
		GameManagerQuestManager.resetSingleton();
		manager = GameManagerQuestManager.getInstance();
		someQuest = manager.getQuests().get(0);

	}

	/**
	 * Tests deleting a quest
	 *
	 * @throws DatabaseException - shouldn't
	 */
	@Test
	public void deleteQuestTest() throws DatabaseException
	{
		final GameManagerQuestManager manager = GameManagerQuestManager.getInstance();

		final CommandDeleteQuest cmd = new CommandDeleteQuest(someQuest.getQuestID());
		//should return true after successful deletion
		assertTrue(cmd.execute());
		//should return false after trying to delete already deleted quest
		assertFalse(cmd.execute());

		QuestRecord quest = manager.getQuest(someQuest.getQuestID());
		//asserts that the quest is null after deletion
		assertNull(quest);
	}

	/**
	 * Ensures that a report is sent after the command executes
	 *
	 * @throws DatabaseException - shouldn't 
	 */
	@Test
	public void testReportIsSent() throws DatabaseException
	{
		MockQualifiedObserver obs = new MockQualifiedObserver(AllQuestsAndAdventuresReport.class);
		final CommandDeleteQuest cmd = new CommandDeleteQuest(someQuest.getQuestID());
		assertTrue(cmd.execute());

		AllQuestsAndAdventuresReport report = (AllQuestsAndAdventuresReport) obs.getReport();
		assertNotNull(report);

		//ensures that the quest is not in the new report 
		final QuestRecord quest = report.getQuestInfo().stream().filter(q -> q == someQuest)
				.findAny()
				.orElse(null);
		assertNull(quest);
	}
}
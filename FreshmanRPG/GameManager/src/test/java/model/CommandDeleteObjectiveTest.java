package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import model.reports.AllQuestsAndObjectivesReport;

/**
 * Tests for delete objective command
 */
public class CommandDeleteObjectiveTest
{

	private QuestRecord someQuest;
	private ObjectiveRecord someObjective;
	private GameManagerQuestManager qm;

	/**
	 * First make sure the DatabaseTest.setUp() is called then initialize test
	 * variables
	 *
	 * @exception DatabaseException,
	 *                shouldn't
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		qm = GameManagerQuestManager.getInstance();
		someQuest = qm.getQuests().get(0);
		someObjective = someQuest.getObjectives().get(0);
	}

	/**
	 * Test that the command returns true for success and false for failure
	 *
	 * @throws DatabaseException,
	 *             shouldn't
	 */
	@Test
	public void testDeleteObjectiveCommand() throws DatabaseException
	{
		final CommandDeleteObjective command = new CommandDeleteObjective(someQuest.getQuestID(),
				someObjective.getObjectiveID());
		assertTrue(command.execute());

		// Should return false because the objective doesn't exist
		assertFalse(command.execute());

		final QuestRecord quest = qm.getQuest(someQuest.getQuestID());
		// Should return null since the objective was deleted
		assertNull(quest.getObjectiveID(someObjective.getObjectiveID()));

	}

	/**
	 * Test that report is correct
	 *
	 * @throws DatabaseException,
	 *             shouldn't
	 */
	@Test
	public void testDeleteObjectiveCommandSendsAReport() throws DatabaseException
	{
		// Get a list of quests before command
		MockQualifiedObserver obs = new MockQualifiedObserver(AllQuestsAndObjectivesReport.class);
		final CommandDeleteObjective command = new CommandDeleteObjective(someQuest.getQuestID(),
				someObjective.getObjectiveID());
		assertTrue(command.execute());

		AllQuestsAndObjectivesReport report = (AllQuestsAndObjectivesReport) obs.getReport();
		assertNotNull(report);

		// Lets make sure that report doesn't contain the objective
		final QuestRecord quest = report.getQuestInfo().stream().filter(q -> q == someQuest).findAny().orElse(null);
		assertNull(quest.getObjectiveID(someObjective.getObjectiveID()));
	}
}
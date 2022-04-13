package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import model.reports.AllQuestsAndAdventuresReport;

/**
 * Tests for delete adventure command
 */
public class CommandDeleteAdventureTest
{

	private QuestRecord someQuest;
	private AdventureRecord someAdventure;
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
		someAdventure = someQuest.getAdventures().get(0);
	}

	/**
	 * Test that the command returns true for success and false for failure
	 *
	 * @throws DatabaseException,
	 *             shouldn't
	 */
	@Test
	public void testDeleteAdventureCommand() throws DatabaseException
	{
		final CommandDeleteAdventure command = new CommandDeleteAdventure(someQuest.getQuestID(),
				someAdventure.getAdventureID());
		assertTrue(command.execute());

		// Should return false because the adventure doesn't exist
		assertFalse(command.execute());

		final QuestRecord quest = qm.getQuest(someQuest.getQuestID());
		// Should return null since the adventure was deleted
		assertNull(quest.getAdventureD(someAdventure.getAdventureID()));

	}

	/**
	 * Test that report is correct
	 *
	 * @throws DatabaseException,
	 *             shouldn't
	 */
	@Test
	public void testDeleteAdventureCommandSendsAReport() throws DatabaseException
	{
		// Get a list of quests before command
		MockQualifiedObserver obs = new MockQualifiedObserver(AllQuestsAndAdventuresReport.class);
		final CommandDeleteAdventure command = new CommandDeleteAdventure(someQuest.getQuestID(),
				someAdventure.getAdventureID());
		assertTrue(command.execute());

		AllQuestsAndAdventuresReport report = (AllQuestsAndAdventuresReport) obs.getReport();
		assertNotNull(report);

		// Lets make sure that report doesn't contain the adventure
		final QuestRecord quest = report.getQuestInfo().stream().filter(q -> q == someQuest).findAny().orElse(null);
		assertNull(quest.getAdventureD(someAdventure.getAdventureID()));
	}
}
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datasource.DatabaseTest;
import datatypes.QuestsForTest;

/**
 * Tests the functionality of CommandEditQuest.
 *
 * Left this using the test DB instead of mock gateways because it was using
 * both a row and a table data gateway and making the mocks share data was too
 * complex for the speed up we would get
 */
public class CommandEditQuestTest extends DatabaseTest
{

	/**
	 * Make sure we are in test mode
	 *
	 * @throws DatabaseException
	 *             - Shouldn't
	 */
	@Before
	public void setup() throws DatabaseException
	{
		super.setUp();
		OptionsManager.getSingleton().setUsingMocKDataSource(false);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 * Should tell it's receiver to update a quest.
	 *
	 * @throws DatabaseException
	 *             if quests unable to be loaded from data source
	 */
	@Test
	public void testExecute() throws DatabaseException
	{
		final QuestsForTest quest = QuestsForTest.EXPLORATION_QUEST;
		final String newDescription = "NEW DESCRIPTION";
		final int newXP = quest.getExperienceGained() + 3;
		final CommandEditQuest command = new CommandEditQuest(quest.getQuestID(), quest.getQuestTitle(), newDescription,
				quest.getMapName(), quest.getPosition(), newXP, quest.getObjectiveForFulfillment(),
				quest.getCompletionActionType(), quest.getCompletionActionParameter(), quest.getStartDate(),
				quest.getEndDate());
		assertTrue(command.execute());

		final QuestRecord record = GameManagerQuestManager.getInstance().getQuest(quest.getQuestID());
		assertEquals(newDescription, record.getDescription());
		assertEquals(newXP, record.getExperiencePointsGained());
	}

}

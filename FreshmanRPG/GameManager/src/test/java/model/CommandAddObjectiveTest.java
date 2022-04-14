package model;

import static org.junit.Assert.assertTrue;

import dataENUM.ObjectiveCompletionType;
import org.junit.Before;
import org.junit.Test;

import criteria.ObjectiveCompletionCriteria;
import datasource.DatabaseException;
import datatypes.ObjectivesForTest;
import datatypes.QuestsForTest;

/**
 * Tests the functionality of CommandAddObjective.
 */
public class CommandAddObjectiveTest
{
	/**
	 * Make sure we are in test mode
	 */
	@Before
	public void setup()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	/**
	 * Should tell it's receiver to add an objective.
	 *
	 * @throws DatabaseException
	 *             if objective unable to be added to data source
	 */
	@Test
	public void testExecuteAddObjective() throws DatabaseException
	{
		final int questId = QuestsForTest.CHAT_TO_AN_NPC_QUEST.getQuestID();
		final String objectiveDescription = "If you're reading this, you've been in a coma for almost "
				+ "20 years now. We're trying a new technique. We don't know where this message will end "
				+ "up in your dream, but we hope it works. Please wake up, we miss you.";
		final int experiencePointsGained = 80;
		final ObjectiveCompletionType objectiveCompletionType = ObjectivesForTest.EXPLORING_FIND_REC_CENTER
				.getCompletionType();
		final ObjectiveCompletionCriteria objectiveCompletionCriteria = ObjectivesForTest.EXPLORING_FIND_REC_CENTER
				.getCompletionCriteria();

		final int objectiveId = GameManagerQuestManager.getInstance().getObjectiveTableGateway()
				.getNextObjectiveID(questId);
		final ObjectiveRecord record = new ObjectiveRecord(questId, objectiveId, objectiveDescription,
				experiencePointsGained, objectiveCompletionType, objectiveCompletionCriteria);

		final CommandAddObjective command = new CommandAddObjective(questId, objectiveDescription,
				experiencePointsGained, objectiveCompletionType, objectiveCompletionCriteria);
		assertTrue(command.execute());

		final QuestRecord quest = GameManagerQuestManager.getInstance().getQuest(questId);
		assertTrue(quest.getObjectives().contains(record));
	}

}

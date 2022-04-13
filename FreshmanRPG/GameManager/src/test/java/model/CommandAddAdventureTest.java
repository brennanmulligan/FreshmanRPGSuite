package model;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import criteria.AdventureCompletionCriteria;
import dataENUM.AdventureCompletionType;
import datasource.DatabaseException;
import datatypes.AdventuresForTest;
import datatypes.QuestsForTest;

/**
 * Tests the functionality of CommandAddAdventure.
 */
public class CommandAddAdventureTest
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
	 * Should tell it's receiver to add an adventure.
	 *
	 * @throws DatabaseException
	 *             if adventure unable to be added to data source
	 */
	@Test
	public void testExecuteAddAdventure() throws DatabaseException
	{
		final int questId = QuestsForTest.CHAT_TO_AN_NPC_QUEST.getQuestID();
		final String adventureDescription = "If you're reading this, you've been in a coma for almost "
				+ "20 years now. We're trying a new technique. We don't know where this message will end "
				+ "up in your dream, but we hope it works. Please wake up, we miss you.";
		final int experiencePointsGained = 80;
		final AdventureCompletionType adventureCompletionType = AdventuresForTest.EXPLORING_FIND_QUIZNASIUM
				.getCompletionType();
		final AdventureCompletionCriteria adventureCompletionCriteria = AdventuresForTest.EXPLORING_FIND_QUIZNASIUM
				.getCompletionCriteria();

		final int adventureId = GameManagerQuestManager.getInstance().getAdventureTableGateway()
				.getNextAdventureID(questId);
		final AdventureRecord record = new AdventureRecord(questId, adventureId, adventureDescription,
				experiencePointsGained, adventureCompletionType, adventureCompletionCriteria);

		final CommandAddAdventure command = new CommandAddAdventure(questId, adventureDescription,
				experiencePointsGained, adventureCompletionType, adventureCompletionCriteria);
		assertTrue(command.execute());

		final QuestRecord quest = GameManagerQuestManager.getInstance().getQuest(questId);
		assertTrue(quest.getAdventures().contains(record));
	}

}

package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datasource.DatabaseTest;
import model.reports.AllQuestsAndAdventuresReport;
import datatypes.AdventuresForTest;

/**
 * @author Darin Alleman and Darnell Martin Tests for the Edit Adventure command
 *         Left this a database test instead of making it use the mock data
 *         sources because it uses both table and row data gateways and making
 *         the mock datasources share data was more complication than the
 *         speedup was worth
 *
 */
public class CommandEditAdventureTest extends DatabaseTest
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
		OptionsManager.getSingleton().setUsingTestDB(true);
		OptionsManager.getSingleton().setUsingMocKDataSource(false);
		GameManagerQuestManager.resetSingleton();
	}

	/**
	 * Test that a AllQuestsAndAdeventuresReport is created and sent when the
	 * command is executed as well as that the list contains the edited adventure.
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testEditAdventure() throws DatabaseException
	{
		AdventuresForTest adventure = AdventuresForTest.QUEST1_ADVENTURE_1;
		CommandEditAdventure cmd = new CommandEditAdventure(adventure.getQuestID(), adventure.getAdventureID(),
				adventure.getAdventureDescription(), 100, adventure.getCompletionType(),
				adventure.getCompletionCriteria());
		cmd.execute();

		ArrayList<QuestRecord> quests = GameManagerQuestManager.getInstance().getQuests();
		QuestRecord afterQuest = quests.get(0);
		AdventureRecord afterAdventure = afterQuest.getAdventureD(adventure.getAdventureID());
		assertEquals(100, afterAdventure.getExperiencePointsGained());

	}

	/**
	 * Test that the command sends a report
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testReportIsSent() throws DatabaseException
	{

		MockQualifiedObserver obs = new MockQualifiedObserver(AllQuestsAndAdventuresReport.class);

		AdventuresForTest adventure = AdventuresForTest.QUEST1_ADVENTURE_1;
		CommandEditAdventure cmd = new CommandEditAdventure(adventure.getQuestID(), adventure.getAdventureID(),
				adventure.getAdventureDescription(), 100, adventure.getCompletionType(),
				adventure.getCompletionCriteria());
		cmd.execute();

		GameManagerQuestManager manager = GameManagerQuestManager.getInstance();
		ArrayList<QuestRecord> list = manager.getQuests();
		AllQuestsAndAdventuresReport listReport = new AllQuestsAndAdventuresReport(list);
		AllQuestsAndAdventuresReport reportReceived = (AllQuestsAndAdventuresReport) obs.getReport();
		assertEquals(listReport.getQuestInfo().size(), reportReceived.getQuestInfo().size());
		for (int i = 0; i < listReport.getQuestInfo().size(); i++)
		{
			assertEquals(listReport.getQuestInfo().get(i).getExperiencePointsGained() + " " + reportReceived.getQuestInfo().get(i).getExperiencePointsGained(), listReport.getQuestInfo().get(i), reportReceived.getQuestInfo().get(i));
		}
	}
}

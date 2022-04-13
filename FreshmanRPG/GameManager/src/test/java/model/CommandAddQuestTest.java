package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import model.reports.AllQuestsAndAdventuresReport;
import datatypes.QuestsForTest;

/**
 * Test CommandAddQuest
 *
 * @author Caleb Bushman & Darnell Martin
 *
 */
public class CommandAddQuestTest
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
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	/**
	 * Test that a AllQuestsAndAdeventuresReport is created and sent when the
	 * command is executed as well as that the list contains the new quest.
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testAddQuest() throws DatabaseException
	{
		MockQualifiedObserver obs = new MockQualifiedObserver(AllQuestsAndAdventuresReport.class);

		QuestsForTest quest1 = QuestsForTest.THE_OTHER_QUEST;
		CommandAddQuest cmd = new CommandAddQuest(quest1.getQuestTitle(), quest1.getQuestDescription(),
				quest1.getMapName(), quest1.getPosition(), quest1.getExperienceGained(),
				quest1.getAdventuresForFulfillment(), quest1.getCompletionActionType(),
				quest1.getCompletionActionParameter(), quest1.getStartDate(), quest1.getEndDate());

		cmd.execute();

		GameManagerQuestManager manager = GameManagerQuestManager.getInstance();
		ArrayList<QuestRecord> list = manager.getQuests();
		AllQuestsAndAdventuresReport listReport = new AllQuestsAndAdventuresReport(list);
		assertEquals(listReport.getQuestInfo(), ((AllQuestsAndAdventuresReport) obs.getReport()).getQuestInfo());
	}
}

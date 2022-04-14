package model.reports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import datasource.DatabaseException;
import model.OptionsManager;
import model.QuestRecord;
import datatypes.QuestsForTest;


/**
 * Tests AllQuestsAndObjectivesReport - pretty simple since it is just a data transfer
 * object
 *
 * @author Darnell Martin & Darin Alleman
 *
 */
public class AllQuestsAndObjectivesReportTest
{

	/**
	 * Make sure we are in test mode
	 */
	@BeforeClass
	public static void setup()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 * Even an empty list of Quests and Objectives should not be null
	 */
	@Test
	public void testEmpty()
	{
		AllQuestsAndObjectivesReport r = new AllQuestsAndObjectivesReport(new ArrayList<>());
		ArrayList<QuestRecord> a = r.getQuestInfo();
		assertEquals(0, a.size());
	}

	/**
	 * We should just get back the list we give it
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testNotEmpty() throws DatabaseException
	{
		ArrayList<QuestRecord> list = new ArrayList<>();
		QuestsForTest t = QuestsForTest.THE_LITTLE_QUEST;

		QuestRecord p1 = new QuestRecord(t.getQuestID(), t.getQuestTitle(), t.getQuestDescription(),
				t.getMapName(), t.getPosition(), null, t.getExperienceGained(),
				t.getObjectiveForFulfillment(), t.getCompletionActionType(),
				t.getCompletionActionParameter(), t.getStartDate(), t.getEndDate());
		t = QuestsForTest.ONE_BIG_QUEST;
		QuestRecord p2 = new QuestRecord(t.getQuestID(), t.getQuestTitle(), t.getQuestDescription(),
				t.getMapName(), t.getPosition(), null, t.getExperienceGained(),
				t.getObjectiveForFulfillment(), t.getCompletionActionType(),
				t.getCompletionActionParameter(), t.getStartDate(), t.getEndDate());
		AllQuestsAndObjectivesReport r = new AllQuestsAndObjectivesReport(list);
		list.add(p1);
		list.add(p2);
		ArrayList<QuestRecord> a = r.getQuestInfo();
		assertEquals(2, a.size());
		assertTrue(a.contains(p1));
		assertTrue(a.contains(p2));
	}

}

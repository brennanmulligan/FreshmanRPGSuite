package model.reports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import model.ObjectiveRecord;
import model.OptionsManager;
import datatypes.ObjectivesForTest;

/**
 * @author Scott Bowling
 *
 */
public class PlayerUncompletedObjectivesReportTest
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
	 * Test behavior when the report contains no questions
	 */
	@Test
	public void testEmpty()
	{
		PlayersUncompletedObjectivesReport report = new PlayersUncompletedObjectivesReport(new ArrayList<>());
		ArrayList<ObjectiveRecord> adv = report.getAllUncompletedObjectives();
		assertEquals(0, adv.size());


	}

	/**
	 * Tests that the report is not empty after adding things to it.
	 */
	@Test
	public void TestNotEmpty()
	{
		ArrayList<ObjectiveRecord> objectives = new ArrayList<>();
		ObjectivesForTest t = ObjectivesForTest.QUEST1_OBJECTIVE2;
		ObjectiveRecord record1 = new ObjectiveRecord(t.getQuestID(), t.getObjectiveID()
				, t.getObjectiveDescription(), t.getExperiencePointsGained(),
				t.getCompletionType(), t.getCompletionCriteria());
		t = ObjectivesForTest.QUEST11_OBJECTIVE_1;
		ObjectiveRecord record2 = new ObjectiveRecord(t.getQuestID(), t.getObjectiveID()
				, t.getObjectiveDescription(), t.getExperiencePointsGained(),
				t.getCompletionType(), t.getCompletionCriteria());
		PlayersUncompletedObjectivesReport report = new PlayersUncompletedObjectivesReport(objectives);
		objectives.add(record1);
		objectives.add(record2);
		ArrayList<ObjectiveRecord> list = report.getAllUncompletedObjectives();
		assertEquals(2, list.size());
		assertTrue(list.contains(record1));
		assertTrue(list.contains(record2));
	}

}

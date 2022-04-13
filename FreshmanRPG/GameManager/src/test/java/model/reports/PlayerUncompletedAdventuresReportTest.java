package model.reports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import model.AdventureRecord;
import model.OptionsManager;
import datatypes.AdventuresForTest;

/**
 * @author Scott Bowling
 *
 */
public class PlayerUncompletedAdventuresReportTest
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
		PlayersUncompletedAdventuresReport report = new PlayersUncompletedAdventuresReport(new ArrayList<>());
		ArrayList<AdventureRecord> adv = report.getAllUncompletedAdventures();
		assertEquals(0, adv.size());


	}

	/**
	 * Tests that the report is not empty after adding things to it.
	 */
	@Test
	public void TestNotEmpty()
	{
		ArrayList<AdventureRecord> adventures = new ArrayList<>();
		AdventuresForTest t = AdventuresForTest.QUEST1_ADVENTURE2;
		AdventureRecord record1 = new AdventureRecord(t.getQuestID(), t.getAdventureID()
				, t.getAdventureDescription(), t.getExperiencePointsGained(),
				t.getCompletionType(), t.getCompletionCriteria());
		t = AdventuresForTest.QUEST11_ADVENTURE_1;
		AdventureRecord record2 = new AdventureRecord(t.getQuestID(), t.getAdventureID()
				, t.getAdventureDescription(), t.getExperiencePointsGained(),
				t.getCompletionType(), t.getCompletionCriteria());
		PlayersUncompletedAdventuresReport report = new PlayersUncompletedAdventuresReport(adventures);
		adventures.add(record1);
		adventures.add(record2);
		ArrayList<AdventureRecord> list = report.getAllUncompletedAdventures();
		assertEquals(2, list.size());
		assertTrue(list.contains(record1));
		assertTrue(list.contains(record2));
	}

}

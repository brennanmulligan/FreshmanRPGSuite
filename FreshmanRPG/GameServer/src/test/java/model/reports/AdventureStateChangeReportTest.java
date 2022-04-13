package model.reports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import datatypes.AdventureStateEnum;
import nl.jqno.equalsverifier.EqualsVerifier;
import datatypes.AdventuresForTest;

/**
 * Tests the report of a change state
 *
 * @author nk3668
 *
 */
public class AdventureStateChangeReportTest
{

	/**
	 * Tests the creation of the AdventureStateChangeReport
	 */
	@Test
	public void testInitialization()
	{
		AdventureStateChangeReport report = new AdventureStateChangeReport(1,
				AdventuresForTest.QUEST1_ADVENTURE_1.getQuestID(),
				AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureID(),
				AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureDescription(), AdventureStateEnum.TRIGGERED, true,
				"Mom");

		assertEquals(1, report.getPlayerID());
		assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getQuestID(), report.getAdventureID());
		assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureID(), report.getAdventureID());
		assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureDescription(), report.getAdventureDescription());
		assertEquals(AdventureStateEnum.TRIGGERED, report.getNewState());
		assertTrue(report.isRealLifeAdventure());
		assertEquals("Mom", report.getWitnessTitle());
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(AdventureStateChangeReport.class).verify();
	}
}

package model.reports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import datatypes.AdventureStateEnum;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * @author nk3668 Tests the creation & functionality of the
 *         AdventuresNeedingNotificationReport class
 */
public class AdventuresNeedingNotificationReportTest
{
	/**
	 * Ensures that the report contains the correct data
	 */
	@Test
	public void testInitialization()
	{

		AdventureNeedingNotificationReport report = new AdventureNeedingNotificationReport(
				1, 2, 1, "Silly Adventure", AdventureStateEnum.COMPLETED, true,
				"El Presidente");
		assertEquals(1, report.getPlayerID());
		assertEquals(2, report.getQuestID());
		assertEquals(1, report.getAdventureID());
		assertEquals("Silly Adventure", report.getAdventureDescription());
		assertTrue(report.isRealLifeAdventure());
		assertEquals("El Presidente", report.getWitnessTitle());
	}

	/**
	 * Testing the equality of two instances of this class
	 */
	@Test
	public void testEqualsContract()
	{
		EqualsVerifier.forClass(AdventureNeedingNotificationReport.class).verify();
	}
}

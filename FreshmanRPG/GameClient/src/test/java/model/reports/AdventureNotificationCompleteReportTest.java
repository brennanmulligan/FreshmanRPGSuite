package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Test for AdventureNotifcationCompleteReport
 * @author Ryan
 *
 */
public class AdventureNotificationCompleteReportTest 
{

	/**
	 * Test init and getters for report
	 */
	@Test
	public void testInitialization() 
	{
		AdventureNotificationCompleteReport report = new AdventureNotificationCompleteReport(1, 2, 3);
		
		assertEquals(1, report.getPlayerID());
		assertEquals(2, report.getQuestID());
		assertEquals(3, report.getAdventureID());
	}
	
	/**
	 * Testing the equality of two instances of this class
	 */
	@Test
	public void testEqualsContract()
	{
		EqualsVerifier.forClass(AdventureNotificationCompleteReport.class).verify();
	}
}

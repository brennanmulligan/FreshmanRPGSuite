package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Test for ObjectiveNotifcationCompleteReport
 * @author Ryan
 *
 */
public class ObjectiveNotificationCompleteReportTest
{

	/**
	 * Test init and getters for report
	 */
	@Test
	public void testInitialization() 
	{
		ObjectiveNotificationCompleteReport report = new ObjectiveNotificationCompleteReport(1, 2, 3);
		
		assertEquals(1, report.getPlayerID());
		assertEquals(2, report.getQuestID());
		assertEquals(3, report.getObjectiveID());
	}
	
	/**
	 * Testing the equality of two instances of this class
	 */
	@Test
	public void testEqualsContract()
	{
		EqualsVerifier.forClass(ObjectiveNotificationCompleteReport.class).verify();
	}
}

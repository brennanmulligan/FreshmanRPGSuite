package model.reports;

import static org.junit.Assert.assertEquals;

import datasource.ServerSideTest;
import org.junit.Test;

import datatypes.PlayersForTest;

/**
 *
 * @author Stephen Clabaugh, Adam Pine, Jacob Knight
 * Tests functionality of InteractableObjectTextReport
 *
 */
public class InteractableObjectTextReportTest extends ServerSideTest
{

	/**
	 * Test the constructor works correctly.
	 */
	@Test
	public void testConstructor()
	{
		InteractableObjectTextReport report = new InteractableObjectTextReport(PlayersForTest.ANDY.getPlayerID(), "Test Message");

		assertEquals(PlayersForTest.ANDY.getPlayerID(), report.getPlayerID());
		assertEquals("Test Message", report.getText());
	}
}
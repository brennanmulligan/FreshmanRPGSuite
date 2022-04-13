package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.OptionsManager;
import datatypes.PlayersForTest;

/**
 *
 * @author Stephen Clabaugh, Adam Pine, Jacob Knight
 * Tests functionality of InteractableObjectTextReport
 *
 */
public class InteractableObjectTextReportTest
{

	/**
	 * Setup for testing InteractableObjectTextReport
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

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
package model.reports;

import static org.junit.Assert.assertEquals;

import datatypes.PlayersForTest;
import org.junit.Before;
import org.junit.Test;

import model.OptionsManager;

/**
 *
 * @author Adam Pine, Jacob Knight
 * Tests functionality of InteractableObjectBuffReport
 *
 */
public class InteractableObjectBuffReportTest
{

	/**
	 * setup for testing InteractableObjectBuffReport
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 * Test that the constructor works correctly
	 */
	@Test
	public void testConstructor()
	{
		InteractableObjectBuffReport report = new InteractableObjectBuffReport(PlayersForTest.ANDY.getPlayerID(), 5);

		assertEquals(PlayersForTest.ANDY.getPlayerID(), report.getPlayerID());
		assertEquals(5, report.getExpPointPool());
	}
}
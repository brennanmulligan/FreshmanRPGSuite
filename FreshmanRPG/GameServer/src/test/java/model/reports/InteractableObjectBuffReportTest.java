package model.reports;

import static org.junit.Assert.assertEquals;

import datasource.ServerSideTest;
import datatypes.PlayersForTest;
import org.junit.Test;

/**
 *
 * @author Adam Pine, Jacob Knight
 * Tests functionality of InteractableObjectBuffReport
 *
 */
public class InteractableObjectBuffReportTest extends ServerSideTest
{

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
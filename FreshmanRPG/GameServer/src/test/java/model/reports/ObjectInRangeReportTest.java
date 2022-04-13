package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test that the InteractionDeniedReport works correctly
 *
 */
public class ObjectInRangeReportTest
{

	/**
	 * Makes sure get player id returns the correct id
	 */
	@Test
	public void testGetPlayerId()
	{
		InteractionDeniedReport report = new InteractionDeniedReport(1);

		assertEquals(1, report.getPlayerID());
	}

}

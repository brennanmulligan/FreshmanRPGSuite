package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Unit tests for item purchased report
 * @author Kevin Marek
 *
 */
public class ItemPurchasedReportTest
{
	/**
	 * Test constructor for item purchased report
	 */
	@Test
	public void testInitialization() 
	{
		ItemPurchasedReport report = new ItemPurchasedReport(1, 1);

		assertEquals(1, report.getPlayerID());
		assertEquals(1, report.getPrice());

	}

	/**
	 * Testing the equality of two instances of this class
	 */
	@Test
	public void testEqualsContract() 
	{
		EqualsVerifier.forClass(ItemPurchasedReport.class).verify();
	}

}

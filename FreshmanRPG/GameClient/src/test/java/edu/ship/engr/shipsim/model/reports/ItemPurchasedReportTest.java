package edu.ship.engr.shipsim.model.reports;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for item purchased report
 *
 * @author Kevin Marek
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

package edu.ship.engr.shipsim.model.reports;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests a file message which is designed to carry a tmx file
 *
 * @author merlin
 */
public class AreaCollisionReportTest
{
    /**
     * Basic test of getters and constructing
     */
    @Test
    public void creation()
    {
        AreaCollisionReport report = new AreaCollisionReport(1, "test");
        assertEquals(1, report.getPlayerID());
        assertEquals("test", report.getAreaName());
    }

    /**
     * Testing the equality of two instances of this class
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(AreaCollisionReport.class).verify();
    }
}

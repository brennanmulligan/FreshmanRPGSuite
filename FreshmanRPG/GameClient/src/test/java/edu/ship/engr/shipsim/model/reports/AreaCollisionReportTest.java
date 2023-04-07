package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests a file message which is designed to carry a tmx file
 *
 * @author merlin
 */
@GameTest("GameClient")
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
        EqualsVerifier.forClass(AreaCollisionReport.class).withRedefinedSuperclass().verify();
    }
}

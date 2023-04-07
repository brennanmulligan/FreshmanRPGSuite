package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the equality of the ChangeMapReport
 *
 * @author Steve
 */
@GameTest("GameClient")
public class ChangeMapReportTest
{

    /**
     * Basic test of getters and constructing
     */
    @Test
    public void creation()
    {
        ChangeMapReport report = new ChangeMapReport(1, new Position(0, 0), "destination");
        assertEquals(new Position(0, 0), report.getPosition());
        assertEquals("destination", report.getMapName());
    }

    /**
     * Testing the equality of two instances of this class
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(ChangeMapReport.class).withRedefinedSuperclass().verify();
    }
}

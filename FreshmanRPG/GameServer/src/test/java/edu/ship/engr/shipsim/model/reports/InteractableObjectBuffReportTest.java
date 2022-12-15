package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Adam Pine, Jacob Knight
 * Tests functionality of InteractableObjectBuffReport
 */
@GameTest("GameServer")
public class InteractableObjectBuffReportTest
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
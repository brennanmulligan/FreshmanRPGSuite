package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test that the InteractionDeniedReport works correctly
 */
@GameTest("GameServer")
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

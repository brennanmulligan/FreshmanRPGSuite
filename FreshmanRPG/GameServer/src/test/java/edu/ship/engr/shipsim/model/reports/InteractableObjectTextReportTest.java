package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Stephen Clabaugh, Adam Pine, Jacob Knight
 * Tests functionality of InteractableObjectTextReport
 */
@GameTest("GameServer")
public class InteractableObjectTextReportTest
{

    /**
     * Test the constructor works correctly.
     */
    @Test
    public void testConstructor()
    {
        InteractableObjectTextReport report = new InteractableObjectTextReport(PlayersForTest.ANDY.getPlayerID(), "Test Message");

        assertEquals(PlayersForTest.ANDY.getPlayerID(), report.getPlayerID());
        assertEquals("Test Message", report.getText());
    }
}
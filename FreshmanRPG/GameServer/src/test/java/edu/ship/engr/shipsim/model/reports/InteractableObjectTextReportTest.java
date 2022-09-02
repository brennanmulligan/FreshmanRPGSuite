package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Stephen Clabaugh, Adam Pine, Jacob Knight
 * Tests functionality of InteractableObjectTextReport
 */
public class InteractableObjectTextReportTest extends ServerSideTest
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
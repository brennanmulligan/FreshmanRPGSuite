package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Adam Pine, Jacob Knight
 * Tests functionality of InteractableObjectBuffReport
 */
public class InteractableObjectBuffReportTest extends ServerSideTest
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
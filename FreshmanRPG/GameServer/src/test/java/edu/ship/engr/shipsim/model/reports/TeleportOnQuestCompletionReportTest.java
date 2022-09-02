package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.criteria.GameLocationDTO;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.ObjectivesForTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test to make sure the TeleportOnQuestFinishReport works
 *
 * @author Chris Hersh, Zach Thompson, Abdul
 */
public class TeleportOnQuestCompletionReportTest extends ServerSideTest
{

    /**
     * Tests the creation of the TeleportOnQuestFinishReport
     */
    @Test
    public void testInitialization()
    {
        String host = "hostname";
        int port = 22;
        GameLocationDTO gl = new GameLocationDTO("quad.tmx", null);

        TeleportOnQuestCompletionReport report = new TeleportOnQuestCompletionReport(1,
                ObjectivesForTest.QUEST1_OBJECTIVE_1.getQuestID(), gl, host, port);

        assertEquals(1, report.getPlayerID());
        assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getQuestID(), report.getQuestID());
        assertEquals(gl, report.getLocation());
        assertEquals(host, report.getHostName());
        assertEquals(port, report.getPortNumber());
    }

}
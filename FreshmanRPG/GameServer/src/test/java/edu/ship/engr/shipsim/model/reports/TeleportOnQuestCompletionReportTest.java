package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.criteria.GameLocationDTO;
import edu.ship.engr.shipsim.datatypes.ObjectivesForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test to make sure the TeleportOnQuestFinishReport works
 *
 * @author Chris Hersh, Zach Thompson, Abdul
 */
@GameTest("GameServer")
public class TeleportOnQuestCompletionReportTest
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
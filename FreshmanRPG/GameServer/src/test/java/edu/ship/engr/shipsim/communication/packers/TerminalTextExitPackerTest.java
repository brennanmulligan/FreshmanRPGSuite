package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.TerminalTextExitMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.reports.TerminalTextExitReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests to ensure a TerminaTextExitPacker can process an ExitReport and send a TerminalTextExitReport
 * <p>
 * Authors: John G. , Ian L.
 */
@GameTest("GameServer")
@ResetPlayerManager
public class TerminalTextExitPackerTest
{
    /**
     * Make sure that the exit report is properly processed into a message.
     */
    @Test
    public void testPacking()
    {
        String region = "test";

        TerminalTextExitReport report = new TerminalTextExitReport(PlayersForTest.MERLIN.getPlayerID());
        TerminalTextExitPacker packer = new TerminalTextExitPacker();
        TerminalTextExitMessage msg = (TerminalTextExitMessage) packer.pack(report);

        assertEquals(PlayersForTest.MERLIN.getPlayerID(), msg.getRelevantPlayerID());
    }


}

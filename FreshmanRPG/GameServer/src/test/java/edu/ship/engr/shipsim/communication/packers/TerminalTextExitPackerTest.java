package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.TerminalTextExitMessage;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.reports.TerminalTextExitReport;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests to ensure a TerminaTextExitPacker can process an ExitReport and send a TerminalTextExitReport
 * <p>
 * Authors: John G. , Ian L.
 */
public class TerminalTextExitPackerTest extends ServerSideTest
{

    /**
     * Setup the mock datasource before each run
     */
    @Before
    public void setup()
    {
        PlayerManager.resetSingleton();
    }

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

        assertEquals(PlayersForTest.MERLIN.getPlayerID(), msg.getPlayerID());
    }


}

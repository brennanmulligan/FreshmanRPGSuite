package communication.packers;

import communication.messages.TerminalTextExitMessage;
import datatypes.PlayersForTest;
import model.OptionsManager;
import model.PlayerManager;
import model.reports.TerminalTextExitReport;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests to ensure a TerminaTextExitPacker can process an ExitReport and send a TerminalTextExitReport
 *
 * Authors: John G. , Ian L.
 */
public class TerminalTextExitPackerTest
{

    /**
     *Setup the mock datasource before each run
     */
    @Before
    public void setup()
    {
        PlayerManager.resetSingleton();
        OptionsManager.getSingleton().setUsingMocKDataSource(true);
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

package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.TerminalResponseReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the CommandRecieveTerminalText class
 *
 * @author Nathaniel, Ben
 */
@GameTest("GameClient")
public class CommandRecieveTerminalResponseTest
{

    int playerID = 1;
    String terminalText = "Yep, that's some text";

    /**
     * Tests that we can create Command and set/get its fields
     */
    @Test
    public void testCreateCommand()
    {
        int playerID = 1;
        String terminalText = "Yep, that's some text";

        CommandReceiveTerminalResponse cmd = new CommandReceiveTerminalResponse(playerID, terminalText);
        assertEquals(playerID, cmd.getPlayerID());
        assertEquals(terminalText, cmd.getTerminalResult());
    }

    /**
     * Tests that the command executes
     */
    @Test
    public void testExecute()
    {
        ReportObserver obs = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton().registerObserver(obs, TerminalResponseReport.class);
        CommandReceiveTerminalResponse cmd = new CommandReceiveTerminalResponse(playerID, terminalText);
        cmd.execute();

        verify(obs, times(1)).receiveReport(new TerminalResponseReport(playerID, terminalText));
    }

}

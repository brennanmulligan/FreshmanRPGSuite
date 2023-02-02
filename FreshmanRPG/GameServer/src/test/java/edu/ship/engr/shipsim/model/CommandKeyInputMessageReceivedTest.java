package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.KeyInputRecievedReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests functionality for a command for receiving key input from the user.
 *
 * @author Ian Keefer & TJ Renninger
 */
@GameTest("GameServer")
public class CommandKeyInputMessageReceivedTest
{

    /**
     * Test creation of a CommandKeyInputMessageReceived
     */
    @Test
    public void testInitializaiton()
    {
        String input = "q";
        int id = 1;
        CommandKeyInputMessageReceived command = new CommandKeyInputMessageReceived(input, id);
        assertEquals(input, command.getInput());

    }

    /**
     * Test execution of a CommandKeyInputMessageReceived
     */
    @Test
    public void testExecute()
    {
        ReportObserver obs = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton().registerObserver(obs, KeyInputRecievedReport.class);
        String input = "q";
        int id = 1;
        CommandKeyInputMessageReceived command = new CommandKeyInputMessageReceived(input, id);
        command.execute();

        verify(obs, times(1)).receiveReport(new KeyInputRecievedReport(input, id));
    }

}

package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.ClientKeyInputSentReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Command to test key input command
 *
 * @author Ian Keefer & TJ Renninger
 */
@GameTest("GameClient")
public class CommandKeyInputSentTest
{

    /**
     * Test initialization of a CommandKeyInputSent
     */
    @Test
    public void testInitialization()
    {
        ReportObserver obs = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton().registerObserver(obs, ClientKeyInputSentReport.class);

        String input = "q";
        CommandKeyInputSent cis = new CommandKeyInputSent(input);
        assertEquals(input, cis.getInput());
        cis.execute();

        verify(obs, times(1)).receiveReport(new ClientKeyInputSentReport(input));
    }

}

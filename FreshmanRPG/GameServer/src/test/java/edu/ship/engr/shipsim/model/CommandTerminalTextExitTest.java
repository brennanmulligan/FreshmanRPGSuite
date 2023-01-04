package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.reports.TerminalTextExitReport;
import edu.ship.engr.shipsim.model.terminal.CommandTerminalTextExit;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetModelFacade;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Test to ensure our command sends the ExitReport upon execution
 * <p>
 * Authors: John G. , Ian L.
 */
@GameTest("GameServer")
@ResetModelFacade
public class CommandTerminalTextExitTest
{
    /**
     * Test executing a remove player command
     */
    @Test
    public void testExecution() throws ModelFacadeException
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a TerminalTextExitReport is sent
        connector.registerObserver(observer, TerminalTextExitReport.class);

        PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());

        CommandTerminalTextExit cmd = new CommandTerminalTextExit();
        cmd.execute(PlayersForTest.MERLIN.getPlayerID(), null);

        ModelFacadeTestHelper.waitForFacadeToProcess();

        verify(observer, times(1)).receiveReport(any(TerminalTextExitReport.class));
    }


}

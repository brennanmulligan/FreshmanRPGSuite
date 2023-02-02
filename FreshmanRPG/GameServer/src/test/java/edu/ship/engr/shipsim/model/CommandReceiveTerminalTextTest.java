package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.reports.ReceiveTerminalTextReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests that the GetAllConnectedPlayers command gets executed properly.
 *
 * @author Nathaniel, Kanza
 */
@GameTest("GameServer")
public class CommandReceiveTerminalTextTest
{
    /**
     * If it executes, we're golden -Nathaniel
     */
    @Test
    public void testExecute()
    {
        ReportObserver obs = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton().registerObserver(obs, ReceiveTerminalTextReport.class);

        int playerID = PlayersForTest.MERLIN.getPlayerID();
        PlayerManager.getSingleton().getConnectedPlayers();
        CommandReceiveTerminalText cgaop = new CommandReceiveTerminalText(playerID, "who");

        cgaop.execute();

        verify(obs, times(1)).receiveReport(isA(ReceiveTerminalTextReport.class));
    }
}

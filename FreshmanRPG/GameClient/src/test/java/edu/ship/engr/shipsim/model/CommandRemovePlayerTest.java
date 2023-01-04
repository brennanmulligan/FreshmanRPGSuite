package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.PlayerDisconnectedFromAreaServerReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

/**
 * @author nhydock
 */
@GameTest("GameClient")
@ResetClientModelFacade
@ResetReportObserverConnector
public class CommandRemovePlayerTest
{
    /**
     * Test executing a remove player command
     */
    @Test
    public void testExecution()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a PlayerDisconnectedFromAreaServerReport is sent
        connector.registerObserver(observer, PlayerDisconnectedFromAreaServerReport.class);

        // setup the player
        ClientPlayerManager pm = ClientPlayerManager.getSingleton();
        pm.initiateLogin("john", "pw");

        assertDoesNotThrow(() ->
        {
            pm.finishLogin(1);
        }, "Could not create this client's player from login");

        // set up the remove command and execute it
        CommandRemovePlayer cmd = new CommandRemovePlayer(1);
        cmd.execute();

        // since the remove command sends a PlayerDisconnectedFromAreaServerReport, verify that the observer was notified of it
        verify(observer, times(1)).receiveReport(any(PlayerDisconnectedFromAreaServerReport.class));
    }
}

package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.NewMapReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

/**
 * @author Merlin
 */
@GameTest("GameClient")
@ResetClientModelFacade
@ResetReportObserverConnector
public class CommandNewMapTest
{

    /**
     * TODO: Add documentation
     */
    @Test
    public void testExecution()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a NewMapReport is sent
        connector.registerObserver(observer, NewMapReport.class);

        // setup the player
        ClientPlayerManager pm = ClientPlayerManager.getSingleton();
        pm.initiateLogin("john", "pw");
        try
        {
            pm.finishLogin(1);
        }
        catch (AlreadyBoundException | NotBoundException e)
        {
            e.printStackTrace();
            fail("Could not create this client's player from login");
        }

        CommandNewMap cmd = new CommandNewMap("simple.tmx", "JUST FOR TESTS");
        cmd.execute();

        MapManager mapManager = MapManager.getSingleton();
        NewMapReport expectedReport = new NewMapReport(mapManager.getTiledMap());

        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }
}

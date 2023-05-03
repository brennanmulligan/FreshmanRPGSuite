package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.PlayerMovedReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Merlin
 */
@GameTest("GameServer")
@ResetPlayerManager
@ResetReportObserverConnector
public class CommandMovePlayerSilentlyTest
{
    /**
     * Update a player's position from id
     */
    @Test
    public void testValidPlayer()
    {
        Position startPosition = new Position(0, 0);
        Position newPosition = new Position(10, 10);

        PlayerManager.getSingleton().addPlayer(1);
        Player p = PlayerManager.getSingleton().getPlayerFromID(1);
        p.setPosition(startPosition);

        assertEquals(startPosition, p.getPosition());

        CommandMovePlayerSilently cmd = new CommandMovePlayerSilently("newMap", 1, newPosition);
        cmd.execute();

        assertEquals(newPosition, p.getPosition());
        assertEquals("newMap", p.getMapName());
    }

    /**
     * Make sure anyone who is observing for movement reports doesn't hear about
     * this one
     */
    @Test
    public void doesntNotifyObservers()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a PlayerMovedReport is sent
        connector.registerObserver(observer, PlayerMovedReport.class);

        PlayerManager.getSingleton().addPlayer(1);

        CommandMovePlayerSilently cmd = new CommandMovePlayerSilently("newMap", 1, new Position(4, 3));
        cmd.execute();

        verify(observer, never()).receiveReport(any(PlayerMovedReport.class));
    }
}

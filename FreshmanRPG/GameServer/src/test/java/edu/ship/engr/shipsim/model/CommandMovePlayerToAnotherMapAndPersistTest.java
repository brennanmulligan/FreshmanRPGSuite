package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.datatypes.ServersForTest;
import edu.ship.engr.shipsim.model.reports.PlayerMovedReport;
import edu.ship.engr.shipsim.model.reports.PlayerReadyToTeleportReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetQualifiedObservableConnector;
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
@ResetQualifiedObservableConnector
public class CommandMovePlayerToAnotherMapAndPersistTest
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
        p.setPlayerPosition(startPosition);

        assertEquals(startPosition, p.getPlayerPosition());

        CommandMovePlayerToAnotherMapAndPersist
                cmd = new CommandMovePlayerToAnotherMapAndPersist(1, "newMap", newPosition);
        cmd.execute();

        assertEquals(newPosition, p.getPlayerPosition());
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
        QualifiedObservableConnector connector = spy(QualifiedObservableConnector.getSingleton());
        QualifiedObserver observer = mock(QualifiedObserver.class);

        // register the observer to be notified if a PlayerMovedReport is sent
        connector.registerObserver(observer, PlayerMovedReport.class);

        PlayerManager.getSingleton().addPlayer(1);

        CommandMovePlayerToAnotherMapAndPersist cmd = new CommandMovePlayerToAnotherMapAndPersist(1, "newMap", new Position(4, 3));
        cmd.execute();

        verify(observer, never()).receiveReport(any(PlayerMovedReport.class));
    }

    /**
     * Update a player's position from id
     */
    @Test
    public void testNoPlayer()
    {
        Position newPosition = new Position(10, 10);

        CommandMovePlayerToAnotherMapAndPersist
                cmd = new CommandMovePlayerToAnotherMapAndPersist(-1, "a map", newPosition);
        assertFalse(cmd.execute());
    }

    /**
     * Test that persistence happens
     */
    @Test
    public void testPersists()
    {
        Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        player.setPlayerPositionWithoutNotifying(new Position(101, 101));
        player.setAppearanceType("appearance");

        CommandMovePlayerToAnotherMapAndPersist
                cmd = new CommandMovePlayerToAnotherMapAndPersist(player.getPlayerID(),
                ServersForTest.CUB.getMapName(),
                new Position(101, 101));
        cmd.execute();

        PlayerManager.resetSingleton();

        Player fetched = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        assertEquals(player.getPlayerPosition(), fetched.getPlayerPosition());
        assertEquals(player.getAppearanceType(), fetched.getAppearanceType());
    }

    /**
     * Make sure that a report is thrown after command execution.
     */
    @Test
    public void testSendsPlayerPersistedReport()
    {
        // mock the connector and observer
        QualifiedObservableConnector connector = spy(QualifiedObservableConnector.getSingleton());
        QualifiedObserver observer = mock(QualifiedObserver.class);

        // register the observer to be notified if a PlayerReadyToTeleportReport is sent
        connector.registerObserver(observer, PlayerReadyToTeleportReport.class);

        PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());

        CommandMovePlayerToAnotherMapAndPersist command = new CommandMovePlayerToAnotherMapAndPersist(PlayersForTest.MERLIN.getPlayerID(), "test map", new Position(3, 5));
        command.execute();

        verify(observer, times(1)).receiveReport(any(PlayerReadyToTeleportReport.class));
    }

}

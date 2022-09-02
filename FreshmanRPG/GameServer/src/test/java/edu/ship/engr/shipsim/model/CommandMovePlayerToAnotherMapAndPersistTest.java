package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.PlayerMovedReport;
import edu.ship.engr.shipsim.model.reports.PlayerReadyToTeleportReport;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Merlin
 */
public class CommandMovePlayerToAnotherMapAndPersistTest extends ServerSideTest
{

    /**
     * Reset PlayerManager
     */
    @Before
    public void localSetup()
    {
        PlayerManager.resetSingleton();
        QualifiedObservableConnector.resetSingleton();
    }

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
        PlayerManager.getSingleton().addPlayer(1);
        QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
        QualifiedObservableConnector.getSingleton().registerObserver(obs, PlayerMovedReport.class);
        EasyMock.replay(obs);

        CommandMovePlayerToAnotherMapAndPersist
                cmd = new CommandMovePlayerToAnotherMapAndPersist(1, "newMap",
                new Position(4, 3));
        cmd.execute();

        EasyMock.verify(obs);
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
                cmd = new CommandMovePlayerToAnotherMapAndPersist(player.getPlayerID(), "a map",
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
        int id = PlayersForTest.MERLIN.getPlayerID();
        PlayerManager.getSingleton().addPlayer(id);
        CommandMovePlayerToAnotherMapAndPersist
                command = new CommandMovePlayerToAnotherMapAndPersist(id, "test map",
                new Position(3, 5));
        PlayerReadyToTeleportReport expected = new PlayerReadyToTeleportReport(id, "test map");
        QualifiedObserver observer = EasyMock.createMock(QualifiedObserver.class);
        QualifiedObservableConnector.getSingleton().registerObserver(observer, PlayerReadyToTeleportReport.class);
        observer.receiveReport(expected);
        EasyMock.replay(observer);

        assertTrue(command.execute());
        EasyMock.verify(observer);
    }

}

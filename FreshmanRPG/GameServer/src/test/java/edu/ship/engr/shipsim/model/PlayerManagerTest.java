package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.PlayerScoreRecord;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.AddExistingPlayerReport;
import edu.ship.engr.shipsim.model.reports.PlayerConnectionReport;
import edu.ship.engr.shipsim.model.reports.PlayerDisconnectedReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Merlin
 */
@GameTest("GameServer")
@ResetPlayerManager
@ResetReportObserverConnector
public class PlayerManagerTest
{
    /**
     * Make sure PlayerManager is a resetable singleton
     */
    @Test
    public void isSingleton()
    {
        PlayerManager pm1 = PlayerManager.getSingleton();
        PlayerManager pm2 = PlayerManager.getSingleton();
        assertSame(pm1, pm2);
        PlayerManager.resetSingleton();
        assertNotSame(pm1, PlayerManager.getSingleton());
    }

    /**
     * Make sure we can add a player to the listS
     */
    @Test
    public void canAddPlayer()
    {
        PlayerManager.getSingleton().addPlayer(1);
        assertEquals(1, PlayerManager.getSingleton().numberOfPlayers());
        Player p = PlayerManager.getSingleton().getPlayerFromID(1);
        assertEquals(1, p.getPlayerID());
        assertTrue(PlayerManager.getSingleton().getConnectedPlayers().contains(p));
    }

    /**
     * Make sure that the PlayerManager notifies with the correct object type
     */
    @Test
    public void notifiesOnAddPlayer()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a PlayerConnectionReport is sent
        connector.registerObserver(observer, PlayerConnectionReport.class);

        PlayerManager.getSingleton().addPlayer(2);

        // since a player was added, the observer should be notified with a PlayerConnectionReport
        verify(observer, times(1)).receiveReport(any(PlayerConnectionReport.class));
    }

    /**
     * When a player is added, we need to send it reports about all of the other
     * players in the system
     */
    @Test
    public void notifiesAboutExistingPlayersOnAddPlayer() throws DatabaseException, IllegalQuestChangeException
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a AddExistingPlayerReport is sent
        connector.registerObserver(observer, AddExistingPlayerReport.class);

        // add a player that will get notified by the next player that joins
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());

        // add a second player, which will notify the other players about their existence
        // the pin is included to mimic a real login, and 1111 is the magic code which always works
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MATT.getPlayerID(), 1111);

        AddExistingPlayerReport expectedReport = new AddExistingPlayerReport(PlayersForTest.MATT.getPlayerID(),
                PlayersForTest.MERLIN.getPlayerID(), PlayersForTest.MERLIN.getPlayerName(),
                PlayersForTest.MERLIN.getAppearanceType(), PlayersForTest.MERLIN.getPosition(),
                PlayersForTest.MERLIN.getCrew(), PlayersForTest.MERLIN.getMajor(), PlayersForTest.MERLIN.getSection(), new ArrayList<>());

        // since a second player joined, the observer should receive an AddExistingPlayerReport
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }

    /**
     * Make sure that we can get a players id from the player name
     *
     * @throws PlayerNotFoundException shouldn't
     */
    @Test
    public void canGetPlayerIDFromPlayerNameOnlyOne() throws PlayerNotFoundException
    {
        PlayerManager.getSingleton().addPlayer(1);
        assertEquals(1, PlayerManager.getSingleton().getPlayerIDFromPlayerName("John"));
    }

    /**
     * Make sure that we can get a players id from the player name
     *
     * @throws PlayerNotFoundException shouldn't
     */
    @Test
    public void canGetPlayerIDFromPlayerName() throws PlayerNotFoundException
    {
        PlayerManager.getSingleton().addPlayer(1);
        PlayerManager.getSingleton().addPlayer(2);
        assertEquals(2, PlayerManager.getSingleton().getPlayerIDFromPlayerName("Merlin"));
    }

    /**
     * Test the removal of a player
     *
     * @throws DatabaseException           thrown when cannot find player by id
     * @throws IllegalQuestChangeException thrown if you try to change quest state to illegal state
     */
    @Test
    public void canRemovePlayer() throws DatabaseException, IllegalQuestChangeException
    {
        PlayerManager.getSingleton().addPlayer(1);
        assertEquals(1, PlayerManager.getSingleton().getPlayerFromID(1).getPlayerID());

        PlayerManager.getSingleton().removePlayer(1);
        assertNull(PlayerManager.getSingleton().getPlayerFromID(1));

    }

    /**
     * Make sure the correct exception is thrown if we search for a player whose
     * name we don't know
     */
    @Test
    public void playerNameNotFound()
    {
        assertThrows(PlayerNotFoundException.class, () ->
        {
            PlayerManager.getSingleton().getPlayerIDFromPlayerName("henry");
        });
    }

    /**
     * Test that a player can be persisted by saving an attribute and fetching
     * the player again
     *
     * @throws DatabaseException           shouldn't
     * @throws IllegalQuestChangeException the state changed illegally
     */
    @Test
    public void playerIsSaved() throws DatabaseException, IllegalQuestChangeException
    {
        Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        player.setPosition(new Position(100, 100));
        assertTrue(PlayerManager.getSingleton().persistPlayer(player.getPlayerID()));

        PlayerManager.resetSingleton();

        Player fetched = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        assertEquals(new Position(100, 100), fetched.getPosition());
    }

    /**
     * Test that the known npcs will be in the database
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void testNpcsLoaded() throws DatabaseException
    {
        OptionsManager om = OptionsManager.getSingleton();
        om.setTestMode(true);
        om.updateMapInformation(PlayersForTest.QUIZBOT.getMapName(), "localhost", 1874);
        PlayerManager.getSingleton().loadNpcs(true);

        assertNotNull(PlayerManager.getSingleton().getPlayerFromID(PlayersForTest.QUIZBOT.getPlayerID()));

    }

    /**
     * Make sure it can get the high score list
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void canGetTopTen() throws DatabaseException
    {
        ArrayList<PlayerScoreRecord> result = PlayerManager.getSingleton().getTopTenPlayers();
        assertEquals(10, result.size());
    }

    /**
     * Test that we can receive a report
     */
    @Test
    public void receiveReport()
    {
        PlayerManager pm = PlayerManager.getSingleton();
        pm.addPlayer(10);
        assertNotNull(pm.getPlayerFromID(10));
        PlayerDisconnectedReport report = new PlayerDisconnectedReport(10);
        pm.receiveReport(report);
        assertNull(pm.getPlayerFromID(10));
    }

    /**
     * Test we can add a map to the player through the constructor in the playerManager
     */
    @Test
    public void testAddMap()
    {
        PlayerManager pm = PlayerManager.getSingleton();
        pm.addPlayer(2);
        assertEquals(PlayersForTest.MERLIN.getMapsVisited(), pm.getPlayerFromID(2).getPlayerVisitedMaps());
    }

    @Test
    public void testIsNPC() throws DatabaseException
    {
        OptionsManager om = OptionsManager.getSingleton();
        om.setTestMode(true);
        om.updateMapInformation(PlayersForTest.RED_HAT.getMapName(), "localhost", 1874);

        PlayerManager pm = PlayerManager.getSingleton();
        pm.loadNpcs(true);
        pm.addPlayer(PlayersForTest.MERLIN.getPlayerID());
        pm.addPlayer(PlayersForTest.RED_HAT.getPlayerID());
        assertFalse(pm.isNPC(PlayersForTest.MERLIN.getPlayerID()));
        assertTrue(pm.isNPC(PlayersForTest.RED_HAT.getPlayerID()));
    }

}
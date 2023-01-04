package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.LoginInitiatedReport;
import edu.ship.engr.shipsim.model.reports.PlayerConnectedToAreaServerReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.apache.commons.compress.utils.Lists;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the player manager to make sure it maintains the list of players
 * correctly
 *
 * @author merlin
 */
@GameTest("GameClient")
@ResetClientPlayerManager
@ResetReportObserverConnector
public class ClientPlayerManagerTest
{

    /**
     * There should be only one player
     */
    @Test
    public void testSingleton()
    {
        ClientPlayerManager player1 = ClientPlayerManager.getSingleton();
        assertSame(player1, ClientPlayerManager.getSingleton());
        ClientPlayerManager.resetSingleton();
        assertNotSame(player1, ClientPlayerManager.getSingleton());
    }

    /**
     * Make sure we can add players and retrieve them by their player names
     */
    @RepeatedTest(10)
    public void testInitializePlayers(RepetitionInfo info)
    {
        ClientPlayerManager manager = ClientPlayerManager.getSingleton();

        // setup data for initialization and assertion
        int playerId = info.getCurrentRepetition(); // uses the current test iteration as the player id
        String playerName = "Awesome Player";
        ArrayList<VanityDTO> vanities = Lists.newArrayList();
        Position position = mock(Position.class);
        Crew randomCrew = Crew.getRandomCrew();
        Major randomMajor = Major.getRandomMajor();

        // initialize the player in the manager
        manager.initializePlayer(playerId, playerName, vanities, position, randomCrew, randomMajor, playerId);

        // grab the player from the manager
        ClientPlayer player = manager.getPlayerFromID(playerId);

        // verify that the data is still the same
        assertEquals(playerId, player.getID());
        assertEquals(playerName, player.getName());
        assertEquals(vanities, player.getVanities());
        assertEquals(position, player.getPosition());
        assertEquals(randomCrew, player.getCrew());
        assertEquals(randomMajor, player.getMajor());
        assertEquals(playerId, player.getSection());
    }

    /**
     * Just make sure he remembers when a login is started
     */
    @Test
    public void canStartToLogin()
    {
        ClientPlayerManager p = ClientPlayerManager.getSingleton();
        assertFalse(p.isLoginInProgress());
        p.initiateLogin("Fred", "mommy");
        assertTrue(p.isLoginInProgress());
    }

    /**
     * Make sure that observers who want to be told when a login is initiated
     * are told
     */
    @Test
    public void notifiesOnLoginInitiation()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a LoginInitiatedReport is sent
        connector.registerObserver(observer, LoginInitiatedReport.class);

        // set up exception for later
        LoginInitiatedReport expectedReport = new LoginInitiatedReport("Fred", "daddy");

        // initialize a player login, which should send out a LoginInitiatedReport
        ClientPlayerManager.getSingleton().initiateLogin("Fred", "daddy");

        // since the ClientPlayerManager sent out a report, verify that the observer was notified of it
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }

    /**
     * Test all the conditions behind setting this client's player to ensure the
     * method is secure
     */
    @Test
    public void testSettingThisClientsPlayer()
    {
        ClientPlayerManager pm = ClientPlayerManager.getSingleton();

        // test setting player without having tried logging in
        assertThrows(NotBoundException.class, () ->
        {
            pm.finishLogin(1);
        }, "Player was already bound to player manager");

        assertDoesNotThrow(() ->
        {
            pm.initiateLogin("bilbo", "baggins");
            pm.finishLogin(1);
        }, "Login should have been processed, and setting should work");

        // player shouldn't be able to be set after having logged in without
        // first logging out
        assertThrows(AlreadyBoundException.class, () ->
        {
            pm.finishLogin(2);
        }, "Player is already logged in.");
    }

    /**
     * Initialize player should send a PlayerConnectedToAreaServerReport
     */
    @Test
    public void testInitializePlayerFiresReport()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a PlayerConnectedToAreaServerReport is sent
        connector.registerObserver(observer, PlayerConnectedToAreaServerReport.class);

        ClientPlayerManager pm = ClientPlayerManager.getSingleton();
        Position pos = new Position(1, 2);
        List<VanityDTO> vanityDTOS = new ArrayList<>();
        VanityDTO vanityDTO = new VanityDTO();
        vanityDTOS.add(vanityDTO);

        // set up exception for later
        PlayerConnectedToAreaServerReport expectedReport = new PlayerConnectedToAreaServerReport(1, "Player 1", pos, Crew.FORTY_PERCENT, Major.COMPUTER_ENGINEERING, false, vanityDTOS);

        // initialize a player login, which should send out a PlayerConnectedToAreaServerReport
        pm.initializePlayer(1, "Player 1", vanityDTOS, pos, Crew.FORTY_PERCENT, Major.COMPUTER_ENGINEERING, 10);

        // since the ClientPlayerManager sent out a report, verify that the observer was notified of it
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }
}

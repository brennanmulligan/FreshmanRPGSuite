package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.TeleportationInitiationMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.datatypes.ServersForTest;
import edu.ship.engr.shipsim.model.ModelFacadeException;
import edu.ship.engr.shipsim.model.ModelFacadeTestHelper;
import edu.ship.engr.shipsim.model.OptionsManager;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.ReportObserver;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.reports.PlayerMovedReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test the handler for GetServerInfoMessages
 *
 * @author Merlin
 */
@GameTest("GameServer")
@ResetModelFacade(waitUntilEmpty = true)
@ResetPlayerManager
public class TeleportationInitiationHandlerTest
{
    /**
     * In the game, teleportation is another method of player movement
     *
     * <p>
     * <p>
     * Normally when a player moves, a {@link PlayerMovedReport report} is sent
     * which notifies other players that there was movement.
     *
     * <p>
     * <p>
     * But when a player is teleported to a new map, a report should not be sent.
     *
     * <p>
     * <p>
     * Since the other players shouldn't see the player move, there is no need to notify
     * them of the change.
     *
     * <p>
     * <p>
     * This test verifies that a PlayerMovedReport isn't sent after teleportation
     *
     * @throws ModelFacadeException shouldn't
     */
    @Test
    public void generatesCorrectResponse() throws ModelFacadeException
    {
        Position playerPos = new Position(5, 6);

        // player is initially in the quad map
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());

        // setup accumulator and handler for message processing later on
        StateAccumulator accum = new StateAccumulator(null).setPlayerId(
                PlayersForTest.MERLIN.getPlayerID());
        TeleportationInitiationHandler handler =
                new TeleportationInitiationHandler();
        handler.setAccumulator(accum);

        // teleport message will move player from quad -> first server
        TeleportationInitiationMessage msg =
                new TeleportationInitiationMessage(PlayersForTest.MERLIN.getPlayerID(), false,
                        ServersForTest.FIRST_SERVER.getMapName(), playerPos);

        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register observer to be notified if the move wasn't handled silently
        connector.registerObserver(observer, PlayerMovedReport.class);

        handler.process(msg);

        ModelFacadeTestHelper.waitForFacadeToProcess();

        // reset the singleton to clear the player
        // this is in preparation to verify the information in the database
        PlayerManager.resetSingleton();

        // add the player to the manager again
        // this ensures that the player is refreshed from the database
        PlayerManager playerManager = spy(PlayerManager.getSingleton());
        playerManager.addPlayer(PlayersForTest.MERLIN.getPlayerID());

        // make sure we moved the player without notifying observers
        Player p = playerManager.getPlayerFromID(PlayersForTest.MERLIN.getPlayerID());
        assertEquals(playerPos, p.getPosition());

        // since the player was teleported, move reports shouldn't be sent/received
        verify(connector, never()).sendReport(any(PlayerMovedReport.class));
        verify(observer, never()).receiveReport(any(PlayerMovedReport.class));
    }

    /**
     * It should correctly report the type of messages it handles
     */
    @Test
    public void messageTypeCorrect()
    {
        TeleportationInitiationHandler handler = new TeleportationInitiationHandler();
        assertEquals(TeleportationInitiationMessage.class,
                handler.getMessageTypeWeHandle());
    }

    /**
     * Reset the PlayerManager
     */
    @BeforeEach
    public void reset()
    {
        OptionsManager.getSingleton().setMapFileTitle(PlayersForTest.MERLIN.getMapName());
    }
}

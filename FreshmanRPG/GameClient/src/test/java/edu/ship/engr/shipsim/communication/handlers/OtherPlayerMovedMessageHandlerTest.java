package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.OtherPlayerMovedMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.ClientModelTestUtilities;
import edu.ship.engr.shipsim.model.CommandClientMovePlayer;
import edu.ship.engr.shipsim.model.MapManager;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author Andrew
 * <p>
 * Test to see if MovementMessagHandler properly works.
 */
@GameTest("GameClient")
@ResetClientModelFacade(shouldClearQueue = true)
public class OtherPlayerMovedMessageHandlerTest
{
    /**
     * Test the type of Message that we expect
     */
    @Test
    public void typeWeHandle()
    {
        OtherPlayerMovedMessageHandler h = new OtherPlayerMovedMessageHandler();
        assertEquals(OtherPlayerMovedMessage.class, h.getMessageTypeWeHandle());
    }

    /**
     * Tests to see if the command is built correctly, and added to the Facade.
     *
     * @throws InterruptedException shouldn't
     */
    @Test
    public void engineNotified() throws InterruptedException
    {
        MapManager.getSingleton().changeToNewFile("simple.tmx", "TestEngineNotified in " +
                "Other PlayerMovedMessageHandler");
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MATT);
        ClientModelTestUtilities.setUpOtherPlayerForTest(PlayersForTest.MERLIN);

        Position p = new Position(1, 1);
        OtherPlayerMovedMessage msg = new OtherPlayerMovedMessage(
                PlayersForTest.MATT.getPlayerID(), false, p);
        OtherPlayerMovedMessageHandler handler = new OtherPlayerMovedMessageHandler();
        handler.process(msg);
        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());

        CommandClientMovePlayer cmd = (CommandClientMovePlayer) ClientModelFacade.getSingleton().getNextCommand();
        assertEquals(p, cmd.getPosition());
        assertFalse(cmd.isTeleporting());
        assertEquals(PlayersForTest.MATT.getPlayerID(), cmd.getPlayerID());
    }

}

package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.PlayerMovedMessage;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.*;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author merlin
 */
@GameTest("GameServer")
@ResetModelFacade
@ResetPlayerManager
public class MovementMessageHandlerTest
{
    /**
     * Tests that getTypeWeHandle method returns correct type.
     */
    @Test
    public void testTypeWeHandle()
    {
        MovementMessageHandler h = new MovementMessageHandler();
        assertEquals(PlayerMovedMessage.class, h.getMessageTypeWeHandle());
    }

    /**
     * Start with a player in a position, send that player through a movement
     * message and ensure that the player has the new position
     *
     * @throws ModelFacadeException Shouldn't
     */
    @Test
    public void updatesAPlayerPosition() throws ModelFacadeException
    {
        int playerID = 1;
        Position startPosition = new Position(0, 0);
        Position newPosition = new Position(1337, 1337);

        PlayerManager.getSingleton().addPlayer(1);
        Player p = PlayerManager.getSingleton().getPlayerFromID(1);
        p.setPosition(startPosition);

        assertEquals(startPosition, p.getPosition());

        PlayerMovedMessage msg = new PlayerMovedMessage(playerID, false, newPosition);
        MovementMessageHandler handler = new MovementMessageHandler();

        handler.process(msg);

        ModelFacadeTestHelper.waitForFacadeToProcess();

        assertEquals(newPosition, p.getPosition());
    }
}

package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.PlayerMovedMessage;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.OptionsManager;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author merlin
 */
public class MovementMessageHandlerTest extends ServerSideTest
{

    /**
     * Reset the PlayerManager
     */
    @Before
    public void reset()
    {
        PlayerManager.resetSingleton();
        ModelFacade.resetSingleton();
        OptionsManager.resetSingleton();
        OptionsManager.getSingleton().setTestMode(true);
    }

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
     * @throws InterruptedException Shouldn't
     */
    @Test
    public void updatesAPlayerPosition() throws InterruptedException
    {
        int playerID = 1;
        Position startPosition = new Position(0, 0);
        Position newPosition = new Position(1337, 1337);

        PlayerManager.getSingleton().addPlayer(1);
        Player p = PlayerManager.getSingleton().getPlayerFromID(1);
        p.setPlayerPosition(startPosition);

        assertEquals(startPosition, p.getPlayerPosition());

        PlayerMovedMessage msg = new PlayerMovedMessage(playerID, newPosition);
        MovementMessageHandler handler = new MovementMessageHandler();

        handler.process(msg);
        int count = 0;
        while (count < 10 && ModelFacade.getSingleton().hasCommandsPending())
        {
            Thread.sleep(100);
            count++;
        }
        assertTrue("ModelFacade didn't process our command", count < 10);
        assertEquals(newPosition, p.getPlayerPosition());
    }
}

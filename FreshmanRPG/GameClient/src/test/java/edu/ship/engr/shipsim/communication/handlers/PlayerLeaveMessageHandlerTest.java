package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.PlayerLeaveMessage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandRemovePlayer;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Make sure the message is handled properly
 *
 * @author Merlin
 */
@GameTest("GameClient")
@ResetClientModelFacade
public class PlayerLeaveMessageHandlerTest
{
    /**
     * Test the type of Message that we expect
     */
    @Test
    public void typeWeHandle()
    {
        PlayerLeaveMessageHandler h = new PlayerLeaveMessageHandler();
        assertEquals(PlayerLeaveMessage.class, h.getMessageTypeWeHandle());
    }

    /**
     * We should add the player to the player manager
     *
     * @throws InterruptedException shouldn't
     */
    @Test
    public void test() throws InterruptedException
    {
        PlayerLeaveMessage msg = new PlayerLeaveMessage(1, false);
        PlayerLeaveMessageHandler handler = new PlayerLeaveMessageHandler();
        handler.process(msg);
        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
        CommandRemovePlayer cmd = (CommandRemovePlayer) ClientModelFacade.getSingleton().getNextCommand();
        assertEquals(1, cmd.getPlayerID());
    }

}

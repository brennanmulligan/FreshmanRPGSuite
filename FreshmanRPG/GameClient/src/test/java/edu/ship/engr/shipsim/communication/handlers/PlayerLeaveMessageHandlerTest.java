package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.PlayerLeaveMessage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandRemovePlayer;
import edu.ship.engr.shipsim.model.OptionsManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Make sure the message is handled properly
 *
 * @author Merlin
 */
public class PlayerLeaveMessageHandlerTest
{
    @BeforeClass
    public static void hardReset()
    {
        OptionsManager.getSingleton().setTestMode(true);
    }

    /**
     * Reset the ModelFacade
     */
    @Before
    public void reset()
    {
        ClientModelFacade.resetSingleton();
        ClientModelFacade.getSingleton(true, false);
    }

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
        PlayerLeaveMessage msg = new PlayerLeaveMessage(1);
        PlayerLeaveMessageHandler handler = new PlayerLeaveMessageHandler();
        handler.process(msg);
        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
        CommandRemovePlayer cmd = (CommandRemovePlayer) ClientModelFacade.getSingleton().getNextCommand();
        assertEquals(1, cmd.getPlayerID());
    }

}

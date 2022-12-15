package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.LoginFailedMessage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.Command;
import edu.ship.engr.shipsim.model.CommandLoginFailed;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Matt and Andy
 */
@GameTest("GameClient")
@ResetClientModelFacade(shouldClearQueue = true)
public class LoginFailedMessageHandlerTest
{
    /**
     * We should add a command to the ModelFacade command queue
     *
     * @throws InterruptedException shouldn't
     */
    @Test
    public void test() throws InterruptedException
    {
        LoginFailedMessageHandler handler = new LoginFailedMessageHandler();
        LoginFailedMessage msg = new LoginFailedMessage();
        handler.process(msg);
        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
        Command cmd = ClientModelFacade.getSingleton().getNextCommand();
        assertEquals(cmd.getClass(), CommandLoginFailed.class);
    }

    /**
     * Test the type of Message that we expect
     */
    @Test
    public void typeWeHandle()
    {
        LoginFailedMessageHandler h = new LoginFailedMessageHandler();
        assertEquals(LoginFailedMessage.class, h.getMessageTypeWeHandle());
    }

}

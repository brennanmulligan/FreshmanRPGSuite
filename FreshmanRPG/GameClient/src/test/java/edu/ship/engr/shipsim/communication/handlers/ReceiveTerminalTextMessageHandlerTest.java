package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.ReceiveTerminalTextMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandReceiveTerminalResponse;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;


/**
 * @author Denny Fleagle
 * @author Ben Lehman
 */
@GameTest("GameClient")
@ResetClientModelFacade
public class ReceiveTerminalTextMessageHandlerTest
{
    /**
     * test the handler process method
     *
     * @throws InterruptedException interrupted exception
     */
    @Test
    public void testHandlerProcess() throws InterruptedException
    {
        String testString = "Test : String";
        ReceiveTerminalTextMessage msg = new ReceiveTerminalTextMessage(PlayersForTest.MERLIN.getPlayerID(), false, testString);
        ReceiveTerminalTextMessageHandler handler = new ReceiveTerminalTextMessageHandler();
        handler.process(msg);
        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());

        CommandReceiveTerminalResponse cmd = (CommandReceiveTerminalResponse) ClientModelFacade.getSingleton().getNextCommand();

        assertEquals(PlayersForTest.MERLIN.getPlayerID(), cmd.getPlayerID());
        assertEquals(testString, cmd.getTerminalResult());
    }

    /**
     * Test the type of Message that we expect
     */
    @Test
    public void typeWeHandle()
    {
        ReceiveTerminalTextMessageHandler h = new ReceiveTerminalTextMessageHandler();
        assertSame(ReceiveTerminalTextMessage.class, h.getMessageTypeWeHandle());
    }
}

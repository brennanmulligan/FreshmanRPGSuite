package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.TerminalTextExitMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetClientPlayerManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test that the client side Handler for a TerminalTextExitMessage can successfully
 * process the message from the server side packer and queue the respective command for
 * removing a player from the ClientSide sources.
 * <p>
 * Authors: John G. , Ian L.
 */
@GameTest("GameClient")
@ResetClientModelFacade
@ResetClientPlayerManager
public class TerminalTextExitHandlerTest
{
    /**
     * Test the handler processes the message fir a TerminalTextExitMessage from the Server Packer.
     */
    @Test
    public void testHandlerProcess()
    {
        Message message = new TerminalTextExitMessage(PlayersForTest.MERLIN.getPlayerID(), false);
        TerminalTextExitHandler handler = new TerminalTextExitHandler();
        handler.process(message);
        assertEquals(1, ClientModelFacade.getSingleton().queueSize());
    }

    /**
     * Tests that getTypeWeHandle method returns correct type.
     */
    @Test
    public void testTypeWeHandle()
    {
        TerminalTextExitHandler h = new TerminalTextExitHandler();
        assertEquals(TerminalTextExitMessage.class, h.getMessageTypeWeHandle());
    }
}





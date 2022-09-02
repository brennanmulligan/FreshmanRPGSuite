package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.TerminalTextExitMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.ClientPlayerManager;
import edu.ship.engr.shipsim.model.OptionsManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test that the client side Handler for a TerminalTextExitMessage can successfully
 * process the message from the server side packer and queue the respective command for
 * removing a player from the ClientSide sources.
 * <p>
 * Authors: John G. , Ian L.
 */
public class TerminalTextExitHandlerTest
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
        ClientPlayerManager.resetSingleton();
        ClientModelFacade.resetSingleton();
    }

    /**
     * Test the handler processes the message fir a TerminalTextExitMessage from the Server Packer.
     */
    @Test
    public void testHandlerProcess()
    {
        reset();

        Message message = new TerminalTextExitMessage(PlayersForTest.MERLIN.getPlayerID());
        TerminalTextExitHandler handler = new TerminalTextExitHandler();
        handler.process(message);
        assertEquals(1, ClientModelFacade.getSingleton().queueSize());

        reset();
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





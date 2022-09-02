package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.ReceiveTerminalTextMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandRecieveTerminalResponse;
import edu.ship.engr.shipsim.model.OptionsManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author Denny Fleagle
 * @author Ben Lehman
 */
public class ReceiveTerminalTextMessageHandlerTest
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
     * test the handler process method
     *
     * @throws InterruptedException interrupted exception
     */
    @Test
    public void testHandlerProcess() throws InterruptedException
    {
        String testString = "Test : String";
        ReceiveTerminalTextMessage msg = new ReceiveTerminalTextMessage(PlayersForTest.MERLIN.getPlayerID(), testString);
        ReceiveTerminalTextMessageHandler handler = new ReceiveTerminalTextMessageHandler();
        handler.process(msg);
        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());

        CommandRecieveTerminalResponse cmd = (CommandRecieveTerminalResponse) ClientModelFacade.getSingleton().getNextCommand();

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
        assertEquals(ReceiveTerminalTextMessage.class, h.getMessageTypeWeHandle());
    }
}

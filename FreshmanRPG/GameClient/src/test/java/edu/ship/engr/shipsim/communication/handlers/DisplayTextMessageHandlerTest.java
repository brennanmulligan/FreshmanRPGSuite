package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.DisplayTextMessage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandDisplayText;
import edu.ship.engr.shipsim.model.OptionsManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Andy Kim
 */
public class DisplayTextMessageHandlerTest
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
    public void setup()
    {
        ClientModelFacade.resetSingleton();
        ClientModelFacade.getSingleton(true, true);
    }

    /**
     * Test the type of Message that we expect
     */
    @Test
    public void typeWeHandle()
    {
        DisplayTextMessageHandler display = new DisplayTextMessageHandler();
        assertEquals(DisplayTextMessage.class, display.getMessageTypeWeHandle());
    }

    /**
     * We add command to the ModelFacade command queue
     * and check they have right information in the command queue.
     *
     * @throws InterruptedException shouldn't
     */
    @Test
    public void test() throws InterruptedException
    {
        DisplayTextMessageHandler display = new DisplayTextMessageHandler();
        DisplayTextMessage text = new DisplayTextMessage(1, "text");
        display.process(text);
        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());

        CommandDisplayText cmd = (CommandDisplayText) ClientModelFacade.getSingleton().getNextCommand();
        assertEquals("text", cmd.getText());
        ClientModelFacade.getSingleton().emptyQueue();

    }

}

package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.DisplayTextMessage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandDisplayText;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Andy Kim
 */
@GameTest("GameClient")
@ResetClientModelFacade(shouldClearQueue = true)
public class DisplayTextMessageHandlerTest
{
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
        DisplayTextMessage text = new DisplayTextMessage(1, false, "text");
        display.process(text);
        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());

        CommandDisplayText cmd = (CommandDisplayText) ClientModelFacade.getSingleton().getNextCommand();
        assertEquals("text", cmd.getText());

    }

}

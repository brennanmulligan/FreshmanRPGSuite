package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.BuffMessage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandDisplayText;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Emmanuel Douge, Elisabeth Ostrow
 */
@GameTest("GameClient")
@ResetClientModelFacade
public class BuffMessageHandlerTest
{
    /**
     * Test the type of Message that we expect
     */
    @Test
    public void typeWeHandle()
    {
        BuffMessageHandler display = new BuffMessageHandler();
        assertEquals(BuffMessage.class, display.getMessageTypeWeHandle());

    }

    /**
     * We add command to the ModelFacade command queue and check they have right
     * information in the command queue.
     *
     * @throws InterruptedException shouldn't
     */
    @Test
    public void test() throws InterruptedException
    {
        ClientModelFacade modelFacade = ClientModelFacade.getSingleton();
        BuffMessageHandler display = new BuffMessageHandler();
        BuffMessage text = new BuffMessage(1, false, 10);

        display.process(text);

        assertEquals(1, modelFacade.getCommandQueueLength());

        CommandDisplayText cmd = (CommandDisplayText) modelFacade.getNextCommand();

        assertEquals("You have received 10 Rec Center Bonus Points.", cmd.getText());
    }

}
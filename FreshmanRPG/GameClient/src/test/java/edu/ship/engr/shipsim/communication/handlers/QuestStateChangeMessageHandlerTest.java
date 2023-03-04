package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.QuestStateChangeMessage;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandQuestStateChange;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ryan
 */
@GameTest("GameClient")
@ResetClientModelFacade
public class QuestStateChangeMessageHandlerTest
{
    /**
     * Test the type of Message that we expect
     */
    @Test
    public void typeWeHandle()
    {
        QuestStateChangeMessageHandler h = new QuestStateChangeMessageHandler();
        assertEquals(QuestStateChangeMessage.class, h.getMessageTypeWeHandle());
    }

    /**
     * Test that the handler messages handles the messages and creates
     * a command
     *
     * @throws InterruptedException shouldn't
     */
    @Test
    public void testMessageHandling() throws InterruptedException
    {
        QuestStateChangeMessageHandler h = new QuestStateChangeMessageHandler();
        QuestStateChangeMessage msg = new QuestStateChangeMessage(1, false, 2, "quest 1 title", "Quest 1", QuestStateEnum.AVAILABLE);
        h.process(msg);
        assertTrue(ClientModelFacade.getSingleton().hasCommandsPending());
        CommandQuestStateChange cmd = (CommandQuestStateChange) ClientModelFacade.getSingleton().getNextCommand();
        assertEquals(2, cmd.getQuestID());
        assertEquals("quest 1 title", cmd.getQuestTitle());
        assertEquals("Quest 1", cmd.getQuestDescription());
        assertEquals(QuestStateEnum.AVAILABLE, cmd.getQuestState());
    }

}

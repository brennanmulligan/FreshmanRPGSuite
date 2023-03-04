package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.DoubloonsChangedMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandDoubloonsChanged;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * @author Matthew Croft
 * @author Emily Maust
 */
@GameTest("GameClient")
@ResetClientModelFacade(shouldClearQueue = true)
public class DoubloonsChangedMessageHandlerTest
{
    /**
     * Tests that getTypeWeHandle method returns correct type.
     */
    @Test
    public void testTypeWeHandle()
    {
        DoubloonsChangedMessageHandler h = new DoubloonsChangedMessageHandler();
        assertSame(DoubloonsChangedMessage.class, h.getMessageTypeWeHandle());
    }

    /**
     * Testing to see if a command is queued after receiving a message
     *
     * @throws InterruptedException shouldn't
     */
    @Test
    public void handleDoubloonsChangedMessage() throws InterruptedException
    {
        int oldScore = PlayersForTest.JOHN.getDoubloons();

        DoubloonsChangedMessage msg = new DoubloonsChangedMessage(PlayersForTest.JOHN.getPlayerID(), false, oldScore + 5, PlayersForTest.JOHN.getBuffPool() - 5);
        DoubloonsChangedMessageHandler h = new DoubloonsChangedMessageHandler();
        h.process(msg);
        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
        CommandDoubloonsChanged cmd = (CommandDoubloonsChanged) ClientModelFacade.getSingleton().getNextCommand();
        assertEquals(oldScore + 5, cmd.getDoubloons());
    }
}

package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.CommunicationException;
import edu.ship.engr.shipsim.communication.ConnectionManager;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.StubMessage1;
import edu.ship.engr.shipsim.communication.messages.StubMessage2;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for the generic MessageProcessor
 *
 * @author merlin
 */
@GameTest("GameShared")
public class MessageHandlerSetTest
{

    /**
     * Make sure it registers all of the handlers in its package. If we can
     * process messages of the type each stub handler expects, then they got
     * registered
     *
     * @throws CommunicationException shouldn't
     */
    @Test
    public void detectsAndRegisters() throws CommunicationException
    {
        MessageHandlerSet proc = new MessageHandlerSet(null);

        proc.process(new StubMessage1(0));
        proc.process(new StubMessage2(0));

    }

    /**
     * If there isn't any handler for the type of message, an exception should
     * be thrown
     */
    @Test
    public void noSuchHandler()
    {
        assertThrows(CommunicationException.class, () ->
        {
            MessageHandlerSet proc = new MessageHandlerSet(null);
            Message msg = Mockito.mock(Message.class);

            proc.process(msg);
        });
    }

    /**
     * Make sure that when we tell the set about its accumulator, it tells each
     * of the handlers it contains
     */
    @Test
    public void accumulatorIsBroadcast()
    {
        ConnectionManager cm = Mockito.mock(ConnectionManager.class);
        MessageHandlerSet proc = new MessageHandlerSet(null);
        proc.setConnectionManager(cm);

        Collection<MessageHandler> handlers = proc.getHandlers();
        for (MessageHandler h : handlers)
        {
            assertEquals(cm, h.getConnectionManager());
        }
    }
}

package edu.ship.engr.shipsim.communication;

import edu.ship.engr.shipsim.communication.handlers.MessageHandlerSet;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.StubMessage1;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Test the parts of the incoming connection that can be tested without a true
 * socket connection
 *
 * @author merlin
 */
@GameTest("GameShared")
public class ConnectionIncomingTest
{


    /**
     * An incoming message should be routed by the MessageProcessor to the
     * MessageHandler register for that type of message
     *
     * @throws IOException shouldn't
     */
    @Test
    public void incomingMsgGetsProcessed() throws IOException
    {
        Message msg = new StubMessage1(0);

        MessageHandlerSet messageHandlerSet = new MessageHandlerSet(null);
        ConnectionIncoming connection = new ConnectionIncoming(null, messageHandlerSet);
        connection.processRequest(msg);

    }
}

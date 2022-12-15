package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.StubMessage1;

/**
 * empty handler for testing purposes
 *
 * @author merlin
 */
public class StubMessageHandler1 extends MessageHandler
{

    /**
     * We don't have to do anything
     *
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return StubMessage1.class;
    }

}

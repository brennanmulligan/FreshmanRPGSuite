package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.NoMoreBuffMessage;

/**
 * Message handler for if a Buff has run out
 *
 * @author Aaron Gerber
 * @author Stephen Clabaugh
 */
public class NoMoreBuffMessageHandler extends MessageHandler
{

    /**
     * handle the message and send the appropriate information.
     */
    @Override
    public void process(Message msg)
    {
    }

    /**
     * give information on the type of message that this handler interacts with.
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return NoMoreBuffMessage.class;
    }

}

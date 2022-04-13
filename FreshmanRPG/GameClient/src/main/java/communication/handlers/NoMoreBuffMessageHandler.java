package communication.handlers;

import communication.messages.Message;
import communication.messages.NoMoreBuffMessage;

/**
 * Message handler for if a Buff has run out
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

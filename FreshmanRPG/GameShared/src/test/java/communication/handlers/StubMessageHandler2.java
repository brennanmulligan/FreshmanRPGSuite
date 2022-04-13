package communication.handlers;

import communication.messages.Message;
import communication.messages.StubMessage2;

/**
 * empty handler for testing purposes
 *
 * @author merlin
 *
 */
public class StubMessageHandler2 extends MessageHandler
{

	/**
	 * We don't have to do anything
	 *
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return StubMessage2.class;
	}

}

package communication.handlers;

import communication.messages.Message;
import communication.messages.StubMessage1;

/**
 * empty handler for testing purposes
 *
 * @author merlin
 *
 */
public class StubMessageHandler1 extends MessageHandler
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
		return StubMessage1.class;
	}

}

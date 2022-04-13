package communication.handlers;

import communication.messages.HighScoreResponseMessage;
import communication.messages.Message;
import model.ClientModelFacade;
import model.CommandHighScoreResponse;

/**
 * Receives a message High Score Response
 * @author nk3668
 *
 */
public class HighScoreResponseMessageHandler extends MessageHandler
{

	/**
	 * Returns what type of object this class handles
	 * @return class type
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return HighScoreResponseMessage.class;
	}

	/**
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(HighScoreResponseMessage.class))
		{
			HighScoreResponseMessage message = (HighScoreResponseMessage) msg;
			CommandHighScoreResponse cmd = new CommandHighScoreResponse(message.getScoreReports());
			ClientModelFacade.getSingleton().queueCommand(cmd);
		}
	}

}

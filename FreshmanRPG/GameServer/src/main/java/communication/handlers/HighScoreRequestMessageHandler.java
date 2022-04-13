package communication.handlers;

import communication.messages.HighScoreRequestMessage;
import communication.messages.HighScoreResponseMessage;
import communication.messages.Message;
import datasource.DatabaseException;
import model.PlayerManager;

/**
 * This should just echo back a response that contains the top ten high scores
 *
 * @author Merlin
 *
 */
public class HighScoreRequestMessageHandler extends MessageHandler
{

	/**
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		try
		{
			this.getStateAccumulator()
					.queueMessage(new HighScoreResponseMessage(PlayerManager.getSingleton().getTopTenPlayers()));
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return HighScoreRequestMessage.class;
	}

}

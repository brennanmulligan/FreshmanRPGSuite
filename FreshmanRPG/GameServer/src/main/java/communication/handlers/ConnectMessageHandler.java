package communication.handlers;

import communication.messages.ConnectMessage;
import communication.messages.Message;
import model.CommandAddPlayer;
import model.ModelFacade;

/**
 * Handles a message that the player is connecting to this area server
 *
 * @author merlin
 *
 */
public class ConnectMessageHandler extends MessageHandler
{

	/**
	 * Add this player to the player list
	 *
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(ConnectMessage.class))
		{
			ConnectMessage cMsg = (ConnectMessage) msg;
			if (getConnectionManager() != null)
			{
				getConnectionManager().setPlayerID(cMsg.getPlayerID());
			}
			CommandAddPlayer cmd = new CommandAddPlayer(cMsg.getPlayerID(), cMsg.getPin());

			ModelFacade.getSingleton().queueCommand(cmd);

		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return ConnectMessage.class;
	}

}

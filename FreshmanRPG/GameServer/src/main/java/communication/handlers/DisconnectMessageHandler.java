package communication.handlers;

import communication.messages.DisconnectMessage;
import communication.messages.Message;
import model.CommandRemovePlayer;
import model.ModelFacade;

/**
 * Handles a message that the player has disconnected from this area server
 *
 * @author nhydock
 *
 */
public class DisconnectMessageHandler extends MessageHandler
{
	/**
	 * remove this player from the player list
	 *
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(DisconnectMessage.class))
		{
			DisconnectMessage cMsg = (DisconnectMessage) msg;

			CommandRemovePlayer cmd = new CommandRemovePlayer(cMsg.getPlayerID());

			ModelFacade.getSingleton().queueCommand(cmd);
		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return DisconnectMessage.class;
	}
}

package communication.handlers;

import communication.ConnectionManagerList;
import communication.messages.ConnectMessage;
import communication.messages.Message;
import datasource.LoggerManager;
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
			LoggerManager.getSingleton().getLogger().info("Player connected: " + cMsg.getPlayerID());

			if (getConnectionManager() != null)
			{
				getConnectionManager().setPlayerID(cMsg.getPlayerID());
				ConnectionManagerList.getSingleton().add(getConnectionManager());
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

package communication.handlers;

import communication.messages.Message;
import communication.messages.OtherPlayerMovedMessage;
import model.ClientModelFacade;
import model.CommandClientMovePlayer;

/**
 * Should process an incoming MovementMessage that is reporting that someone
 * else moved
 *
 * @author merlin
 *
 */
public class OtherPlayerMovedMessageHandler extends MessageHandler
{
	/**
	 *
	 *
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(OtherPlayerMovedMessage.class))
		{

			OtherPlayerMovedMessage movementMessage = (OtherPlayerMovedMessage) msg;
			CommandClientMovePlayer cmd = new CommandClientMovePlayer(movementMessage.getPlayerID(),
					movementMessage.getPosition());
			ClientModelFacade.getSingleton().queueCommand(cmd);
		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return OtherPlayerMovedMessage.class;
	}

}

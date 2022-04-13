package communication.handlers;

import communication.messages.Message;
import communication.messages.PlayerMovedMessage;
import model.CommandMovePlayer;
import model.ModelFacade;

/**
 * Handles a report of a player moving
 *
 * @author merlin
 *
 */
public class MovementMessageHandler extends MessageHandler
{

	/**
	 * When one player moves, we should update the state of the engine
	 *
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(PlayerMovedMessage.class))
		{
			PlayerMovedMessage mMsg = (PlayerMovedMessage) msg;
			CommandMovePlayer cmd = new CommandMovePlayer(mMsg.getPlayerID(), mMsg.getPosition());
			ModelFacade.getSingleton().queueCommand(cmd);
		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return PlayerMovedMessage.class;
	}
}

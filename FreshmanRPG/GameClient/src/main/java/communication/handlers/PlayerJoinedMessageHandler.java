package communication.handlers;

import communication.messages.Message;
import communication.messages.PlayerJoinedMessage;
import model.ClientModelFacade;
import model.CommandInitializePlayer;

/**
 * Should process an incoming PlayerJoinedMessage that is reporting that someone
 * joined our area server
 *
 * @author merlin
 *
 */
public class PlayerJoinedMessageHandler extends MessageHandler
{

	/**
	 * A player has joined our area server, so notify the PlayerManager of his
	 * presence
	 *
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		PlayerJoinedMessage playerJoinedMessage = (PlayerJoinedMessage) msg;
		CommandInitializePlayer cmd = new CommandInitializePlayer(
				playerJoinedMessage.getPlayerID(), playerJoinedMessage.getPlayerName(),
				playerJoinedMessage.getAppearanceType(),
				playerJoinedMessage.getPosition(),
				playerJoinedMessage.getCrew(),
				playerJoinedMessage.getMajor(),
				playerJoinedMessage.getSection()
		);
		ClientModelFacade.getSingleton().queueCommand(cmd);
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return PlayerJoinedMessage.class;
	}

}

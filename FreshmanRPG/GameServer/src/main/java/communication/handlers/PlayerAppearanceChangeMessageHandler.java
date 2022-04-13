package communication.handlers;

import communication.messages.Message;
import communication.messages.PlayerAppearanceChangeMessage;
import model.CommandChangePlayerAppearance;
import model.ModelFacade;

/**
 * Process a player Appearance Changed Message so
 * the player can be updated
 *
 */
public class PlayerAppearanceChangeMessageHandler extends MessageHandler
{

	/**
	 * PLayers appearance has changed call command to change appearance
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		PlayerAppearanceChangeMessage playerAppearanceChnageMessage = (PlayerAppearanceChangeMessage) msg;
		CommandChangePlayerAppearance cmd = new CommandChangePlayerAppearance(
				playerAppearanceChnageMessage.getPlayerID(),
				playerAppearanceChnageMessage.getAppearanceType());
		ModelFacade.getSingleton().queueCommand(cmd);


	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{

		return PlayerAppearanceChangeMessage.class;
	}

}

package communication.handlers;

import communication.messages.Message;
import communication.messages.PlayerAppearanceChangeMessage;
import model.ClientModelFacade;
import model.CommandChangePlayerAppearance;

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
		PlayerAppearanceChangeMessage message = (PlayerAppearanceChangeMessage) msg;
		CommandChangePlayerAppearance cmd = new CommandChangePlayerAppearance(
				message.getPlayerID(),
				message.getVanities()
		);
		ClientModelFacade.getSingleton().queueCommand(cmd);
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

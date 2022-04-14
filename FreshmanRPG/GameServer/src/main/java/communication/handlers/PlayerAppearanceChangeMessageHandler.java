package communication.handlers;

import communication.messages.Message;
import communication.messages.PlayerAppearanceChangeMessage;
import communication.messages.PlayerChangeAppearanceMessage;
import dataDTO.VanityDTO;
import model.CommandChangePlayerAppearance;
import model.ModelFacade;

import java.util.ArrayList;

/**
 * Process a player change appearance Message so
 * the player can be updated
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
		PlayerChangeAppearanceMessage playerChangeAppearanceMessage = (PlayerChangeAppearanceMessage) msg;
		CommandChangePlayerAppearance cmd = new CommandChangePlayerAppearance(
				playerChangeAppearanceMessage.getPlayerID(),
				playerChangeAppearanceMessage.getNewWearing());
		ModelFacade.getSingleton().queueCommand(cmd);
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return PlayerChangeAppearanceMessage.class;
	}

}

package communication.handlers;

import communication.messages.Message;
import communication.messages.TeleportationInitiationMessage;
import model.CommandMovePlayerSilentlyAndPersist;
import model.ModelFacade;

/**
 * Process GetServerInfoMessages by gathering the requested data and sending it
 * back to the requestor
 *
 * @author Merlin
 *
 */
public class TeleportationInitiationHandler extends MessageHandler
{

	/**
	 * This handler should retrieve the information requested and send it back
	 *
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		TeleportationInitiationMessage currentMsg = (TeleportationInitiationMessage) msg;

		CommandMovePlayerSilentlyAndPersist command = new CommandMovePlayerSilentlyAndPersist(
				currentMsg.getPlayerId(),
				currentMsg.getMapName(),
				currentMsg.getPosition()
		);

		ModelFacade.getSingleton().queueCommand(command);
	}

	/**
	 *
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return TeleportationInitiationMessage.class;
	}

}

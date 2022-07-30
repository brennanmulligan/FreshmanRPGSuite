package communication.handlers;

import communication.messages.Message;
import communication.messages.TeleportationInitiationMessage;
import datasource.LoggerManager;
import model.CommandMovePlayerToAnotherMapAndPersist;
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
		LoggerManager.getSingleton().getLogger().info("Received Teleportation " +
				"initiation from player " + currentMsg.getPlayerId());
		CommandMovePlayerToAnotherMapAndPersist
				command = new CommandMovePlayerToAnotherMapAndPersist(
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

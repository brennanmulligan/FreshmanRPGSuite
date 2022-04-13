package communication.handlers;

import communication.messages.KeyInputMessage;
import communication.messages.Message;
import model.CommandKeyInputMessageReceived;
import model.ModelFacade;

/**
 * Handler that receives key input messages to unpack.
 *
 * @author Ian Keefer & TJ Renninger
 *
 */
public class KeyInputMessageHandler extends MessageHandler
{

	/**
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(KeyInputMessage.class))
		{
			KeyInputMessage kiMsg = (KeyInputMessage) msg;
			int playerId = getStateAccumulator().getPlayerID();
			CommandKeyInputMessageReceived command = new CommandKeyInputMessageReceived(kiMsg.getInput(), playerId);
			ModelFacade.getSingleton().queueCommand(command);
		}

	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return KeyInputMessage.class;
	}

}

package communication.handlers;

import communication.messages.ObjectiveStateChangeMessage;
import communication.messages.Message;
import model.ClientModelFacade;
import model.CommandObjectiveStateChange;

/**
 * Handles the Objective State Change Message
 * @author Ryan
 *
 */
public class ObjectiveStateChangeMessageHandler extends MessageHandler
{

	/**
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		ObjectiveStateChangeMessage ourMsg = (ObjectiveStateChangeMessage) msg;
		CommandObjectiveStateChange cmd = new CommandObjectiveStateChange(ourMsg);
		ClientModelFacade.getSingleton().queueCommand(cmd);
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return ObjectiveStateChangeMessage.class;
	}

}

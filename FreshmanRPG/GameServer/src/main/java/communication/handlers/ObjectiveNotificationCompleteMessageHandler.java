package communication.handlers;

import communication.messages.ObjectiveNotificationCompleteMessage;
import communication.messages.Message;
import model.CommandObjectiveNotificationComplete;
import model.ModelFacade;

/**
 * @author Ryan
 *
 */
public class ObjectiveNotificationCompleteMessageHandler extends MessageHandler
{

	/**
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(ObjectiveNotificationCompleteMessage.class))
		{
			ObjectiveNotificationCompleteMessage aMsg = (ObjectiveNotificationCompleteMessage) msg;
			CommandObjectiveNotificationComplete cmd = new CommandObjectiveNotificationComplete(aMsg.getPlayerID(),
					aMsg.getQuestID(), aMsg.getObjectiveID());
			ModelFacade.getSingleton().queueCommand(cmd);
		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return ObjectiveNotificationCompleteMessage.class;
	}

}

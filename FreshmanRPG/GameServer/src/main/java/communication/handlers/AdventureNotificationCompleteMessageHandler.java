package communication.handlers;

import communication.messages.AdventureNotificationCompleteMessage;
import communication.messages.Message;
import model.CommandAdventureNotificationComplete;
import model.ModelFacade;

/**
 * @author Ryan
 *
 */
public class AdventureNotificationCompleteMessageHandler extends MessageHandler
{

	/**
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(AdventureNotificationCompleteMessage.class))
		{
			AdventureNotificationCompleteMessage aMsg = (AdventureNotificationCompleteMessage) msg;
			CommandAdventureNotificationComplete cmd = new CommandAdventureNotificationComplete(aMsg.getPlayerID(),
					aMsg.getQuestID(), aMsg.getAdventureID());
			ModelFacade.getSingleton().queueCommand(cmd);
		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return AdventureNotificationCompleteMessage.class;
	}

}

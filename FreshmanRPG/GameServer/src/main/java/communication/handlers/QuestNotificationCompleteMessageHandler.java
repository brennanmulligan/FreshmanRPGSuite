package communication.handlers;

import communication.messages.Message;
import communication.messages.QuestNotificationCompleteMessage;
import model.CommandQuestNotificationComplete;
import model.ModelFacade;

/**
 * @author Merlin
 *
 */
public class QuestNotificationCompleteMessageHandler extends MessageHandler
{

	/**
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(QuestNotificationCompleteMessage.class))
		{
			QuestNotificationCompleteMessage aMsg = (QuestNotificationCompleteMessage) msg;
			CommandQuestNotificationComplete cmd = new CommandQuestNotificationComplete(aMsg.getPlayerID(),
					aMsg.getQuestID());
			ModelFacade.getSingleton().queueCommand(cmd);
		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return QuestNotificationCompleteMessage.class;
	}

}

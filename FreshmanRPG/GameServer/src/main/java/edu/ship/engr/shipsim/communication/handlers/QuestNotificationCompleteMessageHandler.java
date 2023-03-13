package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.QuestNotificationCompleteMessage;
import edu.ship.engr.shipsim.model.CommandQuestNotificationComplete;
import edu.ship.engr.shipsim.model.ModelFacade;

/**
 * @author Merlin
 */
public class QuestNotificationCompleteMessageHandler extends MessageHandler
{

    /**
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        if (msg.getClass().equals(QuestNotificationCompleteMessage.class))
        {
            QuestNotificationCompleteMessage aMsg = (QuestNotificationCompleteMessage) msg;
            CommandQuestNotificationComplete cmd = new CommandQuestNotificationComplete(aMsg.getRelevantPlayerID(),
                    aMsg.getQuestID());
            ModelFacade.getSingleton().queueCommand(cmd);
        }
    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return QuestNotificationCompleteMessage.class;
    }

}

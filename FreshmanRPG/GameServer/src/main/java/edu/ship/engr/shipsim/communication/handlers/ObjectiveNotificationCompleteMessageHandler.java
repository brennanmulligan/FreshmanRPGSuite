package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.ObjectiveNotificationCompleteMessage;
import edu.ship.engr.shipsim.model.CommandObjectiveNotificationComplete;
import edu.ship.engr.shipsim.model.ModelFacade;

/**
 * @author Ryan
 */
public class ObjectiveNotificationCompleteMessageHandler extends MessageHandler
{

    /**
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        if (msg.getClass().equals(ObjectiveNotificationCompleteMessage.class))
        {
            ObjectiveNotificationCompleteMessage aMsg = (ObjectiveNotificationCompleteMessage) msg;
            CommandObjectiveNotificationComplete cmd = new CommandObjectiveNotificationComplete(aMsg.getRelevantPlayerID(),
                    aMsg.getQuestID(), aMsg.getObjectiveID());
            ModelFacade.getSingleton().queueCommand(cmd);
        }
    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return ObjectiveNotificationCompleteMessage.class;
    }

}

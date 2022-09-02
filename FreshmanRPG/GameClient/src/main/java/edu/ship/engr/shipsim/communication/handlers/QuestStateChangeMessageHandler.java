package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.QuestStateChangeMessage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandQuestStateChange;

/**
 * Handles the QuestStateChange Message
 *
 * @author Ryan
 */
public class QuestStateChangeMessageHandler extends MessageHandler
{
    /**
     * Queues the command in Model Facade
     *
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        QuestStateChangeMessage ourMsg = (QuestStateChangeMessage) msg;
        CommandQuestStateChange cmd = new CommandQuestStateChange(ourMsg);
        ClientModelFacade.getSingleton().queueCommand(cmd);
    }

    /**
     * @return the message we expect
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return QuestStateChangeMessage.class;
    }
}

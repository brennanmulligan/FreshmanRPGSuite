package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.ObjectiveStateChangeMessage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandObjectiveStateChange;

/**
 * Handles the Objective State Change Message
 *
 * @author Ryan
 */
public class ObjectiveStateChangeMessageHandler extends MessageHandler
{

    /**
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        ObjectiveStateChangeMessage ourMsg = (ObjectiveStateChangeMessage) msg;
        CommandObjectiveStateChange cmd = new CommandObjectiveStateChange(ourMsg);
        ClientModelFacade.getSingleton().queueCommand(cmd);
    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return ObjectiveStateChangeMessage.class;
    }

}

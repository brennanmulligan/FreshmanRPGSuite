package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.BuffMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandDisplayText;

/**
 * Handles message from server for buff messages
 *
 * @author Andy, Emmanuel
 */
public class BuffMessageHandler extends MessageHandler
{
    /**
     * queues command for buff
     */
    @Override
    public void process(Message msg)
    {
        BuffMessage buffMsg = (BuffMessage) msg;
        CommandDisplayText cmd = new CommandDisplayText("You have received " + buffMsg.getExperiencePointPool() + " Rec Center Bonus Points.");
        ClientModelFacade.getSingleton().queueCommand(cmd);
    }

    /**
     * returns the message type that we handle
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return BuffMessage.class;
    }

}

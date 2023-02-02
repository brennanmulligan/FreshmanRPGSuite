package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.ExperienceChangedMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandOverwriteExperience;

/**
 * @author Ryan
 */
public class ExperienceChangedMessageHandler extends MessageHandler
{

    /**
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        if (msg.getClass().equals(ExperienceChangedMessage.class))
        {
            ExperienceChangedMessage experienceChangedMessage = (ExperienceChangedMessage) msg;
            CommandOverwriteExperience cmd = new CommandOverwriteExperience(experienceChangedMessage.getExperiencePoints(),
                    experienceChangedMessage.getLevel());
            ClientModelFacade.getSingleton().queueCommand(cmd);
        }

    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return ExperienceChangedMessage.class;
    }

}

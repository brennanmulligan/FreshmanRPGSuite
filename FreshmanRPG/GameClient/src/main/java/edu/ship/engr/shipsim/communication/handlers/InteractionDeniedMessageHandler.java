package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.InteractionDeniedMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandInteractionDenied;

/**
 * Message handler for when Interaction is Denied
 */
public class InteractionDeniedMessageHandler extends MessageHandler
{

    /**
     * (non-Javadoc)
     *
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        ClientModelFacade.getSingleton().queueCommand(new CommandInteractionDenied());
    }

    /**
     * (non-Javadoc)
     *
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return InteractionDeniedMessage.class;
    }

}

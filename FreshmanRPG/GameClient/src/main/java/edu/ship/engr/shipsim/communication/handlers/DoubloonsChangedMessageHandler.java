package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.DoubloonsChangedMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandDoubloonsChanged;

/**
 * @author Matthew Croft
 * @author Evan Stevenson
 */
public class DoubloonsChangedMessageHandler extends MessageHandler
{

    /**
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        if (msg.getClass().equals(DoubloonsChangedMessage.class))
        {
            DoubloonsChangedMessage doubloonsChangedMessage = (DoubloonsChangedMessage) msg;
            CommandDoubloonsChanged cmd = new CommandDoubloonsChanged(doubloonsChangedMessage.getDoubloons(), doubloonsChangedMessage.getBuffPool());
            ClientModelFacade.getSingleton().queueCommand(cmd);
        }

    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return DoubloonsChangedMessage.class;
    }

}

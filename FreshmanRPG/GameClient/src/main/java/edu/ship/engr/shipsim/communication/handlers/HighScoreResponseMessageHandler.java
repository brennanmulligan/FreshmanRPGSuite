package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.HighScoreResponseMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandHighScoreResponse;

/**
 * Receives a message High Score Response
 *
 * @author nk3668
 */
public class HighScoreResponseMessageHandler extends MessageHandler
{

    /**
     * Returns what type of object this class handles
     *
     * @return class type
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return HighScoreResponseMessage.class;
    }

    /**
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        if (msg.getClass().equals(HighScoreResponseMessage.class))
        {
            HighScoreResponseMessage message = (HighScoreResponseMessage) msg;
            CommandHighScoreResponse cmd = new CommandHighScoreResponse(message.getScoreReports());
            ClientModelFacade.getSingleton().queueCommand(cmd);
        }
    }

}

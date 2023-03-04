package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.HighScoreRequestMessage;
import edu.ship.engr.shipsim.communication.messages.HighScoreResponseMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.model.PlayerManager;

/**
 * This should just echo back a response that contains the top ten high scores
 *
 * @author Merlin
 */
public class HighScoreRequestMessageHandler extends MessageHandler
{

    /**
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        try
        {
            this.getStateAccumulator()
                    .queueMessage(new HighScoreResponseMessage(PlayerManager.getSingleton().getTopTenPlayers(), msg.isQuietMessage()));
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return HighScoreRequestMessage.class;
    }

}

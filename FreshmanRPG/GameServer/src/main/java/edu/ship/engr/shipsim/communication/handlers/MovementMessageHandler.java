package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PlayerMovedMessage;
import edu.ship.engr.shipsim.model.CommandMovePlayer;
import edu.ship.engr.shipsim.model.ModelFacade;

/**
 * Handles a report of a player moving
 *
 * @author merlin
 */
public class MovementMessageHandler extends MessageHandler
{

    /**
     * When one player moves, we should update the state of the engine
     *
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        if (msg.getClass().equals(PlayerMovedMessage.class))
        {
            PlayerMovedMessage mMsg = (PlayerMovedMessage) msg;
            CommandMovePlayer cmd = new CommandMovePlayer(mMsg.getRelevantPlayerID(), mMsg.getPosition());
            ModelFacade.getSingleton().queueCommand(cmd);
        }
    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return PlayerMovedMessage.class;
    }
}

package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.OtherPlayerMovedMessage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandClientMovePlayer;

/**
 * Should process an incoming MovementMessage that is reporting that someone
 * else moved
 *
 * @author merlin
 */
public class OtherPlayerMovedMessageHandler extends MessageHandler
{
    /**
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        if (msg.getClass().equals(OtherPlayerMovedMessage.class))
        {

            OtherPlayerMovedMessage movementMessage = (OtherPlayerMovedMessage) msg;
            CommandClientMovePlayer cmd = new CommandClientMovePlayer(movementMessage.getRelevantPlayerID(),
                    movementMessage.getPosition());
            ClientModelFacade.getSingleton().queueCommand(cmd);
        }
    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return OtherPlayerMovedMessage.class;
    }

}

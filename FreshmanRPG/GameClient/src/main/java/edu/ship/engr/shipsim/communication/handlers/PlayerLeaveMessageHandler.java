package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PlayerLeaveMessage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandRemovePlayer;

/**
 * Should process an incoming PlayerJoinedMessage that is reporting that someone
 * joined our area server
 *
 * @author merlin
 */
public class PlayerLeaveMessageHandler extends MessageHandler
{

    /**
     * A player has joined our area server, so notify the PlayerManager of his
     * presence
     *
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {


        if (msg.getClass().equals(PlayerLeaveMessage.class))
        {
            PlayerLeaveMessage realMsg = (PlayerLeaveMessage) msg;
            CommandRemovePlayer cmd = new CommandRemovePlayer(realMsg.getRelevantPlayerID());
            ClientModelFacade.getSingleton().queueCommand(cmd);
        }

    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return PlayerLeaveMessage.class;
    }

}

package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PlayerJoinedMessage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandInitializePlayer;

/**
 * Should process an incoming PlayerJoinedMessage that is reporting that someone
 * joined our area server
 *
 * @author merlin
 */
public class PlayerJoinedMessageHandler extends MessageHandler
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
        PlayerJoinedMessage playerJoinedMessage = (PlayerJoinedMessage) msg;
        CommandInitializePlayer cmd = new CommandInitializePlayer(
                playerJoinedMessage.getRelevantPlayerID(), playerJoinedMessage.getPlayerName(),
                playerJoinedMessage.getVanities(),
                playerJoinedMessage.getPosition(),
                playerJoinedMessage.getCrew(),
                playerJoinedMessage.getMajor(),
                playerJoinedMessage.getSection(),
                playerJoinedMessage.getAllOwnedItems()
        );
        ClientModelFacade.getSingleton().queueCommand(cmd);
    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return PlayerJoinedMessage.class;
    }

}

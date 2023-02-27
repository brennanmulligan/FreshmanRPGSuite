package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PlayerAppearanceChangeMessage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandChangePlayerAppearance;

/**
 * Process a player Appearance Changed Message so
 * the player can be updated
 */
public class PlayerAppearanceChangeMessageHandler extends MessageHandler
{

    /**
     * PLayers appearance has changed call command to change appearance
     *
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        PlayerAppearanceChangeMessage message = (PlayerAppearanceChangeMessage) msg;
        CommandChangePlayerAppearance cmd = new CommandChangePlayerAppearance(
                message.getRelevantPlayerID(),
                message.getVanities()
        );
        ClientModelFacade.getSingleton().queueCommand(cmd);
    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return PlayerAppearanceChangeMessage.class;
    }

}

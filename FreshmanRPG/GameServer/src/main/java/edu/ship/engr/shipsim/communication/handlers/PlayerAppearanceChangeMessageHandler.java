package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PlayerChangeAppearanceMessage;
import edu.ship.engr.shipsim.model.CommandChangePlayerAppearance;
import edu.ship.engr.shipsim.model.ModelFacade;

/**
 * Process a player change appearance Message so
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
        PlayerChangeAppearanceMessage playerChangeAppearanceMessage = (PlayerChangeAppearanceMessage) msg;
        CommandChangePlayerAppearance cmd = new CommandChangePlayerAppearance(
                playerChangeAppearanceMessage.getRelevantPlayerID(),
                playerChangeAppearanceMessage.getNewWearing());
        ModelFacade.getSingleton().queueCommand(cmd);
    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return PlayerChangeAppearanceMessage.class;
    }

}

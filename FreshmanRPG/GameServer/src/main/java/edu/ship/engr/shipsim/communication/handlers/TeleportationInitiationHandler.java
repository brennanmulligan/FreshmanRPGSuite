package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.TeleportationInitiationMessage;
import edu.ship.engr.shipsim.datasource.LoggerManager;
import edu.ship.engr.shipsim.model.CommandMovePlayerToAnotherMapAndPersist;
import edu.ship.engr.shipsim.model.ModelFacade;

/**
 * Process GetServerInfoMessages by gathering the requested data and sending it
 * back to the requestor
 *
 * @author Merlin
 */
public class TeleportationInitiationHandler extends MessageHandler
{

    /**
     * This handler should retrieve the information requested and send it back
     *
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        TeleportationInitiationMessage currentMsg = (TeleportationInitiationMessage) msg;
        LoggerManager.getSingleton().getLogger().info("Received Teleportation " +
                "initiation from player " + currentMsg.getRelevantPlayerID());
        CommandMovePlayerToAnotherMapAndPersist
                command = new CommandMovePlayerToAnotherMapAndPersist(
                currentMsg.getRelevantPlayerID(),
                currentMsg.getMapName(),
                currentMsg.getPosition()
        );

        ModelFacade.getSingleton().queueCommand(command);
    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return TeleportationInitiationMessage.class;
    }

}

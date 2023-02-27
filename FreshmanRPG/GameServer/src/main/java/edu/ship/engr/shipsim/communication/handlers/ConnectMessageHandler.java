package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.ConnectionManagerList;
import edu.ship.engr.shipsim.communication.messages.ConnectMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.datasource.LoggerManager;
import edu.ship.engr.shipsim.model.CommandAddPlayer;
import edu.ship.engr.shipsim.model.ModelFacade;

/**
 * Handles a message that the player is connecting to this area server
 *
 * @author merlin
 */
public class ConnectMessageHandler extends MessageHandler
{

    /**
     * Add this player to the player list
     *
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        if (msg.getClass().equals(ConnectMessage.class))
        {
            ConnectMessage cMsg = (ConnectMessage) msg;
            LoggerManager.getSingleton().getLogger().info("Player connected: " + cMsg.getRelevantPlayerID());

            if (getConnectionManager() != null)
            {
                getConnectionManager().setPlayerID(cMsg.getRelevantPlayerID());
                ConnectionManagerList.getSingleton().add(getConnectionManager());
            }
            CommandAddPlayer cmd = new CommandAddPlayer(cMsg.getRelevantPlayerID(), cMsg.getPin());

            ModelFacade.getSingleton().queueCommand(cmd);

        }
    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return ConnectMessage.class;
    }

}

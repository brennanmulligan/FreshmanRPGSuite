package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.KeyInputMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.CommandKeyInputMessageReceived;
import edu.ship.engr.shipsim.model.ModelFacade;

/**
 * Handler that receives key input messages to unpack.
 *
 * @author Ian Keefer & TJ Renninger
 */
public class KeyInputMessageHandler extends MessageHandler
{

    /**
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        if (msg.getClass().equals(KeyInputMessage.class))
        {
            KeyInputMessage kiMsg = (KeyInputMessage) msg;
            int playerId = getStateAccumulator().getPlayerID();
            CommandKeyInputMessageReceived command = new CommandKeyInputMessageReceived(kiMsg.getInput(), playerId);
            ModelFacade.getSingleton().queueCommand(command);
        }

    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return KeyInputMessage.class;
    }

}

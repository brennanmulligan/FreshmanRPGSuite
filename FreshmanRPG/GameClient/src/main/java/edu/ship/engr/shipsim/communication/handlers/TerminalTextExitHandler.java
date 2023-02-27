package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.TerminalTextExitMessage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandRemovePlayer;

/**
 * Receives a TerminalTextExitMessage from the server.
 * Processes the message, and calls the command to remove the player from the client side.
 * <p>
 * Authors: John G. , Ian L.
 */
public class TerminalTextExitHandler extends MessageHandler
{


    /**
     * Receives the TerminalTextExitMessage sent from the server side packer (TerminalTextExitPacker).
     * <p>
     * Extracts the playerID from the message, and calls the CommandRemovePlayer to logout/remove the player from the
     * client side sources.
     *
     * @param msg the message to handle (TerminalTextExitMessage)
     */
    @Override
    public void process(Message msg)
    {
        if (msg.getClass().equals(TerminalTextExitMessage.class))
        {
            TerminalTextExitMessage message = (TerminalTextExitMessage) msg;
            CommandRemovePlayer cmd = new CommandRemovePlayer(message.getRelevantPlayerID());
            ClientModelFacade.getSingleton().queueCommand(cmd);
        }
    }

    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return TerminalTextExitMessage.class;
    }
}


package communication.handlers;

import communication.messages.Message;
import communication.messages.TerminalTextExitMessage;
import model.CommandRemovePlayer;
import model.ClientModelFacade;

/**
 * Receives a TerminalTextExitMessage from the server.
 * Processes the message, and calls the command to remove the player from the client side.
 *
 * Authors: John G. , Ian L.
 */
public class TerminalTextExitHandler extends MessageHandler
{


    /**
     * Receives the TerminalTextExitMessage sent from the server side packer (TerminalTextExitPacker).
     *
     * Extracts the playerID from the message, and calls the CommandRemovePlayer to logout/remove the player from the
     * client side sources.
     *
     * @param msg the message to handle (TerminalTextExitMessage)
     */
    @Override
    public void process(Message msg)
    {
        if(msg.getClass().equals(TerminalTextExitMessage.class))
        {
            TerminalTextExitMessage message = (TerminalTextExitMessage) msg;
            CommandRemovePlayer cmd = new CommandRemovePlayer(message.getPlayerID());
            ClientModelFacade.getSingleton().queueCommand(cmd);
        }
    }

    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return TerminalTextExitMessage.class;
    }
}


package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.ReceiveTerminalTextMessage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandReceiveTerminalResponse;

/**
 * @author Denny Fleagle
 * @author Ben Lehman
 */
public class ReceiveTerminalTextMessageHandler extends MessageHandler
{
    /**
     * Process the message
     * <p>
     * Currently the process only prints to the console
     * we should build a command that will do something more
     * meaningful in the future. ie, display to the client in the GUI
     */
    @Override
    public void process(Message msg)
    {
        ReceiveTerminalTextMessage message = (ReceiveTerminalTextMessage) msg;
        CommandReceiveTerminalResponse cmd = new CommandReceiveTerminalResponse(
                message.getRequestingPlayerID(), message.getResultText());

        ClientModelFacade.getSingleton().queueCommand(cmd);
    }

    /**
     * identify the type of the message we are handling
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return ReceiveTerminalTextMessage.class;
    }

}

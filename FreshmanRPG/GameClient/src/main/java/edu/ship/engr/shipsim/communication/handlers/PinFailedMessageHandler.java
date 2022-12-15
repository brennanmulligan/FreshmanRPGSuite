package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PinFailedMessage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandPinFailed;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.io.IOException;
import java.net.Socket;

/**
 * Should process an incoming LoginFailedMessage.
 * <p>
 * Creates the CommandPinFailed and queues it into the ModelFacade singleton.
 *
 * @author Andy and Matt
 */
public class PinFailedMessageHandler extends MessageHandler
{

    /**
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        if (msg.getClass().equals(PinFailedMessage.class))
        {
            try
            {
                if (!OptionsManager.getSingleton().isTestMode())
                {
                    getConnectionManager().moveToNewSocket(new Socket("localhost", 1871));
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            CommandPinFailed cmd = new CommandPinFailed(msg.toString());
            ClientModelFacade.getSingleton().queueCommand(cmd);
        }
    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return PinFailedMessage.class;
    }

}
package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.LoginFailedMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandLoginFailed;

/**
 * Should process an incoming LoginFailedMessage.
 * <p>
 * Creates the CommandLogInFailed and queues it into the ModelFacade singleton.
 *
 * @author Dave, Andy, Matt
 */
public class LoginFailedMessageHandler extends MessageHandler
{

    /**
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {

        if (msg.getClass().equals(LoginFailedMessage.class))
        {
            CommandLoginFailed cmd = new CommandLoginFailed(msg.toString());
            ClientModelFacade.getSingleton().queueCommand(cmd);
        }
    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return LoginFailedMessage.class;
    }

}

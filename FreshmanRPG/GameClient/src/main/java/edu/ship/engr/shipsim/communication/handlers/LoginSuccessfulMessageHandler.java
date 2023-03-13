package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.ConnectMessage;
import edu.ship.engr.shipsim.communication.messages.LoginSuccessfulMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandFinishLogin;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.io.IOException;
import java.net.Socket;

/**
 * Should process an incoming LoginSuccessulMessage. This means that we should
 * move our connection to the area server specified by that msg and initiate a
 * session with that server
 *
 * @author merlin
 */
public class LoginSuccessfulMessageHandler extends MessageHandler
{

    /**
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        if (msg.getClass().equals(LoginSuccessfulMessage.class))
        {
            LoginSuccessfulMessage rMsg = (LoginSuccessfulMessage) msg;
            try
            {
                if (!OptionsManager.getSingleton().isTestMode())
                {
                    Socket socket = new Socket(rMsg.getHostName(), rMsg.getPortNumber());
                    getConnectionManager().moveToNewSocket(socket);
                }

                CommandFinishLogin cmd = new CommandFinishLogin(rMsg.getRelevantPlayerID());
                ClientModelFacade.getSingleton().queueCommand(cmd);
                this.getStateAccumulator().queueMessage(new ConnectMessage(rMsg.getRelevantPlayerID(), rMsg.isQuietMessage(), rMsg.getPin()));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return LoginSuccessfulMessage.class;
    }

}

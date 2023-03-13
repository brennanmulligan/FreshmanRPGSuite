package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.ConnectMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.TeleportationContinuationMessage;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.io.IOException;
import java.net.Socket;

/**
 * Should process an incoming TeleportationContinuationMessage. This means that
 * we should move our connection to the area server specified by that msg and
 * initiate a session with that server
 *
 * @author merlin
 */
public class TeleportationContinuationMessageHandler extends MessageHandler
{

    /**
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        if (msg.getClass().equals(TeleportationContinuationMessage.class))
        {
            TeleportationContinuationMessage rMsg = (TeleportationContinuationMessage) msg;
            try
            {
                if (!OptionsManager.getSingleton().isTestMode())
                {
                    getConnectionManager().moveToNewSocket(new Socket(rMsg.getHostName(), rMsg.getPortNumber()));
                    this.getStateAccumulator().queueMessage(new ConnectMessage(rMsg.getRelevantPlayerID(), rMsg.isQuietMessage(), rMsg.getPin()));
                }
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
        return TeleportationContinuationMessage.class;
    }

}

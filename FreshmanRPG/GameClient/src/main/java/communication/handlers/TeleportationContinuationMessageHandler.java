package communication.handlers;

import java.io.IOException;
import java.net.Socket;

import communication.messages.ConnectMessage;
import communication.messages.Message;
import communication.messages.TeleportationContinuationMessage;
import model.OptionsManager;

/**
 * Should process an incoming TeleportationContinuationMessage. This means that
 * we should move our connection to the area server specified by that msg and
 * initiate a session with that server
 *
 * @author merlin
 *
 */
public class TeleportationContinuationMessageHandler extends MessageHandler
{

	/**
	 *
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
					this.getStateAccumulator().queueMessage(new ConnectMessage(rMsg.getPlayerID(), rMsg.getPin()));
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return TeleportationContinuationMessage.class;
	}

}

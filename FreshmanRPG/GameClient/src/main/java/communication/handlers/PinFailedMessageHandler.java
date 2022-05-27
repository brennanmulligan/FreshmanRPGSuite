package communication.handlers;

import java.io.IOException;
import java.net.Socket;

import communication.messages.Message;
import communication.messages.PinFailedMessage;
import model.ClientModelFacade;
import model.CommandPinFailed;
import model.OptionsManager;

/**
 * Should process an incoming LoginFailedMessage.
 *
 * Creates the CommandPinFailed and queues it into the ModelFacade singleton.
 *
 * @author Andy and Matt
 *
 */
public class PinFailedMessageHandler extends MessageHandler
{

	/**
	 *
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
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return PinFailedMessage.class;
	}

}
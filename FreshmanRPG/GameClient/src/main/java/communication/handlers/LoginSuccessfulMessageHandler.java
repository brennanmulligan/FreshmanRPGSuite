package communication.handlers;

import java.io.IOException;
import java.net.Socket;

import communication.messages.ConnectMessage;
import communication.messages.LoginSuccessfulMessage;
import communication.messages.Message;
import model.ClientModelFacade;
import model.CommandFinishLogin;
import model.OptionsManager;

/**
 * Should process an incoming LoginSuccessulMessage. This means that we should
 * move our connection to the area server specified by that msg and initiate a
 * session with that server
 *
 * @author merlin
 *
 */
public class LoginSuccessfulMessageHandler extends MessageHandler
{

	/**
	 *
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
				Socket socket = null;
				if (!OptionsManager.getSingleton().isUsingMockDataSource())
				{
					socket = new Socket(rMsg.getHostName(), rMsg.getPortNumber());
					getConnectionManager().moveToNewSocket(socket);
				}

				CommandFinishLogin cmd = new CommandFinishLogin(rMsg.getPlayerID());
				ClientModelFacade.getSingleton().queueCommand(cmd);
				this.getStateAccumulator().queueMessage(new ConnectMessage(rMsg.getPlayerID(), rMsg.getPin()));
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
		return LoginSuccessfulMessage.class;
	}

}

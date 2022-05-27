import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import communication.ConnectionManager;
import communication.StateAccumulator;
import communication.handlers.MessageHandlerSet;
import communication.packers.MessagePackerSet;
import datasource.LoggerManager;
import model.OptionsManager;

/**
 * A daemon that resides on the server listening to the gigabuds and to client
 * requests
 *
 * @author Merlin
 *
 */
public class LoginServer implements Runnable
{

	/**
	 *
	 */
	public LoginServer()
	{
		initializeMessageHandlers();
		LoggerManager.createLogger("LoginServer");
	}

	private void initializeMessageHandlers()
	{

	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		int i = 0;
		try
		{
			ServerSocket servSock = new ServerSocket(1871, 10);
			OptionsManager.getSingleton();
			while (true)
			{
				Socket sock = servSock.accept();
				i++;
				MessagePackerSet packers = new MessagePackerSet();
				StateAccumulator stateAccumulator = new StateAccumulator(packers);

				MessageHandlerSet handlers = new MessageHandlerSet(stateAccumulator);
				new ConnectionManager(sock, stateAccumulator, handlers, packers, true);

			}

		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param args Main runner
	 */
	public static void main(String[] args)
	{
		OptionsManager.getSingleton();
		for (String arg : args)
		{
			String[] splitArg = arg.split("=");
			if (splitArg[0].equals("--localhost"))
			{
				OptionsManager.getSingleton().setHostName("localhost");
			}
			else if (splitArg[0].equals("--production"))
			{
				OptionsManager.getSingleton().setUsingTestDB(false);
			}
		}
		LoginServer S = new LoginServer();
		S.run();
	}
}

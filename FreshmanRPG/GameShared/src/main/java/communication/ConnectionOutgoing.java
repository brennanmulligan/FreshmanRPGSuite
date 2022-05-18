package communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import communication.messages.Message;
import communication.packers.MessagePackerSet;
import datasource.DatabaseException;
import datasource.DatabaseManager;
import datasource.LoggerManager;
import model.OptionsManager;

/**
 * Responsible for communication between the server and a single connected
 * player
 *
 * @author Merlin
 *
 */
public class ConnectionOutgoing implements Runnable
{

	private ObjectOutputStream ostream;
	/**
	 *
	 */
	private StateAccumulator stateAccumulator;
	private Logger logger;
	/**
	 * @param socket Socket being used - will be null for JUnit tests
	 * @param stateAccumulator the accumulator that is gathering events that
	 *            should be sent to the other side
	 * @param messagePackerSet the set of messagepackers the outgoing connection
	 *            should use
	 * @throws IOException Exception thrown for invalid input or output
	 */
	public ConnectionOutgoing(Socket socket, StateAccumulator stateAccumulator, MessagePackerSet messagePackerSet)
			throws IOException
	{
		if (socket != null)
		{
			this.ostream = new ObjectOutputStream(socket.getOutputStream());
		}
		this.stateAccumulator = stateAccumulator;
		logger = LoggerManager.getSingleton().getLogger();
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		try
		{
			boolean done = false;
			while (!Thread.currentThread().isInterrupted() && !done)
			{
				ArrayList<Message> msgs = stateAccumulator.getPendingMsgs();
				if (msgs.size() == 0)
				{
					Thread.sleep(100);
					continue;
				}
				if (!OptionsManager.getSingleton().isUsingMockDataSource())
				{
					if (ostream != null)
					{
						for (Message msg : msgs)
						{
							try
							{
								logger.log(Level.FINE,"Sending " + msg.getClass() + " to " + stateAccumulator.getPlayerID());

								this.ostream.writeObject(msg);
							}
							catch (SocketException e)
							{
								System.out.println("Write failed.  Player: " + stateAccumulator.getPlayerID());
								cleanUpAndExit();
								done = true;
							}
						}
					}
				}
			}
		}
		catch (InterruptedException E)
		{
			cleanUpAndExit();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				DatabaseManager.getSingleton().closeConnection();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			catch (DatabaseException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 *
	 */
	private void cleanUpAndExit()
	{
		// TODO need to unobserve all of the things from the message packers

	}

	/**
	 * @return the state accumulator associated with this outgoing connecting
	 */
	public StateAccumulator getStateAccumulator()
	{
		return stateAccumulator;
	}

	/**
	 * @return the output stream we are writing to
	 */
	public ObjectOutputStream getStream()
	{
		return this.ostream;
	}
}

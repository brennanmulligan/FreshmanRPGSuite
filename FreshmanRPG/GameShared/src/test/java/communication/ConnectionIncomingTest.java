package communication;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

import datasource.LoggerManager;
import model.OptionsManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import communication.handlers.MessageHandlerSet;
import communication.messages.Message;
import communication.messages.StubMessage1;
import datasource.DatabaseException;
import datasource.DatabaseManager;

/**
 * Test the parts of the incoming connection that can be tested without a true
 * socket connection
 *
 * @author merlin
 *
 */
public class ConnectionIncomingTest
{


	/**
	 * An incoming message should be routed by the MessageProcessor to the
	 * MessageHandler register for that type of message
	 *
	 * @throws IOException shouldn't
	 */
	@Test
	public void incomingMsgGetsProcessed() throws IOException
	{
		Message msg = new StubMessage1();

		MessageHandlerSet messageHandlerSet = new MessageHandlerSet(null);
		ConnectionIncoming connection = new ConnectionIncoming(null, messageHandlerSet);
		connection.processRequest(msg);

	}

	/**
	 * Check to make sure that different threads get different database connections.
	 * @throws IOException If there is an error creating the runnable classes.
	 * @throws InterruptedException If there is a threading error.
	 */
	@Test
	public void testDatabaseConnectionsAreDifferent() throws IOException, InterruptedException
	{
		MessageHandlerSet messageHandlerSet = new MessageHandlerSet(null);
		MockConnectionIncoming c1 = new MockConnectionIncoming(null, messageHandlerSet);
		MockConnectionIncoming c2 = new MockConnectionIncoming(null, messageHandlerSet);
		Thread t1 = new Thread(c1);
		Thread t2 = new Thread(c2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		assertNotNull(c1.manager);
		assertNotNull(c2.manager);
		assertNotSame(c1.manager, c2.manager);
	}

}

class MockConnectionIncoming extends ConnectionIncoming
{

	public Connection manager;

	public MockConnectionIncoming(Socket socket, MessageHandlerSet processor) throws IOException
	{
		super(socket, processor);
	}

	@Override
	public void run()
	{
		try
		{
			this.manager = DatabaseManager.getSingleton().getConnection();
		}
		catch (DatabaseException ignored)
		{
		}
		finally
		{
			try
			{
				DatabaseManager.getSingleton().closeConnection();
			}
			catch (SQLException | DatabaseException e)
			{
				e.printStackTrace();
			}

		}
	}

}
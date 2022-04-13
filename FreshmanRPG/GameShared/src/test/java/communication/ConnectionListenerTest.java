package communication;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import datasource.DatabaseException;
import datasource.DatabaseManager;

/**
 * Test the behavior of a connection listener.
 */
public class ConnectionListenerTest
{
	/**
	 * Check to make sure that different threads get different database connections.
	 * @throws IOException If there is an error creating the runnable classes.
	 * @throws InterruptedException If there is a threading error.
	 */
	@Test
	public void testDatabaseConnectionsAreDifferent() throws IOException, InterruptedException
	{
		MockConnectionListener c1 = new MockConnectionListener(new NullStream(), 1);
		MockConnectionListener c2 = new MockConnectionListener(new NullStream(), 1);
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

class NullStream extends ObjectOutputStream
{

	protected NullStream() throws SecurityException, IOException
	{
		super();
	}

	@Override
	public void write(int bytes) throws IOException
	{
	}

}


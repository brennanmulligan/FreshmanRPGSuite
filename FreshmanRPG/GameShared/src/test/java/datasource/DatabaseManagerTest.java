package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import model.OptionsManager;

/**
 * Test the behavior of the database manager.
 */
public class DatabaseManagerTest
{

	/**
	 * @throws DatabaseException
	 *             shouldn't
	 *
	 */
	@BeforeClass
	public static void hardReset() throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingTestDB(true);
		OptionsManager.getSingleton().setTestMode(true);
		DatabaseManager.getSingleton().setTesting();
	}

	@After
	public void tearDown() throws DatabaseException
	{
			DatabaseManager.resetSingleton();
	}

	/**
	 * Tests if the singleton is the same
	 */
	@Test
	public void isSingleton() throws DatabaseException
	{
		DatabaseManager dm = DatabaseManager.getSingleton();
		DatabaseManager dm1 = DatabaseManager.getSingleton();

		assertSame(dm, dm1);
		DatabaseManager.resetSingleton();
		assertNotSame(dm, DatabaseManager.getSingleton());
	}


}

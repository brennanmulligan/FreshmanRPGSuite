package model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * The Map Manager class is pretty trivial. Everything is delegated to the data
 * source, so there isn't much to test.
 *
 */
public class GameManagerMapManagerTest
{

	/**
	 * Make sure it is a singleton
	 */
	@Test
	public void testGetInstance()
	{
		assertNotNull(GameManagerMapManager.getInstance());
		assertSame(GameManagerMapManager.getInstance(), GameManagerMapManager.getInstance());
	}

}

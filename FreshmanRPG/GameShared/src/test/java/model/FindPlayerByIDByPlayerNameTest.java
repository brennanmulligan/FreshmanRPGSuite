package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datasource.DatabaseTest;
import datasource.PlayerLoginRowDataGatewayMock;

/**
 * Test that a player Id can be found by name
 *
 */
public class FindPlayerByIDByPlayerNameTest extends DatabaseTest
{

	/**
	 * Resets the data in the mocks and sets test mode.
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		super.setUp();
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		new PlayerLoginRowDataGatewayMock().resetData();

	}


	/**
	 * Test that the correct Id is returned when searching by player name
	 * @throws DatabaseException Shouldn't
	 * @throws PlayerNotFoundException If search for player that doesn't exist
	 */
	@Test
	public void testgetPlayerIDFromPlayerName() throws DatabaseException
	{
		assertEquals(1, FindPlayerIDFromPlayerName.getPlayerID("John"));
		assertEquals(21, FindPlayerIDFromPlayerName.getPlayerID("Jawn"));
	}

	/**
	 * Test that an exception is thrown when no player found
	 * @throws DatabaseException Shouldn't
	 * @throws PlayerNotFoundException If search for player that doesn't exist
	 *
	 */
	@Test(expected = DatabaseException.class)
	public void testExceptionThrownWhenPlayerNotFound() throws DatabaseException
	{

		assertEquals(21, FindPlayerIDFromPlayerName.getPlayerID("BillyBob"));

	}

}

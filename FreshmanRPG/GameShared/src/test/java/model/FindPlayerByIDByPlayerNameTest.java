package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datasource.DatabaseException;
import datasource.ServerSideTest;

/**
 * Test that a player Id can be found by name
 *
 */
public class FindPlayerByIDByPlayerNameTest extends ServerSideTest
{

	/**
	 * Test that the correct Id is returned when searching by player name
	 * @throws DatabaseException Shouldn't
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
	 *
	 */
	@Test(expected = DatabaseException.class)
	public void testExceptionThrownWhenPlayerNotFound() throws DatabaseException
	{
		assertEquals(21, FindPlayerIDFromPlayerName.getPlayerID("BillyBob"));
	}

}

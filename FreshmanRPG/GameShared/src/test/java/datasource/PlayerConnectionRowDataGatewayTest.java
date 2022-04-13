package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import datatypes.Position;
import datatypes.PlayersForTest;

/**
 * Tests for all of our gateways
 *
 * @author Merlin
 *
 */
public abstract class PlayerConnectionRowDataGatewayTest extends DatabaseTest
{
	protected PlayerConnectionRowDataGateway gateway;

	/**
	 * Make sure any static information is cleaned up between tests
	 */
	@After
	public void cleanup()
	{
		if (gateway != null)
		{
			gateway.resetData();
		}
	}

	/**
	 * Make sure we can delete the row for the current connection information
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void deleteRow() throws DatabaseException
	{
		gateway = findRowDataGateway(4);
		gateway.deleteRow();
		boolean sawException = false;
		try
		{
			findRowDataGateway(4);
		}
		catch (DatabaseException e)
		{
			sawException = true;
		}
		assertTrue(sawException);
	}

	/**
	 * make sure we get an exception if we try to retrieve a player that isn't
	 * there
	 *
	 * @throws DatabaseException should
	 */
	@Test(expected = DatabaseException.class)
	public void exceptionOnNoRow() throws DatabaseException
	{
		gateway = findRowDataGateway(PlayersForTest.values().length + 3);
	}

	/**
	 * Make sure we can retrieve an existing entry
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void findExisting() throws DatabaseException
	{
		gateway = findRowDataGateway(PlayersForTest.FRANK.getPlayerID());
		assertEquals(PlayersForTest.FRANK.getPin(), gateway.getPin());
		assertEquals(PlayersForTest.FRANK.getMapName(), gateway.getMapName());
	}

	/**
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void changePosition() throws DatabaseException
	{
		gateway = findRowDataGateway(PlayersForTest.MERLIN.getPlayerID());
		gateway.storePosition(new Position(42, 32));
		PlayerConnectionRowDataGateway after = findRowDataGateway(PlayersForTest.MERLIN.getPlayerID());
		assertEquals(new Position(42, 32), after.getPosition());
	}

	/**
	 * Make sure we can store a map name
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void storeMapName() throws DatabaseException
	{
		gateway = findRowDataGateway(7);
		gateway.storeMapName("dumb.tmx");
		PlayerConnectionRowDataGateway after = findRowDataGateway(7);
		assertEquals("dumb.tmx", after.getMapName());
	}

	/**
	 * Make sure we can store a pin
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void storePin() throws DatabaseException
	{
		gateway = findRowDataGateway(7);
		gateway.storePin(42);
		PlayerConnectionRowDataGateway after = findRowDataGateway(7);
		assertEquals(42, after.getPin());
	}

	/**
	 * Create a row data gateway for an existing row in the table
	 *
	 * @param playerID the player's unique ID
	 * @return the gateway that we should be testing
	 * @throws DatabaseException if the data source cannot find the player
	 */
	abstract PlayerConnectionRowDataGateway findRowDataGateway(int playerID) throws DatabaseException;

}

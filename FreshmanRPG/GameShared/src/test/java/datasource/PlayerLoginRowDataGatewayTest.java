package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import datatypes.PlayersForTest;

/**
 * @author Carol
 *
 */

/**
 * @author Merlin Tests for all of the Player Login row data gateways
 */
public abstract class PlayerLoginRowDataGatewayTest extends DatabaseTest
{
	/**
	 * Create the row data gateway for a test based on existing data in the data
	 * source
	 *
	 * @param playerName the name of the player we are looking for
	 * @return the gateway for that player's row in the data source
	 * @throws DatabaseException if the gateway can't find the player
	 */
	abstract PlayerLoginRowDataGateway findRowDataGateway(String playerName) throws DatabaseException;

	/**
	 * Create the row data gateway for a test based on existing data in the data
	 * source
	 *
	 * @param playerID the unique ID of the player we are looking for
	 * @return the gateway for that player's row in the data source
	 * @throws DatabaseException if the gateway can't find the player
	 */
	abstract PlayerLoginRowDataGateway findRowDataGateway(int playerID) throws DatabaseException;

	/**
	 * Create a gateway that manages a new row being added to the data source
	 *
	 * @param playerName the name of the player
	 * @param password the player's password
	 * @return the gateway we will test
	 * @throws DatabaseException if the gateway can't create the row
	 */
	abstract PlayerLoginRowDataGateway createRowDataGateway(int playerID, String playerName, String password)
			throws DatabaseException;

	protected PlayerLoginRowDataGateway gateway;

	/**
	 * Make sure any static information is cleaned up between tests
	 *
	 * @throws SQLException if we have trouble cleaning up the db connection
	 * @throws DatabaseException if we have trouble cleaning up the db
	 *             connection
	 */
	@After
	public void tearDown() throws DatabaseException, SQLException
	{
		super.tearDown();
		if (gateway != null)
		{
			gateway.resetData();
		}
	}

	/**
	 * Make sure we can add a new user to the system
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void creation() throws DatabaseException
	{
		PlayersForTest player = PlayersForTest.JAWN;
		gateway = findRowDataGateway(player.getPlayerID());
		gateway.deleteRow();
		gateway = createRowDataGateway(
				player.getPlayerID(),
				player.getPlayerName(),
				player.getPlayerPassword());

		PlayerLoginRowDataGateway after = findRowDataGateway(player.getPlayerID());
		assertTrue(after.checkPassword(player.getPlayerPassword()));
	}


	/**
	 * Try to create a row for a map file that is already in the database
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test(expected = DatabaseException.class)
	public void createExisting() throws DatabaseException
	{
		gateway = createRowDataGateway(
				PlayersForTest.MERLIN.getPlayerID(),
				PlayersForTest.MERLIN.getPlayerName(),
				PlayersForTest.MERLIN.getPlayerPassword());
	}

	/**
	 * Make sure we can retrieve an existing player
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void findExisting() throws DatabaseException
	{
		gateway = findRowDataGateway(PlayersForTest.MERLIN.getPlayerName());
		assertEquals(2, gateway.getPlayerID());
		assertEquals(PlayersForTest.MERLIN.getPlayerName(), gateway.getPlayerName());
		assertEquals(true, gateway.checkPassword(PlayersForTest.MERLIN.getPlayerPassword()));
	}

	/**
	 * Make sure we can retrieve an existing player
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void findExistingFromPlayerID() throws DatabaseException
	{
		gateway = findRowDataGateway(PlayersForTest.MERLIN.getPlayerID());
		assertEquals(2, gateway.getPlayerID());
		assertEquals(PlayersForTest.MERLIN.getPlayerName(), gateway.getPlayerName());
		assertTrue(gateway.checkPassword(PlayersForTest.MERLIN.getPlayerPassword()));
	}

	/**
	 * make sure we get the right exception if we try to find someone who
	 * doesn't exist
	 *
	 * @throws DatabaseException should
	 */
	@Test(expected = DatabaseException.class)
	public void findNotExisting() throws DatabaseException
	{
		gateway = findRowDataGateway("no one");
	}

	/**
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void changePassword() throws DatabaseException
	{
		gateway = findRowDataGateway(PlayersForTest.MERLIN.getPlayerName());
		gateway.setPassword("not secret");
		gateway.persist();
		PlayerLoginRowDataGateway after = findRowDataGateway(PlayersForTest.MERLIN.getPlayerName());
		assertTrue(after.checkPassword("not secret"));
	}
}
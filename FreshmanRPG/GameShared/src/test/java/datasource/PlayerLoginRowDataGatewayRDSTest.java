package datasource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.SecureRandom;
import java.util.Random;

import org.junit.Test;

/**
 * Tests the RDS gateway
 *
 * @author Merlin
 *
 */
public class PlayerLoginRowDataGatewayRDSTest extends PlayerLoginRowDataGatewayTest
{

	/**
	 * @see datasource.PlayerLoginRowDataGatewayTest#findRowDataGateway(java.lang.String)
	 */
	@Override
	PlayerLoginRowDataGateway findRowDataGateway(String playerName) throws DatabaseException
	{
		return new PlayerLoginRowDataGatewayRDS(playerName);
	}

	/**
	 * @see datasource.PlayerLoginRowDataGatewayTest#createRowDataGateway(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	PlayerLoginRowDataGateway createRowDataGateway(int playerID, String playerName, String password) throws DatabaseException
	{
		return new PlayerLoginRowDataGatewayRDS(playerID, playerName, password);
	}

	/**
	 *
	 * @see datasource.PlayerLoginRowDataGatewayTest#findRowDataGateway(int)
	 */
	@Override
	PlayerLoginRowDataGateway findRowDataGateway(int playerID) throws DatabaseException
	{
		return new PlayerLoginRowDataGatewayRDS(playerID);
	}

	/**
	 * tests that a person is deleted by their id,
	 * if person is searched again, expect to catch exception
	 * @throws DatabaseException SHOULD THROW BECAUSE ROW IS DELETED
	 */
	@Test(expected = DatabaseException.class)
	public void testDeleteRow() throws DatabaseException
	{
		PlayerLoginRowDataGatewayRDS loginRowDataGateway = new PlayerLoginRowDataGatewayRDS(20);
		loginRowDataGateway.deleteRow();
		loginRowDataGateway = new PlayerLoginRowDataGatewayRDS(20);
	}

	/**
	 * Check that passwords are hashed appropriately.
	 * @throws DatabaseException Probably not
	 */
	@Test()
	public void testSetPassword() throws DatabaseException
	{
		PlayerLoginRowDataGatewayRDS loginRowDataGateway = new PlayerLoginRowDataGatewayRDS(20);
		loginRowDataGateway.setPassword("test");
		assertTrue(loginRowDataGateway.checkPassword("test"));
	}

	/**
	 * Verify that login fails if the salt silently changes
	 * @throws DatabaseException Probably not
	 */
	@Test()
	public void testBadSalt() throws DatabaseException
	{
		byte wrongSalt[] = new byte[32];
		Random rand = new SecureRandom();
		rand.nextBytes(wrongSalt);

		PlayerLoginRowDataGatewayRDS loginRowDataGateway = new PlayerLoginRowDataGatewayRDS(20);
		loginRowDataGateway.setPassword("test");
		loginRowDataGateway.setSalt(wrongSalt); // Change the salt after the password is set
		assertFalse(loginRowDataGateway.checkPassword("test"));
	}
}

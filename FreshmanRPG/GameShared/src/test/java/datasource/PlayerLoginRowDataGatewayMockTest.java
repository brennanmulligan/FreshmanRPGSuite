package datasource;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;

/**
 * Tests our mock gateway
 *
 * @author Merlin
 *
 */
public class PlayerLoginRowDataGatewayMockTest extends PlayerLoginRowDataGatewayTest
{

	/**
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp()
	{

	}

	/**
	 * Make sure any static information is cleaned up between tests
	 *
	 * @throws SQLException shouldn't
	 * @throws DatabaseException shouldn't
	 */
	@After
	public void tearDown() throws DatabaseException, SQLException
	{
		if (gateway != null)
		{
			gateway.resetData();
		}
	}

	/**
	 * @see datasource.PlayerLoginRowDataGatewayTest#findRowDataGateway(java.lang.String)
	 */
	@Override
	PlayerLoginRowDataGateway findRowDataGateway(String playerName) throws DatabaseException
	{
		return new PlayerLoginRowDataGatewayMock(playerName);
	}

	/**
	 * @see datasource.PlayerLoginRowDataGatewayTest#createRowDataGateway(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	PlayerLoginRowDataGateway createRowDataGateway(int playerID, String playerName, String password) throws DatabaseException
	{
		return new PlayerLoginRowDataGatewayMock(playerID, playerName, password);
	}

	/**
	 * @see datasource.PlayerLoginRowDataGatewayTest#findRowDataGateway(int)
	 */
	@Override
	PlayerLoginRowDataGateway findRowDataGateway(int playerID) throws DatabaseException
	{
		return new PlayerLoginRowDataGatewayMock(playerID);
	}

}

package datasource;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;

import model.OptionsManager;

/**
 * Test our mock behavior
 *
 * @author Merlin
 *
 */
public class ServerRowDataGatewayMockTest extends ServerRowDataGatewayTest
{

	/**
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp()
	{
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	/**
	 * Make sure any static information is cleaned up between tests
	 *
	 * @throws SQLException
	 *             shouldn't
	 * @throws DatabaseException
	 *             shouldn't
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
	 * @see datasource.ServerRowDataGatewayTest#createGateway(java.lang.String,
	 *      java.lang.String, int, java.lang.String, int, int)
	 */
	@Override
	public ServerRowDataGateway createGateway(String mapName, String hostName, int port, String mapTitle, int teleportPositionX, int teleportPositionY) throws DatabaseException
	{
		return new ServerRowDataGatewayMock(mapName, hostName, port, mapTitle, teleportPositionX, teleportPositionY);
	}

	/**
	 * @see datasource.ServerRowDataGatewayTest#findGateway(java.lang.String)
	 */
	@Override
	public ServerRowDataGateway findGateway(String mapName) throws DatabaseException
	{
		return new ServerRowDataGatewayMock(mapName);
	}
}

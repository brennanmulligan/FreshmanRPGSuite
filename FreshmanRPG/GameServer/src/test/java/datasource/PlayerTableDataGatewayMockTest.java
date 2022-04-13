package datasource;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;

/**
 * Tests the mock implementation
 *
 * @author merlin
 *
 */
public class PlayerTableDataGatewayMockTest extends PlayerTableDataGatewayTest
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
	 * @see datasource.PlayerTableDataGatewayTest#getGatewaySingleton()
	 */
	@Override
	public PlayerTableDataGateway getGatewaySingleton()
	{
		return PlayerTableDataGatewayMock.getSingleton();
	}

}

package datasource;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;

import model.OptionsManager;

/**
 * Tests the mock implementation
 *
 * @author merlin
 *
 */
public class AdventureStateTableDataGatewayMockTest extends AdventureStateTableDataGatewayTest
{

	/**
	 * @see datasource.AdventureStateTableDataGatewayTest#setUp()
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		this.getGateway().resetData();
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
	 * @see datasource.AdventureStateTableDataGatewayTest#getGateway()
	 */
	@Override
	public AdventureStateTableDataGateway getGateway()
	{
		return AdventureStateTableDataGatewayMock.getSingleton();
	}

}

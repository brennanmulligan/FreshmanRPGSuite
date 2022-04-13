package datasource;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;

import model.OptionsManager;

public class FriendTableDataGatewayMockTest extends FriendTableDataGatewayTest
{

	// gateway instance
	private FriendTableDataGateway gateway;

	/**
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	/**
	 *
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
	 * use the mock gateway for the tests
	 */
	@Override
	public FriendTableDataGateway getGatewaySingleton() throws DatabaseException
	{
		return FriendTableDataGatewayMock.getSingleton();
	}
}
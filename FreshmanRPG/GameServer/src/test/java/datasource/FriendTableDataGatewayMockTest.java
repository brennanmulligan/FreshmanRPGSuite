package datasource;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;

import model.OptionsManager;
import org.junit.BeforeClass;

public class FriendTableDataGatewayMockTest extends FriendTableDataGatewayTest
{

	// gateway instance
	private FriendTableDataGateway gateway;

	@BeforeClass
	public static void hardReset()
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
			gateway.resetTableGateway();
		}
	}

	/**
	 * use the mock gateway for the tests
	 */
	@Override
	public FriendTableDataGateway getGatewaySingleton() throws DatabaseException
	{
		return (FriendTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
				"Friend");
	}
}
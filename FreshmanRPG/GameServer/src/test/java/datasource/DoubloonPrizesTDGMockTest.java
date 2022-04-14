package datasource;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;

import model.OptionsManager;

/**
 *
 * @author Andrew M, Christian C
 *
 * test for mock implementation
 *
 */
public class DoubloonPrizesTDGMockTest extends DoubloonPrizesTableDataGatewayTest
{

	// gateway instance
	private DoubloonPrizesTableDataGateway gateway;

	/**
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	@After
	public void tearDown() throws DatabaseException, SQLException
	{
		super.tearDown();
		if (gateway != null)
		{
			gateway.resetData();
		}
	}


	@Override
	public DoubloonPrizesTableDataGateway getGatewaySingleton() throws DatabaseException
	{
		return DoubloonPrizesTDGMock.getInstance();
	}
}

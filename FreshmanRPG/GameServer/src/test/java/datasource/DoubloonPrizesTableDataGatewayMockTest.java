package datasource;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;

import model.OptionsManager;

//TODO This doesn't test anything!
/**
 *
 * @author Andrew M, Christian C
 *
 * test for mock implementation
 *
 */
public class DoubloonPrizesTableDataGatewayMockTest extends DoubloonPrizesTableDataGatewayTest
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
		TableDataGatewayManager.getSingleton().resetTableGateway("DoubloonPrizes");
	}

	@Override
	public DoubloonPrizesTableDataGateway getGatewaySingleton() throws DatabaseException
	{
		return null;
	}

}

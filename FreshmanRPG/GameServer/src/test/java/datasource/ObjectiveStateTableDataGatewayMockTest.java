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
public class ObjectiveStateTableDataGatewayMockTest extends ObjectiveStateTableDataGatewayTest
{

	/**
	 * @see ObjectiveStateTableDataGatewayTest#setUp()
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		this.getGateway().resetTableGateway();
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
			gateway.resetTableGateway();
		}
	}

	/**
	 * @see ObjectiveStateTableDataGatewayTest#getGateway()
	 */
	@Override
	public ObjectiveStateTableDataGateway getGateway()
	{
		return (ObjectiveStateTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
				"ObjectiveState");
	}

}

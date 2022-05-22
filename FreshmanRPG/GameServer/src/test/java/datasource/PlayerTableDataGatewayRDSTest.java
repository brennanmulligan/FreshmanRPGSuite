package datasource;

import model.OptionsManager;
import org.junit.After;
import org.junit.BeforeClass;

import java.sql.SQLException;

/**
 * Tests the mock implementation
 *
 * @author merlin
 *
 */
public class PlayerTableDataGatewayRDSTest extends PlayerTableDataGatewayTest
{
	@BeforeClass
	public static void hardReset() throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingTestDB(true);
		OptionsManager.getSingleton().setTestMode(true);
		DatabaseManager.getSingleton().setTesting();
	}

	@After
	public void tearDown() throws DatabaseException
	{
		try
		{
			DatabaseManager.getSingleton().rollBack();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * @see datasource.PlayerTableDataGatewayTest#getGatewaySingleton()
	 */
	@Override
	public PlayerTableDataGateway getGatewaySingleton()
	{
		return (PlayerTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway("Player");
	}


}
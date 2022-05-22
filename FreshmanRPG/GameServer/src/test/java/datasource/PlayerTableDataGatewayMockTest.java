package datasource;

import model.OptionsManager;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Tests the mock implementation
 *
 * @author merlin
 *
 */
public class PlayerTableDataGatewayMockTest extends PlayerTableDataGatewayTest
{
	@BeforeClass
	public static void hardReset()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	@Before
	public void setUp() throws DatabaseException
	{
		PlayerTableDataGatewayMock gateway =
				(PlayerTableDataGatewayMock) getGatewaySingleton();
		gateway.resetTableGateway();
	}

	/**
	 * @see datasource.PlayerTableDataGatewayTest#getGatewaySingleton()
	 */
	@Override
	public PlayerTableDataGateway getGatewaySingleton()
	{
		return (PlayerTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
				"Player");
	}

}

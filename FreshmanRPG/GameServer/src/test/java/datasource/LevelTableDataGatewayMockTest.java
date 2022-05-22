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
public class LevelTableDataGatewayMockTest extends LevelTableDataGatewayTest
{
	@BeforeClass
	public static void hardReset()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	@Before
	public void setUp() throws DatabaseException
	{
		LevelTableDataGatewayMock gateway = (LevelTableDataGatewayMock) getGateway();
		gateway.resetTableGateway();
	}
	/**
	 * @see datasource.LevelTableDataGatewayTest#getGateway()
	 */
	@Override
	public LevelTableDataGateway getGateway()
	{
		return (LevelTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
				"Level");
	}

}

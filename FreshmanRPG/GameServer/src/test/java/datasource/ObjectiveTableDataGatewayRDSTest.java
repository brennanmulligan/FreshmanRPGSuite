package datasource;

import model.OptionsManager;
import org.junit.BeforeClass;

/**
 * Tests the RDS Implementation
 *
 * @author merlin
 *
 */
public class ObjectiveTableDataGatewayRDSTest extends ObjectiveTableDataGatewayTest
{
	@BeforeClass
	public static void hardReset() throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingTestDB(true);
		OptionsManager.getSingleton().setTestMode(true);
		DatabaseManager.getSingleton().setTesting();
	}
	/**
	 * @see ObjectiveTableDataGatewayTest#getGateway()
	 */
	@Override
	public ObjectiveTableDataGateway getGateway()
	{
		return (ObjectiveTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
				"Objective");
	}

}

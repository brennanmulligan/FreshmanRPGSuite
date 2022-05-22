package datasource;

import model.OptionsManager;
import org.junit.After;
import org.junit.BeforeClass;

import java.sql.SQLException;

/**
 * Test for RDS
 * @author Jake Moore, Lis Ostrow
 */
public class InteractableItemTableDataGatewayRDSTest extends InteractableItemTableDataGatewayTest
{
	/**
	 * Gets singleton for test
	 */
	@Override
	public InteractableItemTableDataGateway getGatewaySingleton() throws DatabaseException
	{
		return (InteractableItemTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
				"InteractableItem");

	}

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
}

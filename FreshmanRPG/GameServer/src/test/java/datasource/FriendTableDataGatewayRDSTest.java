package datasource;

import static org.junit.Assert.*;

import model.OptionsManager;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

public class FriendTableDataGatewayRDSTest extends FriendTableDataGatewayTest
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

	@Override
	public FriendTableDataGateway getGatewaySingleton() throws DatabaseException
	{
		return (FriendTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
				"Friend");
	}
}
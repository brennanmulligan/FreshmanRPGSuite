package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import model.OptionsManager;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import dataDTO.QuestStateRecordDTO;
import datatypes.QuestStateEnum;
import datatypes.QuestStatesForTest;

/**
 * Tests the mock implementation
 *
 * @author merlin
 */
public class QuestStateTableDataGatewayRDSTest extends QuestStateTableDataGatewayTest
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
	 * @see datasource.QuestStateTableDataGatewayTest#findGateway()
	 */
	@Override
	public QuestStateTableDataGateway findGateway()
	{
		return (QuestStateTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
				"QuestState");
	}


}

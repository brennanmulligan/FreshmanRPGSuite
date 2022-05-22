package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import model.OptionsManager;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the RDS Implementation
 *
 * @author merlin
 *
 */
public class LevelTableDataGatewayRDSTest extends LevelTableDataGatewayTest
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
	 * @see ObjectiveTableDataGatewayTest#getGateway()
	 */
	@Override
	public LevelTableDataGateway getGateway()
	{
		return (LevelTableDataGatewayRDS) TableDataGatewayManager.getSingleton().getTableGateway(
				"Level");
	}

	/**
	 * Test that a new row is added when creatRow is called
	 * @throws DatabaseException if can't add row
	 */
	@Test
	public void testCreateRow() throws DatabaseException
	{
		OptionsManager x = OptionsManager.getSingleton();
		LevelTableDataGatewayRDS testGateway =
				(LevelTableDataGatewayRDS) TableDataGatewayManager.getSingleton().getTableGateway(
						"Level");
		LevelRecord testLevel = new LevelRecord("GOD", 500, 1, 30);

		testGateway.createRow(testLevel.getDescription(), testLevel.getLevelUpPoints(),
				testLevel.getLevelUpMonth(), testLevel.getLevelUpDayOfMonth());

		ArrayList<LevelRecord> testLevels = testGateway.getAllLevels();

		assertTrue(testLevels.contains(testLevel));
		assertEquals(testLevels.size(), 5);
	}

}

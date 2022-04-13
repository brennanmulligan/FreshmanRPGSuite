package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

/**
 * Tests the RDS Implementation
 *
 * @author merlin
 *
 */
public class LevelTableDataGatewayRDSTest extends LevelTableDataGatewayTest
{

	/**
	 * @see datasource.AdventureTableDataGatewayTest#getGateway()
	 */
	@Override
	public LevelTableDataGateway getGateway()
	{
		return LevelTableDataGatewayRDS.getSingleton();
	}

	/**
	 * Test that a new row is added when creatRow is called
	 * @throws DatabaseException if can't add row
	 */
	@Test
	public void testCreateRow() throws DatabaseException
	{
		LevelTableDataGatewayRDS testGate = LevelTableDataGatewayRDS.getSingleton();
		LevelRecord testLevel = new LevelRecord("GOD", 500, 1, 30);

		testGate.createRow(testLevel.getDescription(), testLevel.getLevelUpPoints(),
				testLevel.getLevelUpMonth(), testLevel.getLevelUpDayOfMonth());

		ArrayList<LevelRecord> testLevels = testGate.getAllLevels();

		assertTrue(testLevels.contains(testLevel));
		assertEquals(testLevels.size(), 5);
	}

}

package datasource;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import datatypes.LevelsForTest;

/**
 * An abstract class that tests the table data gateways into the Level table
 *
 * @author merlin
 *
 */
public abstract class LevelTableDataGatewayTest extends DatabaseTest
{

	/**
	 * @return the gateway we should test
	 */
	public abstract LevelTableDataGateway getGateway();

	/**
	 *
	 */
	@Test
	public void isASingleton()
	{
		LevelTableDataGateway x = getGateway();
		LevelTableDataGateway y = getGateway();
		assertSame(x, y);
		assertNotNull(x);
	}

	/**
	 * One method retrieves them all
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void retrievesThemAll() throws DatabaseException
	{
		LevelTableDataGateway gateway = getGateway();
		ArrayList<LevelRecord> actual = gateway.getAllLevels();

		for (LevelsForTest l : LevelsForTest.values())
		{
			LevelRecord r = new LevelRecord(l.getDescription(), l.getLevelUpPoints(), l.getLevelUpMonth(),
					l.getLevelUpDayOfMonth());
			assertTrue(actual.contains(r));
		}
	}

}

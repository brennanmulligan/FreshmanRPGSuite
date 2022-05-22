package datasource;

import datatypes.LevelsForTest;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The mock version of LevelTableDataGatewas
 *
 * @author Merlin
 *
 */
public class LevelTableDataGatewayMock implements LevelTableDataGateway
{

	static TableDataGateway getGateway()
	{
		return new LevelTableDataGatewayMock();
	}
	private HashMap<String, LevelRecord> data = new HashMap<>();

	/**
	 * Constructor for LevelTableDataGatewayMock
	 */
	private LevelTableDataGatewayMock()
	{
		putDataIntoTable();
	}

	private void putDataIntoTable()
	{
		for (LevelsForTest l : LevelsForTest.values())
		{
			data.put(l.getDescription(), new LevelRecord(l.getDescription(), l.getLevelUpPoints(), l.getLevelUpMonth(),
					l.getLevelUpDayOfMonth()));
		}
	}

	/**
	 * @see datasource.LevelTableDataGateway#getAllLevels()
	 */
	@Override
	public ArrayList<LevelRecord> getAllLevels()
	{
		ArrayList<LevelRecord> results = new ArrayList<>();
		for (String desc : data.keySet())
		{
			results.add(data.get(desc));
		}
		return results;
	}

	@Override
	public void resetTableGateway()
	{
		data = new HashMap<>();
		putDataIntoTable();
	}
}

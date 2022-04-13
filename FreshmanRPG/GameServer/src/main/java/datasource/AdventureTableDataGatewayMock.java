package datasource;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import criteria.GameLocationDTO;
import dataENUM.AdventureCompletionType;
import datatypes.AdventuresForTest;
import datatypes.Position;
import model.AdventureRecord;

/**
 * Mock version of the gateway to the table of adventures.
 *
 * @author merlin
 *
 */
public class AdventureTableDataGatewayMock implements AdventureTableDataGateway
{
	private static AdventureTableDataGateway singleton;

	/**
	 * Retrieves the mock gateway singleton.
	 *
	 * @return singleton
	 */
	public static synchronized AdventureTableDataGateway getSingleton()
	{
		if (singleton == null)
		{
			singleton = new AdventureTableDataGatewayMock();
		}
		return singleton;
	}

	private Hashtable<Integer, ArrayList<AdventureRecord>> data;

	/**
	 * build the mock data from AdventuresForTest
	 */
	private AdventureTableDataGatewayMock()
	{
		resetData();
	}

	/**
	 * @see datasource.AdventureTableDataGateway#getAdventuresForQuest(int)
	 */
	@Override
	public ArrayList<AdventureRecord> getAdventuresForQuest(int questID)
	{
		return data.getOrDefault(questID, new ArrayList<>());
	}

	/**
	 * @see datasource.AdventureTableDataGateway#getAdventure(int, int)
	 */
	@Override
	public AdventureRecord getAdventure(int questID, int adventureID)
	{
		ArrayList<AdventureRecord> adventures = data.get(questID);
		if (adventures != null)
		{
			for (AdventureRecord adv : adventures)
			{
				if (adv.getAdventureID() == adventureID)
				{
					return adv;
				}
			}
		}
		return null;
	}

	/**
	 * @see datasource.AdventureTableDataGateway#findAdventuresCompletedForMapLocation(String,
	 *      Position)
	 */
	@Override
	public ArrayList<AdventureRecord> findAdventuresCompletedForMapLocation(String mapName, Position pos)
			throws DatabaseException
	{
		Set<Integer> keys = data.keySet();
		ArrayList<AdventureRecord> results = new ArrayList<>();

		for (Integer key : keys)
		{
			ArrayList<AdventureRecord> adventuresByQuest = data.get(key);

			for (AdventureRecord a : adventuresByQuest)
			{
				if (a.getCompletionType().equals(AdventureCompletionType.MOVEMENT))
				{
					GameLocationDTO thisLocation = (GameLocationDTO) a.getCompletionCriteria();
					if (thisLocation.getPosition().equals(pos) && thisLocation.getMapName().equals(mapName))
					{
						results.add(a);
					}
				}
			}
		}

		return results;
	}

	/**
	 * @see datasource.AdventureTableDataGateway#getNextAdventureID(int)
	 */
	@Override
	public int getNextAdventureID(int questID)
	{
		return getAdventuresForQuest(questID).size() + 1;
	}

	/**
	 * @see datasource.AdventureTableDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		data = new Hashtable<>();
		for (AdventuresForTest a : AdventuresForTest.values())
		{
			AdventureRecord rec = new AdventureRecord(a.getQuestID(), a.getAdventureID(), a.getAdventureDescription(),
					a.getExperiencePointsGained(), a.getCompletionType(), a.getCompletionCriteria());

			if (data.containsKey(a.getQuestID()))
			{
				ArrayList<AdventureRecord> x = data.get(a.getQuestID());
				x.add(rec);
			}
			else
			{
				ArrayList<AdventureRecord> x = new ArrayList<>();
				x.add(rec);
				data.put(a.getQuestID(), x);
			}
		}
	}
}

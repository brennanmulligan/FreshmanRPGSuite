package datasource;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import criteria.GameLocationDTO;
import dataENUM.ObjectiveCompletionType;
import datatypes.ObjectivesForTest;
import datatypes.Position;
import model.ObjectiveRecord;

/**
 * Mock version of the gateway to the table of objectives.
 *
 * @author merlin
 *
 */
public class ObjectiveTableDataGatewayMock implements ObjectiveTableDataGateway
{
	private static ObjectiveTableDataGateway singleton;

	/**
	 * Retrieves the mock gateway singleton.
	 *
	 * @return singleton
	 */
	public static synchronized ObjectiveTableDataGateway getSingleton()
	{
		if (singleton == null)
		{
			singleton = new ObjectiveTableDataGatewayMock();
		}
		return singleton;
	}

	private Hashtable<Integer, ArrayList<ObjectiveRecord>> data;

	/**
	 * build the mock data from ObjectivesForTest
	 */
	private ObjectiveTableDataGatewayMock()
	{
		resetData();
	}

	/**
	 * @see ObjectiveTableDataGateway#getObjectivesForQuest(int)
	 */
	@Override
	public ArrayList<ObjectiveRecord> getObjectivesForQuest(int questID)
	{
		return data.getOrDefault(questID, new ArrayList<>());
	}

	/**
	 * @see ObjectiveTableDataGateway#getObjective(int, int)
	 */
	@Override
	public ObjectiveRecord getObjective(int questID, int objectiveID)
	{
		ArrayList<ObjectiveRecord> objectives = data.get(questID);
		if (objectives != null)
		{
			for (ObjectiveRecord adv : objectives)
			{
				if (adv.getObjectiveID() == objectiveID)
				{
					return adv;
				}
			}
		}
		return null;
	}

	/**
	 * @see ObjectiveTableDataGateway#findObjectivesCompletedForMapLocation(String,
	 *      Position)
	 */
	@Override
	public ArrayList<ObjectiveRecord> findObjectivesCompletedForMapLocation(String mapName, Position pos)
			throws DatabaseException
	{
		Set<Integer> keys = data.keySet();
		ArrayList<ObjectiveRecord> results = new ArrayList<>();

		for (Integer key : keys)
		{
			ArrayList<ObjectiveRecord> objectivesByQuest = data.get(key);

			for (ObjectiveRecord a : objectivesByQuest)
			{
				if (a.getCompletionType().equals(ObjectiveCompletionType.MOVEMENT))
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
	 * @see ObjectiveTableDataGateway#getNextObjectiveID(int)
	 */
	@Override
	public int getNextObjectiveID(int questID)
	{
		return getObjectivesForQuest(questID).size() + 1;
	}

	/**
	 * @see ObjectiveTableDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		data = new Hashtable<>();
		for (ObjectivesForTest a : ObjectivesForTest.values())
		{
			ObjectiveRecord rec = new ObjectiveRecord(a.getQuestID(), a.getObjectiveID(), a.getObjectiveDescription(),
					a.getExperiencePointsGained(), a.getCompletionType(), a.getCompletionCriteria());

			if (data.containsKey(a.getQuestID()))
			{
				ArrayList<ObjectiveRecord> x = data.get(a.getQuestID());
				x.add(rec);
			}
			else
			{
				ArrayList<ObjectiveRecord> x = new ArrayList<>();
				x.add(rec);
				data.put(a.getQuestID(), x);
			}
		}
	}
}

package datasource;

import java.util.ArrayList;
import java.util.Hashtable;

import dataDTO.AdventureStateRecordDTO;
import datatypes.AdventureStateEnum;
import datatypes.AdventureStatesForTest;

/**
 * Mock version of the gateway to the table of adventure states.
 *
 * @author merlin
 *
 */
public class AdventureStateTableDataGatewayMock implements AdventureStateTableDataGateway
{
	private static AdventureStateTableDataGateway singleton;

	/**
	 * Retrieves the mock gateway singleton.
	 *
	 * @return singleton
	 */
	public static synchronized AdventureStateTableDataGateway getSingleton()
	{
		if (singleton == null)
		{
			singleton = new AdventureStateTableDataGatewayMock();
		}
		return singleton;
	}

	/**
	 * build the mock data from AdventuresForTest
	 */
	private AdventureStateTableDataGatewayMock()
	{
		resetData();
	}


	private Hashtable<Key, ArrayList<AdventureStateRecordDTO>> data;
	private int maxQuestIDSeen;

	/**
	 * @see datasource.AdventureStateTableDataGateway#resetData()
	 */
	public void resetData()
	{
		data = new Hashtable<>();
		for (AdventureStatesForTest a : AdventureStatesForTest.values())
		{
			AdventureStateRecordDTO rec = new AdventureStateRecordDTO(a.getPlayerID(), a.getQuestID(), a.getAdventureID(),
					a.getState(), a.isNeedingNotification());
			if (a.getQuestID() > maxQuestIDSeen)
			{
				maxQuestIDSeen = a.getQuestID();
			}
			Key key = new Key(a.getPlayerID(), a.getQuestID());
			if (data.containsKey(key))
			{
				ArrayList<AdventureStateRecordDTO> x = data.get(key);
				x.add(rec);
			}
			else
			{
				ArrayList<AdventureStateRecordDTO> x = new ArrayList<>();
				x.add(rec);
				data.put(key, x);
			}
		}
	}

	/**
	 * @see datasource.AdventureStateTableDataGateway#getAdventureStates(int,
	 *      int)
	 */
	@Override
	public ArrayList<AdventureStateRecordDTO> getAdventureStates(int playerID, int questID)
	{
		if (data.containsKey(new Key(playerID, questID)))
		{
			return data.get(new Key(playerID, questID));
		}
		else
		{
			return new ArrayList<>();
		}
	}

	private class Key
	{
		private int playerID;
		private int questID;

		public Key(int playerID, int questID)
		{
			this.playerID = playerID;
			this.questID = questID;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + playerID;
			result = prime * result + questID;
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			if (obj == null)
			{
				return false;
			}
			if (getClass() != obj.getClass())
			{
				return false;
			}
			Key other = (Key) obj;
			if (!getOuterType().equals(other.getOuterType()))
			{
				return false;
			}
			;
			if (playerID != other.playerID)
			{
				return false;
			}
			if (questID != other.questID)
			{
				return false;
			}
			return true;
		}

		private AdventureStateTableDataGatewayMock getOuterType()
		{
			return AdventureStateTableDataGatewayMock.this;
		}
	}

	/**
	 * @see datasource.AdventureStateTableDataGateway#updateState(int, int, int,
	 *      datatypes.AdventureStateEnum, boolean)
	 */
	@Override
	public void updateState(int playerID, int questID, int adventureID, AdventureStateEnum newState,
							boolean needingNotification) throws DatabaseException
	{

		Key key = new Key(playerID, questID);
		if (data.containsKey(key))
		{

			ArrayList<AdventureStateRecordDTO> advStates = data.get(key);

			for (AdventureStateRecordDTO asRec : advStates)
			{
				if ((asRec.getQuestID() == questID) && (asRec.getAdventureID() == adventureID))
				{
					asRec.setState(newState);
					asRec.setNeedingNotification(needingNotification);

				}
			}
		}
		else
		{

			ArrayList<AdventureStateRecordDTO> list = new ArrayList<>();
			list.add(new AdventureStateRecordDTO(playerID, questID, adventureID, newState, needingNotification));
			data.put(key, list);
		}
	}

	/**
	 * @see datasource.AdventureStateTableDataGateway#createTable()
	 */
	@Override
	public void createTable() throws DatabaseException
	{
		resetData();
	}

	/**
	 * @see datasource.AdventureStateTableDataGateway#createRow(int, int, int,
	 *      datatypes.AdventureStateEnum, boolean)
	 */
	@Override
	public void createRow(int playerID, int questID, int adventureID, AdventureStateEnum state,
						  boolean needingNotification) throws DatabaseException
	{
		updateState(playerID, questID, adventureID, state, needingNotification);
	}

	/**
	 * @see datasource.AdventureStateTableDataGateway#getPendingAdventuresForPlayer(int)
	 */
	@Override
	public ArrayList<AdventureStateRecordDTO> getPendingAdventuresForPlayer(int playerID) throws DatabaseException
	{
		ArrayList<AdventureStateRecordDTO> results = new ArrayList<>();
		for (int questID = 0; questID <= maxQuestIDSeen; questID++)
		{
			if (data.containsKey(new Key(playerID, questID)))
			{
				ArrayList<AdventureStateRecordDTO> adventureList = data.get(new Key(playerID, questID));
				for (AdventureStateRecordDTO a : adventureList)
				{
					if (a.getState() == AdventureStateEnum.TRIGGERED)
					{
						results.add(a);
					}
				}
			}
		}
		return results;
	}

	/**
	 * @see datasource.AdventureStateTableDataGateway#getPendingAdventuresForPlayer(int)
	 */
	@Override
	public ArrayList<AdventureStateRecordDTO> getUncompletedAdventuresForPlayer(int playerID) throws DatabaseException
	{
		ArrayList<AdventureStateRecordDTO> results = new ArrayList<>();
		for (int questID = 0; questID <= maxQuestIDSeen; questID++)
		{
			if (data.containsKey(new Key(playerID, questID)))
			{
				ArrayList<AdventureStateRecordDTO> adventureList = data.get(new Key(playerID, questID));
				for (AdventureStateRecordDTO a : adventureList)
				{
					if (a.getState() == AdventureStateEnum.TRIGGERED ||
							a.getState() == AdventureStateEnum.HIDDEN)
					{
						results.add(a);
					}
				}
			}
		}
		return results;
	}

}

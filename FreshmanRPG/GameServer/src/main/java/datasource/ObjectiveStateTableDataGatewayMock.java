package datasource;

import java.util.ArrayList;
import java.util.Hashtable;

import dataDTO.ObjectiveStateRecordDTO;
import datatypes.ObjectiveStateEnum;
import datatypes.ObjectiveStatesForTest;

/**
 * Mock version of the gateway to the table of objective states.
 *
 * @author merlin
 *
 */
public class ObjectiveStateTableDataGatewayMock implements ObjectiveStateTableDataGateway
{
	private static ObjectiveStateTableDataGateway singleton;

	/**
	 * Retrieves the mock gateway singleton.
	 *
	 * @return singleton
	 */
	public static synchronized ObjectiveStateTableDataGateway getSingleton()
	{
		if (singleton == null)
		{
			singleton = new ObjectiveStateTableDataGatewayMock();
		}
		return singleton;
	}

	/**
	 * build the mock data from ObjectivesForTest
	 */
	private ObjectiveStateTableDataGatewayMock()
	{
		resetData();
	}


	private Hashtable<Key, ArrayList<ObjectiveStateRecordDTO>> data;
	private int maxQuestIDSeen;

	/**
	 * @see ObjectiveStateTableDataGateway#resetData()
	 */
	public void resetData()
	{
		data = new Hashtable<>();
		for (ObjectiveStatesForTest objectiveState : ObjectiveStatesForTest.values())
		{
			ObjectiveStateRecordDTO rec = new ObjectiveStateRecordDTO(objectiveState.getPlayerID(), objectiveState.getQuestID(), objectiveState.getObjectiveID(),
					objectiveState.getState(), objectiveState.isNeedingNotification());
			if (objectiveState.getQuestID() > maxQuestIDSeen)
			{
				maxQuestIDSeen = objectiveState.getQuestID();
			}
			Key key = new Key(objectiveState.getPlayerID(), objectiveState.getQuestID());
			if (data.containsKey(key))
			{
				ArrayList<ObjectiveStateRecordDTO> x = data.get(key);
				x.add(rec);
			}
			else
			{
				ArrayList<ObjectiveStateRecordDTO> x = new ArrayList<>();
				x.add(rec);
				data.put(key, x);
			}
		}
	}

	/**
	 * @see ObjectiveStateTableDataGateway#getObjectiveStates(int,
	 *      int)
	 */
	@Override
	public ArrayList<ObjectiveStateRecordDTO> getObjectiveStates(int playerID, int questID)
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

		private ObjectiveStateTableDataGatewayMock getOuterType()
		{
			return ObjectiveStateTableDataGatewayMock.this;
		}
	}

	/**
	 * @see ObjectiveStateTableDataGateway#updateState(int, int, int,
	 *      ObjectiveStateEnum, boolean)
	 */
	@Override
	public void updateState(int playerID, int questID, int objectiveID, ObjectiveStateEnum newState,
							boolean needingNotification) throws DatabaseException
	{

		Key key = new Key(playerID, questID);
		if (data.containsKey(key))
		{

			ArrayList<ObjectiveStateRecordDTO> advStates = data.get(key);

			for (ObjectiveStateRecordDTO asRec : advStates)
			{
				if ((asRec.getQuestID() == questID) && (asRec.getObjectiveID() == objectiveID))
				{
					asRec.setState(newState);
					asRec.setNeedingNotification(needingNotification);

				}
			}
		}
		else
		{

			ArrayList<ObjectiveStateRecordDTO> list = new ArrayList<>();
			list.add(new ObjectiveStateRecordDTO(playerID, questID, objectiveID, newState, needingNotification));
			data.put(key, list);
		}
	}

	/**
	 * @see ObjectiveStateTableDataGateway#createTable()
	 */
	@Override
	public void createTable() throws DatabaseException
	{
		resetData();
	}

	/**
	 * @see ObjectiveStateTableDataGateway#createRow(int, int, int,
	 *      ObjectiveStateEnum, boolean)
	 */
	@Override
	public void createRow(int playerID, int questID, int objectiveID, ObjectiveStateEnum state,
                          boolean needingNotification) throws DatabaseException
	{
		updateState(playerID, questID, objectiveID, state, needingNotification);
	}

	/**
	 * @see ObjectiveStateTableDataGateway#getPendingObjectivesForPlayer(int)
	 */
	@Override
	public ArrayList<ObjectiveStateRecordDTO> getPendingObjectivesForPlayer(int playerID) throws DatabaseException
	{
		ArrayList<ObjectiveStateRecordDTO> results = new ArrayList<>();
		for (int questID = 0; questID <= maxQuestIDSeen; questID++)
		{
			if (data.containsKey(new Key(playerID, questID)))
			{
				ArrayList<ObjectiveStateRecordDTO> objectiveList = data.get(new Key(playerID, questID));
				for (ObjectiveStateRecordDTO a : objectiveList)
				{
					if (a.getState() == ObjectiveStateEnum.TRIGGERED)
					{
						results.add(a);
					}
				}
			}
		}
		return results;
	}

	/**
	 * @see ObjectiveStateTableDataGateway#getPendingObjectivesForPlayer(int)
	 */
	@Override
	public ArrayList<ObjectiveStateRecordDTO> getUncompletedObjectivesForPlayer(int playerID) throws DatabaseException
	{
		ArrayList<ObjectiveStateRecordDTO> results = new ArrayList<>();
		for (int questID = 0; questID <= maxQuestIDSeen; questID++)
		{
			if (data.containsKey(new Key(playerID, questID)))
			{
				ArrayList<ObjectiveStateRecordDTO> objectiveList = data.get(new Key(playerID, questID));
				for (ObjectiveStateRecordDTO a : objectiveList)
				{
					if (a.getState() == ObjectiveStateEnum.TRIGGERED ||
							a.getState() == ObjectiveStateEnum.HIDDEN)
					{
						results.add(a);
					}
				}
			}
		}
		return results;
	}

}

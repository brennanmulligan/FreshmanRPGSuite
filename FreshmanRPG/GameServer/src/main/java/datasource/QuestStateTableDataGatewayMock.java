package datasource;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import dataDTO.QuestStateRecordDTO;
import datatypes.QuestStateEnum;
import datatypes.QuestStatesForTest;

/**
 * A mock implementation of the gateway
 *
 * @author Merlin
 *
 */
public class QuestStateTableDataGatewayMock implements QuestStateTableDataGateway
{
	private static QuestStateTableDataGateway singleton;

	/**
	 * Retrieves the mock gateway singleton.
	 *
	 * @return singleton
	 */
	public static synchronized QuestStateTableDataGateway getSingleton()
	{
		if (singleton == null)
		{
			singleton = new QuestStateTableDataGatewayMock();
		}
		return singleton;
	}

	private Hashtable<Integer, ArrayList<QuestStateRecordDTO>> data;

	/**
	 * build the mock data from ObjectivesForTest
	 */
	private QuestStateTableDataGatewayMock()
	{
		resetData();
	}

	/**
	 * @see datasource.QuestStateTableDataGateway#resetData()
	 */
	public void resetData()
	{
		data = new Hashtable<>();
		for (QuestStatesForTest a : QuestStatesForTest.values())
		{
			QuestStateRecordDTO rec = new QuestStateRecordDTO(a.getPlayerID(), a.getQuestID(), a.getState(),
					a.isNeedingNotification());

			try
			{
				insertRow(a.getPlayerID(), rec);
			}
			catch (DatabaseException e)
			{
				// QuestStatesForTest should not contain duplicate entries and
				// that is the only way we can get here
				e.printStackTrace();
			}
		}
	}

	private void insertRow(int playerID, QuestStateRecordDTO rec) throws DatabaseException
	{
		if (data.containsKey(playerID))
		{
			ArrayList<QuestStateRecordDTO> x = data.get(playerID);
			for (QuestStateRecordDTO r : x)
			{
				if (r.getQuestID() == rec.getQuestID())
				{
					throw new DatabaseException(
							"Duplicate quest state: player ID " + playerID + " and quest id " + rec.getQuestID());
				}
			}
			x.add(rec);
		}
		else
		{
			ArrayList<QuestStateRecordDTO> x = new ArrayList<>();
			x.add(rec);
			data.put(playerID, x);
		}
	}

	/**
	 * @see datasource.QuestStateTableDataGateway#getQuestStates(int)
	 */
	@Override
	public ArrayList<QuestStateRecordDTO> getQuestStates(int playerID)
	{
		if (data.containsKey(playerID))
		{
			return data.get(playerID);
		}
		else
		{
			return new ArrayList<>();
		}
	}

	/**
	 * @see datasource.QuestStateTableDataGateway#createTable()
	 */
	@Override
	public void createTable() throws DatabaseException
	{
		resetData();
	}

	/**
	 * @see datasource.QuestStateTableDataGateway#createRow(int, int,
	 *      datatypes.QuestStateEnum, boolean)
	 */
	@Override
	public void createRow(int playerID, int questID, QuestStateEnum state, boolean needingNotification)
			throws DatabaseException
	{
		insertRow(playerID, new QuestStateRecordDTO(playerID, questID, state, needingNotification));

	}

	/**
	 * @throws DatabaseException shouldn't
	 * @see datasource.QuestStateTableDataGateway#udpateState(int, int,
	 *      datatypes.QuestStateEnum, boolean)
	 */
	@Override
	public void udpateState(int playerID, int questID, QuestStateEnum newState, boolean needingNotification)
			throws DatabaseException
	{
		boolean updated = false;
		ArrayList<QuestStateRecordDTO> quests = data.get(playerID);
		if (quests == null)
		{
			quests = new ArrayList<>();
		}
		for (QuestStateRecordDTO qsRec : quests)
		{
			if (qsRec.getQuestID() == questID)
			{
				updated = true;
				qsRec.setState(newState);
				qsRec.setNeedingNotification(needingNotification);
			}
		}
		if (!updated)
		{
			createRow(playerID, questID, newState, needingNotification);
		}

	}

	/**
	 * Retrieves all quests from the gateway
	 */
	@Override
	public ArrayList<QuestStateRecordDTO> retrieveAllQuestStates()
	{
		ArrayList<QuestStateRecordDTO> listOfQuestStateRecords = new ArrayList<>();
		Set<Integer> keys = data.keySet();
		for (Integer key : keys)
		{
			listOfQuestStateRecords.addAll(data.get(key));
		}
		return listOfQuestStateRecords;
	}

	/**
	 * deletes a quest state when needed to
	 */
	@Override
	public void deleteQuestState(int questID)
	{
		data.remove(questID);
	}

}

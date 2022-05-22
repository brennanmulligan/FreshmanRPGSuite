package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dataDTO.QuestStateRecordDTO;
import datatypes.QuestStateEnum;

/**
 * The RDS implementation of the gateway
 *
 * @author Merlin
 *
 */
public class QuestStateTableDataGatewayRDS implements QuestStateTableDataGateway
{

	static TableDataGateway getGateway()
	{
		return new QuestStateTableDataGatewayRDS();
	}

	/**
	 * A private constructor only called by the getSingleton method
	 */
	private QuestStateTableDataGatewayRDS()
	{
		//do nothing this just explicitly makes it private
	}

	private void checkForDuplicateEntry(int playerID, int questID) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement(
					"SELECT * FROM QuestStates WHERE playerID = ? and questID = ?");
			stmt.setInt(1, playerID);
			stmt.setInt(2, questID);
			ResultSet result = stmt.executeQuery();

			if (result.next())
			{
				throw new DatabaseException(
						"Duplicate quest state for player ID " + playerID + " and quest id " + questID);
			}
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find quests for player ID " + playerID, e);
		}
	}

	private QuestStateEnum convertToState(int int1)
	{
		return QuestStateEnum.values()[int1];
	}

	/**
	 * Add a new row to the table
	 *
	 * @param playerID the player
	 * @param questID the quest
	 * @param state the player's state in that quest
	 * @param needingNotification true if the player should be notified about
	 *            this state
	 * @throws DatabaseException if we can't talk to the RDS server
	 */
	public void createRow(int playerID, int questID, QuestStateEnum state, boolean needingNotification)
			throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		checkForDuplicateEntry(playerID, questID);
		try
		{
			PreparedStatement stmt =  connection.prepareStatement(
					"Insert INTO QuestStates SET playerID = ?, questID = ?, questState = ?, needingNotification = ?");
			stmt.setInt(1, playerID);
			stmt.setInt(2, questID);
			stmt.setInt(3, state.getID());
			stmt.setBoolean(4, needingNotification);
			stmt.executeUpdate();

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't create a quest state record for player with ID " + playerID
					+ " and quest with ID " + questID, e);
		}
	}

	/**
	 * Drop the table if it exists and re-create it empty
	 *
	 * @throws DatabaseException shouldn't
	 */
	public void createTable() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement(
					"DROP TABLE IF EXISTS QuestStates");
			stmt.executeUpdate();
			stmt.close();

			stmt =  connection.prepareStatement(
					"Create TABLE QuestStates (playerID INT NOT NULL, questID INT NOT NULL , questState INT NOT NULL, needingNotification BOOLEAN NOT NULL)");
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the QuestStates table", e);
		}
	}

	/**
	 * @see datasource.QuestStateTableDataGateway#getQuestStates(int)
	 */
	@Override
	public ArrayList<QuestStateRecordDTO> getQuestStates(int playerID) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement(
					"SELECT * FROM QuestStates WHERE playerID = ?");
			stmt.setInt(1, playerID);
			ResultSet result = stmt.executeQuery();

			ArrayList<QuestStateRecordDTO> results = new ArrayList<>();
			while (result.next())
			{
				QuestStateRecordDTO rec = new QuestStateRecordDTO(result.getInt("playerID"), result.getInt("questID"),
						convertToState(result.getInt("QuestState")), result.getBoolean("needingNotification"));
				results.add(rec);
			}
			return results;
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find QuestStates record for player with playerID " + playerID, e);
		}
	}

	public void resetTableGateway()
	{
		// Nothing required
	}

	/**
	 * @see datasource.QuestStateTableDataGateway#udpateState(int, int,
	 *      datatypes.QuestStateEnum, boolean)
	 */
	@Override
	public void udpateState(int playerID, int questID, QuestStateEnum newState, boolean needingNotification)
			throws DatabaseException
	{

		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement(
					"UPDATE QuestStates SET questState = ?, needingNotification = ? WHERE  playerID = ? and questID = ?");
			stmt.setInt(1, newState.getID());
			stmt.setBoolean(2, needingNotification);
			stmt.setInt(3, playerID);
			stmt.setInt(4, questID);
			int count = stmt.executeUpdate();
			if (count == 0)
			{
				this.createRow(playerID, questID, newState, needingNotification);
			}
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't update a quest state record for player with ID " + playerID
					+ " and quest with ID " + questID, e);
		}

	}

	/**
	 *
	 * Returns a list of all quest
	 * @throws DatabaseException shouldn't
	 */
	@Override
	public ArrayList<QuestStateRecordDTO> retrieveAllQuestStates() throws DatabaseException
	{
		ArrayList<QuestStateRecordDTO> listOfQuestStates = new ArrayList<>();
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement( "select * from QuestStates");
			ResultSet rs = stmt.executeQuery();

			HashMap<Integer, QuestStateEnum> recordMap = new HashMap<>();
			for (QuestStateEnum value : QuestStateEnum.values())
			{
				recordMap.put(value.getID(), value);
			}

			while (rs.next())
			{
				int playerID = rs.getInt("playerID");
				int questID = rs.getInt("questID");
				int questStateID = rs.getInt("questState");
				boolean needingNotification = rs.getBoolean("needingNotification");

				QuestStateRecordDTO questStateRecord = new QuestStateRecordDTO(playerID, questID, recordMap.get(questStateID), needingNotification);
				listOfQuestStates.add(questStateRecord);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return listOfQuestStates;
	}


}

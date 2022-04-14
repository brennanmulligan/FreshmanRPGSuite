
package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dataDTO.ObjectiveStateRecordDTO;
import datatypes.ObjectiveStateEnum;

/**
 * The RDS Implementation of this gateway
 *
 * @author merlin
 *
 */
public class ObjectiveStateTableDataGatewayRDS implements ObjectiveStateTableDataGateway
{

	private static ObjectiveStateTableDataGateway singleton;

	/**
	 * Retrieves the rds gateway singleton.
	 *
	 * @return singleton
	 */
	public static synchronized ObjectiveStateTableDataGateway getSingleton()
	{
		if (singleton == null)
		{
			singleton = new ObjectiveStateTableDataGatewayRDS();
		}
		return singleton;
	}

	/**
	 * A private constructor only called by the getSingleton method
	 */
	private ObjectiveStateTableDataGatewayRDS()
	{
		//do nothing this just explicitly makes it private
	}

	/**
	 * Drop the table if it exists and re-create it empty
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Override
	public void createTable() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"DROP TABLE IF EXISTS ObjectiveStates");
			stmt.executeUpdate();
			stmt.close();
			stmt = connection.prepareStatement(
					"Create TABLE ObjectiveStates (objectiveID INT NOT NULL, questID INT NOT NULL, playerID INT NOT NULL, "
							+ "objectiveState INT, needingNotification BOOLEAN)");
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the Objective State table", e);
		}
	}

	/**
	 * @see ObjectiveStateTableDataGateway#getObjectiveStates(int,
	 *      int)
	 */
	@Override
	public ArrayList<ObjectiveStateRecordDTO> getObjectiveStates(int playerID, int questID) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * FROM ObjectiveStates WHERE questID = ? and playerID = ?");
			stmt.setInt(1, questID);
			stmt.setInt(2, playerID);
			ResultSet result = stmt.executeQuery();

			ArrayList<ObjectiveStateRecordDTO> results = new ArrayList<>();
			while (result.next())
			{
				ObjectiveStateRecordDTO rec = new ObjectiveStateRecordDTO(result.getInt("playerID"),
						result.getInt("questID"), result.getInt("objectiveID"),
						convertToState(result.getInt("objectiveState")), result.getBoolean("needingNotification"));
				results.add(rec);
			}
			return results;
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find objective for quest ID " + questID, e);
		}
	}

	private ObjectiveStateEnum convertToState(int int1)
	{
		return ObjectiveStateEnum.values()[int1];
	}

	/**
	 * Create a new row in the table
	 *
	 * @param playerID the player ID
	 * @param questID the quest that contains the objective
	 * @param objectiveID the unique ID of the objective
	 * @param objectiveState the state of this objective for this player
	 * @param needingNotification true if the player should be notified about
	 *            the state of this objective
	 * @throws DatabaseException if we can't talk to the RDS
	 */
	@Override
	public void createRow(int playerID, int questID, int objectiveID, ObjectiveStateEnum objectiveState,
						  boolean needingNotification) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"Insert INTO ObjectiveStates SET questID = ?, objectiveID = ?, playerID = ?, objectiveState = ?, needingNotification = ?");
			stmt.setInt(1, questID);
			stmt.setInt(2, objectiveID);
			stmt.setInt(3, playerID);
			stmt.setInt(4, objectiveState.getID());
			stmt.setBoolean(5, needingNotification);
			stmt.executeUpdate();

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't create a objective state record for objective with ID " + objectiveID,
					e);
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

		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"UPDATE ObjectiveStates SET objectiveState = ?, needingNotification = ? WHERE  playerID = ? and questID = ? and objectiveID = ?");
			stmt.setInt(1, newState.getID());
			stmt.setBoolean(2, needingNotification);
			stmt.setInt(3, playerID);
			stmt.setInt(4, questID);
			stmt.setInt(5, objectiveID);
			int count = stmt.executeUpdate();
			if (count == 0)
			{
				this.createRow(playerID, questID, objectiveID, newState, true);
			}
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't update an objective state record for player with ID " + playerID
					+ " and quest with ID " + questID, e);
		}

	}

	/**
	 * @see ObjectiveStateTableDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		// nothing necessary

	}

	/**
	 * @see ObjectiveStateTableDataGateway#getPendingObjectivesForPlayer(int)
	 */
	@Override
	public ArrayList<ObjectiveStateRecordDTO> getPendingObjectivesForPlayer(int playerID) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * FROM ObjectiveStates WHERE objectiveState = ? and playerID = ?");
			stmt.setInt(1, ObjectiveStateEnum.TRIGGERED.getID());
			stmt.setInt(2, playerID);
			ResultSet result = stmt.executeQuery();

			ArrayList<ObjectiveStateRecordDTO> results = new ArrayList<>();
			while (result.next())
			{
				ObjectiveStateRecordDTO rec = new ObjectiveStateRecordDTO(result.getInt("playerID"),
						result.getInt("questID"), result.getInt("objectiveID"),
						convertToState(result.getInt("objectiveState")), result.getBoolean("needingNotification"));
				results.add(rec);
			}
			return results;
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find pending objectives for player ID " + playerID, e);
		}
	}

	/**
	 * Get a list of all of the uncompleted (e.g. HIDDEN or TRIGGERED) objectives that a player current has
	 *
	 * @param playerID the player
	 * @return the list
	 * @throws DatabaseException if we can't talk to the data source
	 */
	@Override
	public ArrayList<ObjectiveStateRecordDTO> getUncompletedObjectivesForPlayer(int playerID) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * FROM ObjectiveStates WHERE (objectiveState = ? OR objectiveState = ?) and playerID = ?");
			stmt.setInt(1, ObjectiveStateEnum.TRIGGERED.getID());
			stmt.setInt(2, ObjectiveStateEnum.HIDDEN.getID());
			stmt.setInt(3, playerID);
			ResultSet result = stmt.executeQuery();

			ArrayList<ObjectiveStateRecordDTO> results = new ArrayList<>();
			while (result.next())
			{
				ObjectiveStateRecordDTO rec = new ObjectiveStateRecordDTO(result.getInt("playerID"),
						result.getInt("questID"), result.getInt("objectiveID"),
						convertToState(result.getInt("objectiveState")), result.getBoolean("needingNotification"));
				results.add(rec);
			}
			return results;
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find pending objectives for player ID " + playerID, e);
		}
	}
}

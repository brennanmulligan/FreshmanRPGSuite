
package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dataDTO.AdventureStateRecordDTO;
import datatypes.AdventureStateEnum;

/**
 * The RDS Implementation of this gateway
 *
 * @author merlin
 *
 */
public class AdventureStateTableDataGatewayRDS implements AdventureStateTableDataGateway
{

	private static AdventureStateTableDataGateway singleton;

	/**
	 * Retrieves the rds gateway singleton.
	 *
	 * @return singleton
	 */
	public static synchronized AdventureStateTableDataGateway getSingleton()
	{
		if (singleton == null)
		{
			singleton = new AdventureStateTableDataGatewayRDS();
		}
		return singleton;
	}

	/**
	 * A private constructor only called by the getSingleton method
	 */
	private AdventureStateTableDataGatewayRDS()
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
					"DROP TABLE IF EXISTS AdventureStates");
			stmt.executeUpdate();
			stmt.close();
			stmt = connection.prepareStatement(
					"Create TABLE AdventureStates (adventureID INT NOT NULL, questID INT NOT NULL, playerID INT NOT NULL, "
							+ "adventureState INT, needingNotification BOOLEAN)");
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the Adventure State table", e);
		}
	}

	/**
	 * @see datasource.AdventureStateTableDataGateway#getAdventureStates(int,
	 *      int)
	 */
	@Override
	public ArrayList<AdventureStateRecordDTO> getAdventureStates(int playerID, int questID) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * FROM AdventureStates WHERE questID = ? and playerID = ?");
			stmt.setInt(1, questID);
			stmt.setInt(2, playerID);
			ResultSet result = stmt.executeQuery();

			ArrayList<AdventureStateRecordDTO> results = new ArrayList<>();
			while (result.next())
			{
				AdventureStateRecordDTO rec = new AdventureStateRecordDTO(result.getInt("playerID"),
						result.getInt("questID"), result.getInt("adventureID"),
						convertToState(result.getInt("adventureState")), result.getBoolean("needingNotification"));
				results.add(rec);
			}
			return results;
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find adventures for quest ID " + questID, e);
		}
	}

	private AdventureStateEnum convertToState(int int1)
	{
		return AdventureStateEnum.values()[int1];
	}

	/**
	 * Create a new row in the table
	 *
	 * @param playerID the player ID
	 * @param questID the quest that contains the adventure
	 * @param adventureID the unique ID of the adventure
	 * @param adventureState the state of this adventure for this player
	 * @param needingNotification true if the player should be notified about
	 *            the state of this adventure
	 * @throws DatabaseException if we can't talk to the RDS
	 */
	@Override
	public void createRow(int playerID, int questID, int adventureID, AdventureStateEnum adventureState,
						  boolean needingNotification) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"Insert INTO AdventureStates SET questID = ?, adventureID = ?, playerID = ?, adventureState = ?, needingNotification = ?");
			stmt.setInt(1, questID);
			stmt.setInt(2, adventureID);
			stmt.setInt(3, playerID);
			stmt.setInt(4, adventureState.getID());
			stmt.setBoolean(5, needingNotification);
			stmt.executeUpdate();

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't create a adventure state record for adventure with ID " + adventureID,
					e);
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

		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"UPDATE AdventureStates SET adventureState = ?, needingNotification = ? WHERE  playerID = ? and questID = ? and adventureID = ?");
			stmt.setInt(1, newState.getID());
			stmt.setBoolean(2, needingNotification);
			stmt.setInt(3, playerID);
			stmt.setInt(4, questID);
			stmt.setInt(5, adventureID);
			int count = stmt.executeUpdate();
			if (count == 0)
			{
				this.createRow(playerID, questID, adventureID, newState, true);
			}
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't update an adventure state record for player with ID " + playerID
					+ " and quest with ID " + questID, e);
		}

	}

	/**
	 * @see datasource.AdventureStateTableDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		// nothing necessary

	}

	/**
	 * @see datasource.AdventureStateTableDataGateway#getPendingAdventuresForPlayer(int)
	 */
	@Override
	public ArrayList<AdventureStateRecordDTO> getPendingAdventuresForPlayer(int playerID) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * FROM AdventureStates WHERE adventureState = ? and playerID = ?");
			stmt.setInt(1, AdventureStateEnum.TRIGGERED.getID());
			stmt.setInt(2, playerID);
			ResultSet result = stmt.executeQuery();

			ArrayList<AdventureStateRecordDTO> results = new ArrayList<>();
			while (result.next())
			{
				AdventureStateRecordDTO rec = new AdventureStateRecordDTO(result.getInt("playerID"),
						result.getInt("questID"), result.getInt("adventureID"),
						convertToState(result.getInt("adventureState")), result.getBoolean("needingNotification"));
				results.add(rec);
			}
			return results;
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find pending adventures for player ID " + playerID, e);
		}
	}

	/**
	 * Get a list of all of the uncompleted (e.g. HIDDEN or TRIGGERED) adventures that a player current has
	 *
	 * @param playerID the player
	 * @return the list
	 * @throws DatabaseException if we can't talk to the data source
	 */
	@Override
	public ArrayList<AdventureStateRecordDTO> getUncompletedAdventuresForPlayer(int playerID) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * FROM AdventureStates WHERE (adventureState = ? OR adventureState = ?) and playerID = ?");
			stmt.setInt(1, AdventureStateEnum.TRIGGERED.getID());
			stmt.setInt(2, AdventureStateEnum.HIDDEN.getID());
			stmt.setInt(3, playerID);
			ResultSet result = stmt.executeQuery();

			ArrayList<AdventureStateRecordDTO> results = new ArrayList<>();
			while (result.next())
			{
				AdventureStateRecordDTO rec = new AdventureStateRecordDTO(result.getInt("playerID"),
						result.getInt("questID"), result.getInt("adventureID"),
						convertToState(result.getInt("adventureState")), result.getBoolean("needingNotification"));
				results.add(rec);
			}
			return results;
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find pending adventures for player ID " + playerID, e);
		}
	}
}

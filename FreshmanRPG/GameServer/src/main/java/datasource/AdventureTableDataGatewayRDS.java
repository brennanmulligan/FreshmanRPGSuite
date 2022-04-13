package datasource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import criteria.AdventureCompletionCriteria;
import criteria.GameLocationDTO;
import dataENUM.AdventureCompletionType;
import datatypes.Position;
import model.AdventureRecord;

/**
 * The RDS Implementation of this gateway
 *
 * @author merlin
 */
public class AdventureTableDataGatewayRDS implements AdventureTableDataGateway
{

	private static AdventureTableDataGateway singleton;

	/**
	 * Retrieves the rds gateway singleton.
	 *
	 * @return singleton
	 */
	public static synchronized AdventureTableDataGateway getSingleton()
	{
		if (singleton == null)
		{
			singleton = new AdventureTableDataGatewayRDS();
		}
		return singleton;
	}

	/**
	 * A private constructor only called by the getSingleton method
	 */
	private AdventureTableDataGatewayRDS()
	{
		//do nothing, this just explicitly makes it private
	}

	/**
	 * Drop the table if it exists and re-create it empty
	 *
	 * @throws DatabaseException shouldn't
	 */
	public static void createTable() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =connection.prepareStatement( "DROP TABLE IF EXISTS Adventures");
			stmt.executeUpdate();
			stmt.close();

			stmt = connection.prepareStatement( "Create TABLE Adventures (adventureID INT NOT NULL, adventureDescription VARCHAR(200), "
					+ "questID INT NOT NULL, experiencePointsGained INT, completionType INT, completionCriteria BLOB, PRIMARY KEY(questID, adventureID))");
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the Adventure table", e);
		}
	}

	/**
	 * @see datasource.AdventureTableDataGateway#getAdventuresForQuest(int)
	 */
	@Override
	public ArrayList<AdventureRecord> getAdventuresForQuest(int questID) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement( "SELECT * FROM Adventures WHERE questID = ?");
			stmt.setInt(1, questID);
			ResultSet queryResult = stmt.executeQuery();

			ArrayList<AdventureRecord> results = new ArrayList<>();
			while (queryResult.next())
			{
				AdventureRecord rec = buildAdventureRecord(queryResult);
				results.add(rec);
			}
			return results;
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find adventures for quest ID " + questID, e);
		}
	}

	/**
	 * Create a new row in the table
	 *
	 * @param adventureID the unique ID of the adventure
	 * @param adventureDescription the description of the adventure
	 * @param questID the quest that contains the adventure
	 * @param experiencePointsEarned the number of points you get when you complete
	 *        this adventure
	 * @param completionType the type of action the player must do to complete this
	 *        adventure
	 * @param completionCriteria the criteria for completing this adventure
	 * @throws DatabaseException if we can't talk to the RDS
	 */
	public static void createRow(int adventureID, String adventureDescription, int questID, int experiencePointsEarned, AdventureCompletionType completionType, AdventureCompletionCriteria completionCriteria)
			throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement( "Insert INTO Adventures SET adventureID = ?, adventureDescription = ?, questID = ?,"
					+ "experiencePointsGained = ?, completionType = ?, completionCriteria = ?");
			stmt.setInt(1, adventureID);
			stmt.setString(2, adventureDescription);
			stmt.setInt(3, questID);
			stmt.setInt(4, experiencePointsEarned);
			stmt.setInt(5, completionType.getID());
			stmt.setObject(6, completionCriteria);
			stmt.executeUpdate();

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't create a adventure record for adventure with ID " + adventureID, e);
		}
	}

	/**
	 * @see datasource.AdventureTableDataGateway#getAdventure(int, int)
	 */
	@Override
	public AdventureRecord getAdventure(int questID, int adventureID) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Adventures WHERE questID = ? and AdventureID = ?");
			stmt.setInt(1, questID);
			stmt.setInt(2, adventureID);
			ResultSet queryResult = stmt.executeQuery();

			if (queryResult.next())
			{
				AdventureRecord rec = buildAdventureRecord(queryResult);
				return rec;
			}
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find adventure " + adventureID + " for quest ID " + questID, e);
		}
		return null;
	}

	private AdventureRecord buildAdventureRecord(ResultSet queryResult) throws DatabaseException
	{
		try
		{
			AdventureCompletionType completionType = AdventureCompletionType.findByID(queryResult.getInt("completionType"));
			AdventureCompletionCriteria completionCriteria = extractCompletionCriteria(queryResult, completionType);

			AdventureRecord rec = new AdventureRecord(queryResult.getInt("questID"), queryResult.getInt("adventureID"), queryResult.getString("adventureDescription"), queryResult
					.getInt("experiencePointsGained"), completionType, completionCriteria);
			return rec;
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Exception trying to parse the results of reading an adventure", e);
		}
	}

	protected static AdventureCompletionCriteria extractCompletionCriteria(ResultSet queryResult, AdventureCompletionType completionType) throws SQLException, DatabaseException
	{
		Class<? extends AdventureCompletionCriteria> completionCriteriaClass = completionType.getCompletionCriteriaType();
		ByteArrayInputStream baip = new ByteArrayInputStream((byte[]) queryResult.getObject("completionCriteria"));
		AdventureCompletionCriteria completionCriteria = null;
		try
		{
			Object x = new ObjectInputStream(baip).readObject();
			completionCriteria = completionCriteriaClass.cast(x);
		}
		catch (ClassNotFoundException | IOException e)
		{
			throw new DatabaseException("Couldn't convert blob to completion criteria ", e);
		}
		return completionCriteria;
	}

	/**
	 * @see datasource.AdventureTableDataGateway#findAdventuresCompletedForMapLocation(String,
	 *      Position)
	 */
	@Override
	public ArrayList<AdventureRecord> findAdventuresCompletedForMapLocation(String mapName, Position pos) throws DatabaseException
	{
		ArrayList<AdventureRecord> results = new ArrayList<>();

		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement( "SELECT * FROM Adventures WHERE completionType = ?");
			stmt.setInt(1, AdventureCompletionType.MOVEMENT.getID());
			ResultSet queryResult = stmt.executeQuery();

			while (queryResult.next())
			{
				AdventureRecord rec = buildAdventureRecord(queryResult);
				GameLocationDTO thisLocation = (GameLocationDTO) rec.getCompletionCriteria();
				if (thisLocation.getPosition().equals(pos) && thisLocation.getMapName().equals(mapName))
				{
					results.add(rec);
				}
			}
			return results;
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find adventures for location at " + mapName + " " + pos.toString(), e);
		}
	}

	/**
	 * @see datasource.AdventureTableDataGateway#getNextAdventureID(int)
	 */
	@Override
	public int getNextAdventureID(int questID) throws DatabaseException
	{
		return getAdventuresForQuest(questID).size() + 1;
	}

	/**
	 * @see datasource.AdventureTableDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
	}

}

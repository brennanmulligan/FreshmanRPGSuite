package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import criteria.AdventureCompletionCriteria;
import dataENUM.AdventureCompletionType;

/**
 * An RDS implementation of AdventureRowDataGateway.
 */
public class AdventureRowDataGatewayRDS implements AdventureRowDataGateway
{
	private Connection connection;
	private int adventureId;
	private String adventureDescription;
	private int questId;
	private int experiencePointsGained;
	private AdventureCompletionType adventureCompletionType;
	private AdventureCompletionCriteria adventureCompletionCriteria;

	/**
	 * Construct and initialize a new AdventureRowDataGatewayRDS. (Finder)
	 *
	 * @param questId - quest ID adventure belongs to
	 * @param adventureId - ID of this adventure
	 * @throws DatabaseException - if adventure not found
	 */
	public AdventureRowDataGatewayRDS(int questId, int adventureId) throws DatabaseException
	{
		this.adventureId = adventureId;
		this.questId = questId;
		this.connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * FROM Adventures WHERE adventureID = ? and questID = ?");
			stmt.setInt(1, adventureId);
			stmt.setInt(2, questId);
			ResultSet result = stmt.executeQuery();
			result.next();
			this.adventureDescription = result.getString("adventureDescription");
			this.questId = result.getInt("questID");
			this.experiencePointsGained = result.getInt("experiencePointsGained");
			this.adventureCompletionType =
					AdventureCompletionType.findByID(result.getInt("completionType"));
			this.adventureCompletionCriteria =
					AdventureTableDataGatewayRDS.extractCompletionCriteria(result,
							this.adventureCompletionType);

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find an adventure with ID " + this.adventureId, e);
		}
	}

	/**
	 * Construct and initialize a new AdventureRowDataGatewayRDS with an adventure ID.
	 * (Creator)
	 *
	 * @param adventureId - ID of this adventure
	 * @param adventureDescription - description for adventure
	 * @param questId - quest ID adventure belongs to
	 * @param experiencePointsGained - experience points gained upon completion of adventure
	 * @param adventureCompletionType - adventure completion type
	 * @param adventureCompletionCriteria - adventure completion criteria
	 * @throws DatabaseException - if we can't talk to RDS server
	 */
	public AdventureRowDataGatewayRDS(int adventureId, String adventureDescription, int questId,
									  int experiencePointsGained, AdventureCompletionType adventureCompletionType,
									  AdventureCompletionCriteria adventureCompletionCriteria) throws DatabaseException
	{
		this.connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"Insert INTO Adventures SET adventureID = ?, adventureDescription = ?, questID = ?,"
							+ "experiencePointsGained = ?, completionType = ?, completionCriteria = ?");
			stmt.setInt(1, adventureId);
			stmt.setString(2, adventureDescription);
			stmt.setInt(3, questId);
			stmt.setInt(4, experiencePointsGained);
			stmt.setInt(5, adventureCompletionType.getID());
			stmt.setObject(6, adventureCompletionCriteria);
			stmt.executeUpdate();
			this.adventureId = adventureId;
			this.adventureDescription = adventureDescription;
			this.questId = questId;
			this.experiencePointsGained = experiencePointsGained;
			this.adventureCompletionType = adventureCompletionType;
			this.adventureCompletionCriteria = adventureCompletionCriteria;

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't create and adventure record for adventure with "
					+ "adventure ID of " + adventureId + " and quest ID of " + questId, e);
		}
	}

	/**
	 * Construct and initialize a new AdventureRowDataGatewayRDS. (Creator)
	 *
	 * @param adventureDescription - description for adventure
	 * @param questId - quest ID adventure belongs to
	 * @param experiencePointsGained - experience points gained upon completion of adventure
	 * @param adventureCompletionType - adventure completion type
	 * @param adventureCompletionCriteria - adventure completion criteria
	 * @throws DatabaseException - if we can't talk to RDS server
	 */
	public AdventureRowDataGatewayRDS(String adventureDescription, int questId,
									  int experiencePointsGained, AdventureCompletionType adventureCompletionType,
									  AdventureCompletionCriteria adventureCompletionCriteria) throws DatabaseException
	{
		this(AdventureTableDataGatewayRDS.getSingleton().getNextAdventureID(questId),
				adventureDescription, questId, experiencePointsGained, adventureCompletionType,
				adventureCompletionCriteria);
	}

	/**
	 * @see datasource.AdventureRowDataGateway#getAdventureId()
	 */
	public int getAdventureId()
	{
		return adventureId;
	}

	/**
	 * @see datasource.AdventureRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		// do nothing
	}

	/**
	 * @see datasource.AdventureRowDataGateway#getAdventureDescription()
	 */
	@Override
	public String getAdventureDescription()
	{
		return adventureDescription;
	}

	/**
	 * @see datasource.AdventureRowDataGateway#getQuestID()
	 */
	@Override
	public int getQuestID()
	{
		return questId;
	}

	/**
	 * @see datasource.AdventureRowDataGateway#getExperiencePointsGained()
	 */
	@Override
	public int getExperiencePointsGained()
	{
		return experiencePointsGained;
	}

	/**
	 * @see datasource.AdventureRowDataGateway#getCompletionType()
	 */
	@Override
	public AdventureCompletionType getCompletionType()
	{
		return adventureCompletionType;
	}

	/**
	 * @see datasource.AdventureRowDataGateway#getCompletionCriteria()
	 */
	@Override
	public AdventureCompletionCriteria getCompletionCriteria()
	{
		return adventureCompletionCriteria;
	}

	/**
	 * @see datasource.AdventureRowDataGateway#removeAdventure()
	 */
	@Override
	public void removeAdventure() throws DatabaseException
	{
		try
		{
			this.connection = DatabaseManager.getSingleton().getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"DELETE from Adventures where questID = ? and adventureID = ?");
			stmt.setInt(1, questId);
			stmt.setInt(2, adventureId);
			stmt.execute();
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to remove adventure with ID " + this.adventureId, e);
		}
	}

	/**
	 * @see datasource.PlayerRowDataGateway#persist()
	 */
	@Override
	public void persist() throws DatabaseException
	{

		this.connection = DatabaseManager.getSingleton().getConnection();

		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"UPDATE Adventures SET adventureDescription = ?,"
							+ "experiencePointsGained = ?, completionType = ?, completionCriteria = ? WHERE adventureID = ? AND questID = ?");
			stmt.setString(1, adventureDescription);
			stmt.setInt(2, experiencePointsGained);
			stmt.setInt(3, adventureCompletionType.getID());
			stmt.setObject(4, adventureCompletionCriteria);
			stmt.setInt(5, adventureId);
			stmt.setInt(6, questId);
			stmt.executeUpdate();

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't update the adventure record for adventure with "
					+ "adventure ID of " + adventureId + " and quest ID of " + questId, e);
		}

	}

	/**
	 * @see datasource.AdventureRowDataGateway#setAdventureId(int)
	 */
	@Override
	public void setAdventureId(int id)
	{
		this.adventureId = id;

	}

	/**
	 * @see datasource.AdventureRowDataGateway#setAdventureDescription(java.lang.String)
	 */
	@Override
	public void setAdventureDescription(String description)
	{
		this.adventureDescription = description;

	}

	/**
	 * @see datasource.AdventureRowDataGateway#setQuestID(int)
	 */
	@Override
	public void setQuestID(int id)
	{
		this.questId = id;
	}

	/**
	 * @see datasource.AdventureRowDataGateway#setExperiencePointsGained(int)
	 */
	@Override
	public void setExperiencePointsGained(int exp)
	{
		this.experiencePointsGained = exp;

	}

	/**
	 * @see datasource.AdventureRowDataGateway#setCompletionType(dataENUM.AdventureCompletionType)
	 */
	@Override
	public void setCompletionType(AdventureCompletionType completionType)
	{
		this.adventureCompletionType = completionType;
	}

	/**
	 * @see datasource.AdventureRowDataGateway#setCompletionCriteria(criteria.AdventureCompletionCriteria)
	 */
	@Override
	public void setCompletionCriteria(AdventureCompletionCriteria criteria)
	{
		this.adventureCompletionCriteria = criteria;
	}

}

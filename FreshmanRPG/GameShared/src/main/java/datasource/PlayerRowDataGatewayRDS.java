package datasource;

import java.sql.*;

import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;

/**
 * The RDS version of the gateway
 *
 * @author Merlin
 *
 */
public class PlayerRowDataGatewayRDS implements PlayerRowDataGateway
{

	/**
	 * Drop the table if it exists and re-create it empty
	 *
	 * @throws DatabaseException shouldn't
	 */
	public static void createTable() throws DatabaseException
	{
		String dropSql = "DROP TABLE IF EXISTS VisitedMaps, PlayerConnection,PlayerLogins,NPCs,Players";
		String playerCreationSql = "CREATE TABLE Players ("
				+ "playerID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ "appearanceType VARCHAR(255),"
				+ "quizScore INTEGER,"
				+ "experiencePoints INTEGER,"
				+ "crew INTEGER NOT NULL, major INTEGER NOT NULL,"
				+ "section INTEGER NOT NULL, "
				+ "buffPool INT,"
				+ "online BOOL)";

		Connection connection = DatabaseManager.getSingleton().getConnection();

		try
		{
			PreparedStatement stmt = connection.prepareStatement(dropSql);
			stmt.execute();
			stmt.close();

			stmt = connection.prepareStatement(playerCreationSql);
			stmt.execute();
			stmt.close();
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the player table", e);
		}
	}

	private int playerID;
	private String appearanceType;
	private int quizScore;
	private int experiencePoints;

	private Connection connection;
	private Crew crew;
	private Major major;
	private int section;

	private int buffPool;
	private boolean online;

	/**
	 * finder constructor
	 *
	 * @param playerID the unique ID of the player we are working with
	 * @throws DatabaseException if we cannot find that player in the database
	 */
	public PlayerRowDataGatewayRDS(int playerID) throws DatabaseException
	{
		this.playerID = playerID;
		this.connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement(
					"SELECT * FROM Players WHERE playerID = ?");
			stmt.setInt(1, playerID);
			ResultSet result = stmt.executeQuery();
			result.next();
			this.appearanceType = result.getString("appearanceType");
			this.quizScore = result.getInt("quizScore");
			this.experiencePoints = result.getInt("experiencePoints");
			this.crew = Crew.getCrewForID(result.getInt("crew"));
			this.major = Major.getMajorForID(result.getInt("major"));
			this.section = result.getInt("section");
			this.buffPool = result.getInt("buffPool");
			this.online = result.getBoolean("online");

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find a player with ID " + playerID, e);
		}
	}

	/**
	 * create constructor
	 *
	 * @param appearanceType the appearance type this player should be rendered
	 *            with
	 * @param quizScore this player's current quiz score
	 * @param experiencePoints this player's experience points
	 * @param crew the crew to which this player belongs
	 * @param major the major of this player
	 * @param section the section the player is in
	 * @param buffPool TODO
	 * @throws DatabaseException shouldn't
	 */
	public PlayerRowDataGatewayRDS(String appearanceType, int quizScore, int experiencePoints,
								   Crew crew, Major major, int section, int buffPool, boolean online) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement(
					"Insert INTO Players SET appearanceType = ?, quizScore = ?, experiencePoints = ?, crew = ?, major = ?, section = ?, buffPool = ?, online = ?",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, appearanceType);
			stmt.setInt(2, quizScore);
			stmt.setInt(3, experiencePoints);
			stmt.setInt(4, crew.getID());
			stmt.setInt(5, major.getID());
			stmt.setInt(6, section);
			stmt.setInt(7, buffPool);
			stmt.setBoolean(8, online);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next())
			{
				playerID = rs.getInt(1);
				this.appearanceType = appearanceType;
				this.quizScore = quizScore;
				this.experiencePoints = experiencePoints;

				this.connection = connection;
				this.crew = crew;
				this.major = major;
				this.section = section;

				this.buffPool = 0;
				this.online = online;
			}
			else
			{
				throw new DatabaseException("Generated key for player not found.");
			}

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't create a player record for player with ID " + playerID, e);
		}
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getAppearanceType()
	 */
	@Override
	public String getAppearanceType()
	{
		return appearanceType;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getCrew()
	 */
	@Override
	public Crew getCrew()
	{
		return crew;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getExperiencePoints()
	 */
	@Override
	public int getExperiencePoints()
	{
		return experiencePoints;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getPlayerID()
	 */
	@Override
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getQuizScore()
	 */
	@Override
	public int getQuizScore()
	{
		return quizScore;
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
			PreparedStatement stmt =  connection.prepareStatement(
					"UPDATE Players SET appearanceType = ?, quizScore = ?, experiencePoints = ?, crew = ?, major = ?, section = ?, buffPool = ?, online = ? WHERE playerID = ?");
			stmt.setString(1, appearanceType);
			stmt.setInt(2, quizScore);
			stmt.setInt(3, experiencePoints);
			stmt.setInt(4, crew.getID());
			stmt.setInt(5, major.getID());
			stmt.setInt(6, section);
			stmt.setInt(7, this.buffPool);
			stmt.setBoolean(8, online);
			stmt.setInt(9, playerID);
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{

			throw new DatabaseException("Couldn't persist info for player with ID " + playerID, e);
		}

	}

	/**
	 * @see datasource.PlayerRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setAppearanceType(java.lang.String)
	 */
	@Override
	public void setAppearanceType(String appearanceType)
	{
		this.appearanceType = appearanceType;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setCrew(datatypes.Crew)
	 */
	public void setCrew(Crew crew)
	{
		this.crew = crew;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setExperiencePoints(int)
	 */
	@Override
	public void setExperiencePoints(int experiencePoints)
	{
		this.experiencePoints = experiencePoints;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setQuizScore(int)
	 */
	@Override
	public void setQuizScore(int quizScore)
	{
		this.quizScore = quizScore;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getMajor()
	 */
	@Override
	public Major getMajor()
	{
		return major;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setMajor(datatypes.Major)
	 */
	@Override
	public void setMajor(Major major)
	{
		this.major = major;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getSection()
	 */
	@Override
	public int getSection()
	{
		return section;
	}

	/**
	 *
	 * @see datasource.PlayerRowDataGateway#setSection(int)
	 */
	@Override
	public void setSection(int section)
	{
		this.section = section;
	}

	/**
	 * Delete the player this instance represents
	 */
	@Override
	public void delete() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement(
					"DELETE FROM Players WHERE Players.playerID = ?");
			stmt.setInt(1, this.playerID);
			stmt.execute();
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't delete player", e);
		}
	}


	/**
	 * gets buffPool
	 * @return buffPool
	 */
	public int getBuffPool()
	{
		return this.buffPool;
	}

	/**
	 * sets buffPool
	 * @param buffPool the new buffPool
	 */
	public void setBuffPool(int buffPool)
	{
		this.buffPool = buffPool;
	}

	/**
	 * gets online status
	 * @return online
	 */
	public boolean getOnline()
	{
		return this.online;
	}

	/**
	 * sets online status
	 * @param online
	 */
	public void setOnline(boolean online)
	{
		this.online = online;
	}
}

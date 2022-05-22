package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dataDTO.PlayerDTO;
import datatypes.Crew;
import datatypes.Major;
import datatypes.PlayerScoreRecord;
import datatypes.Position;


/**
 * The RDS implementation of the gateway
 *
 * @author Merlin
 *
 */
public class PlayerTableDataGatewayRDS implements PlayerTableDataGateway
{

	static TableDataGateway getGateway()
	{
		return new PlayerTableDataGatewayRDS();
	}
	/**
	 * A private constructor only called by the getSingleton method
	 */
	private PlayerTableDataGatewayRDS()
	{
		//do nothing this just explicitly makes it private
	}


	public void resetTableGateway()
	{
		// Nothing required
	}

	/**
	 * @see datasource.PlayerTableDataGateway#getTopTenList()
	 */
	@Override
	public ArrayList<PlayerScoreRecord> getTopTenList() throws DatabaseException
	{
		ArrayList<PlayerScoreRecord> resultList = new ArrayList<>();
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement(
					"SELECT experiencePoints, playerName, crew, section FROM Players, PlayerLogins where Players.playerID = PlayerLogins.playerID ORDER BY experiencePoints desc limit 10");
			ResultSet result = stmt.executeQuery();

			while (result.next())
			{
				int crewID = result.getInt("crew");
				PlayerScoreRecord rec = new PlayerScoreRecord(result.getString("playerName"),
						result.getInt("experiencePoints"), Crew.getCrewForID(crewID).toString(), result.getInt("section"));
				resultList.add(rec);
			}
			return resultList;
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find the top ten players", e);
		}
	}

	/**
	 * @see datasource.PlayerTableDataGateway#getTopTenList()
	 */
	@Override
	public ArrayList<PlayerScoreRecord> getHighScoreList() throws DatabaseException
	{
		ArrayList<PlayerScoreRecord> resultList = new ArrayList<>();
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement( "SELECT experiencePoints, playerName, crew, section FROM "
					+ "Players NATURAL JOIN PlayerLogins ORDER BY Players.experiencePoints DESC");
			ResultSet result = stmt.executeQuery();

			while (result.next())
			{
				int crewID = result.getInt("crew");
				PlayerScoreRecord rec = new PlayerScoreRecord(result.getString("playerName"),
						result.getInt("experiencePoints"), Crew.getCrewForID(crewID).toString(), result.getInt("section"));
				resultList.add(rec);
			}
			return resultList;
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find the high score list", e);
		}
	}

	/**
	 *
	 * @throws DatabaseException shouldn't
	 * @see datasource.PlayerTableDataGateway#retrieveAllPlayers()
	 */
	@Override
	public ArrayList<PlayerDTO> retrieveAllPlayers() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();

		try
		{
			return processResultSetToPlayerDTO( connection.prepareStatement( "SELECT * FROM Players INNER JOIN PlayerLogins ON "
					+ "Players.playerID = PlayerLogins.playerID INNER JOIN PlayerConnection ON "
					+ "Players.playerID = PlayerConnection.playerID "
					+ ""));
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Could not retrieve all players!", e);
		}
	}

	/**
	 * retrieve all online players from the database and return them in a list of dtos
	 * @return resultList the list of dtos
	 */
	@Override
	public ArrayList<PlayerDTO> retrieveAllOnlinePlayers() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();

		try
		{
			return processResultSetToPlayerDTO( connection.prepareStatement( "SELECT * FROM Players INNER JOIN PlayerLogins ON "
					+ "Players.playerID = PlayerLogins.playerID INNER JOIN PlayerConnection ON "
					+ "Players.playerID = PlayerConnection.playerID Where Players.online = true"));
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Could not retrieve all players!", e);
		}

	}

	/**
	 * process a result and turn the results into a list of playerDTOs
	 * @param stmt the sql query
	 * @return list player DTOs
	 * @throws DatabaseException if we can't talk to the db
	 * TODO: Get real vanity items from database
	 */
	private ArrayList<PlayerDTO> processResultSetToPlayerDTO(PreparedStatement stmt) throws DatabaseException
	{
		ArrayList<PlayerDTO> resultList = new ArrayList<>();

		try
		{
			ResultSet result = stmt.executeQuery();

			while (result.next())
			{
				int playerID = result.getInt("playerID");

				int crewNumber = result.getInt("crew");
				int majorNumber = result.getInt("major");
				String appearance = result.getString("appearanceType");
				String playerName = result.getString("playerName");
				String playerPassword = result.getString("password");
				int xPos = result.getInt("playerRow");
				int yPos = result.getInt("playerCol");
				Position position = new Position(xPos, yPos);
				String map = result.getString("mapName");
				int experiencePoints = result.getInt("experiencePoints");
				int section = result.getInt("section");
				int quizScore = result.getInt("quizScore");

				VisitedMapsGatewayRDS gateway = new VisitedMapsGatewayRDS(playerID);
				ArrayList<String> maps = gateway.getMaps();

				PlayerDTO playerInfo = new PlayerDTO(playerID, playerName, playerPassword,
						appearance, quizScore, position, map, experiencePoints,
						Crew.getCrewForID(crewNumber), Major.getMajorForID(majorNumber),
						section, maps, new ArrayList<>());
				resultList.add(playerInfo);
			}
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Could not retrieve all players!", e);
		}

		return resultList;
	}
}

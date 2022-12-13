package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.criteria.GameLocationDTO;
import edu.ship.engr.shipsim.dataDTO.PlayerDTO;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.PlayerScoreRecord;
import edu.ship.engr.shipsim.datatypes.Position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * The RDS implementation of the gateway
 *
 * @author Merlin
 */
public class PlayerTableDataGateway
{
    /**
     * the game location where new players should start
     */
    public static final GameLocationDTO
            INITIAL_GAME_LOCATION =
            new GameLocationDTO("StartingRoom.tmx", new Position(2, 32));
    private static PlayerTableDataGateway singleton;

    /**
     * A private constructor only called by the getSingleton method
     */
    private PlayerTableDataGateway()
    {
        //do nothing this just explicitly makes it private
    }

    public static PlayerTableDataGateway getSingleton()
    {
        if (singleton == null)
        {
            singleton = new PlayerTableDataGateway();
        }
        return singleton;
    }

    public ArrayList<PlayerScoreRecord> getHighScoreList() throws DatabaseException
    {
        ArrayList<PlayerScoreRecord> resultList = new ArrayList<>();
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT experiencePoints, playerName, crew, section FROM " +
                        "Players NATURAL JOIN PlayerLogins ORDER BY Players.experiencePoints DESC");
             ResultSet result = stmt.executeQuery())
        {
            while (result.next())
            {
                int crewID = result.getInt("crew");
                PlayerScoreRecord rec =
                        new PlayerScoreRecord(result.getString("playerName"),
                                result.getInt("experiencePoints"),
                                Crew.getCrewForID(crewID).toString(),
                                result.getInt("section"));
                resultList.add(rec);
            }
            return resultList;
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't find the high score list", e);
        }
    }

    public ArrayList<PlayerScoreRecord> getTopTenList() throws DatabaseException
    {
        ArrayList<PlayerScoreRecord> resultList = new ArrayList<>();
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT experiencePoints, playerName, crew, section FROM Players, PlayerLogins where Players.playerID = PlayerLogins.playerID ORDER BY experiencePoints desc limit 10");
             ResultSet result = stmt.executeQuery())
        {
            while (result.next())
            {
                int crewID = result.getInt("crew");
                PlayerScoreRecord rec =
                        new PlayerScoreRecord(result.getString("playerName"),
                                result.getInt("experiencePoints"),
                                Crew.getCrewForID(crewID).toString(),
                                result.getInt("section"));
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
     * retrieve all online players from the database and return them in a list of dtos
     *
     * @return resultList the list of dtos
     */
    public ArrayList<PlayerDTO> retrieveAllOnlinePlayers() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(
                "SELECT * FROM Players INNER JOIN PlayerLogins ON "
                        +
                        "Players.playerID = PlayerLogins.playerID INNER JOIN PlayerConnection ON "
                        +
                        "Players.playerID = PlayerConnection.playerID Where Players.online = true"))
        {
            return processResultSetToPlayerDTO(pstm);
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not retrieve all players!", e);
        }

    }

    public ArrayList<PlayerDTO> retrieveAllPlayers() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(
                "SELECT * FROM Players INNER JOIN PlayerLogins ON "
                        +
                        "Players.playerID = PlayerLogins.playerID INNER JOIN PlayerConnection ON "
                        + "Players.playerID = PlayerConnection.playerID "
                        + ""))
        {
            return processResultSetToPlayerDTO(pstm);
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not retrieve all players!", e);
        }
    }

    /**
     * process a result and turn the results into a list of playerDTOs
     *
     * @param stmt the sql query
     * @return list player DTOs
     * @throws DatabaseException if we can't talk to the db
     */
    private ArrayList<PlayerDTO> processResultSetToPlayerDTO(PreparedStatement stmt)
            throws DatabaseException
    {
        ArrayList<PlayerDTO> resultList = new ArrayList<>();

        try (ResultSet result = stmt.executeQuery())
        {
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

                VisitedMapsGateway gateway = new VisitedMapsGateway(playerID);
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

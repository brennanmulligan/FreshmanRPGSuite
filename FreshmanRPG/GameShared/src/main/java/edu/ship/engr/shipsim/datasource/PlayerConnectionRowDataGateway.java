package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.datatypes.Position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * AN RDS Implementation of PlayerConnectionRowDataGateway
 *
 * @author Merlin
 */
public class PlayerConnectionRowDataGateway
{

    private final int playerID;
    private int pin;
    private String changedOn;
    private String mapName;
    private Position position;

    /**
     * Drop and reinitialize the table this gateway manages
     *
     * @throws DatabaseException if we can't talk to the RDS
     */
    public static void createPlayerConnectionTable() throws DatabaseException
    {
        String dropSql = "DROP Table IF EXISTS PlayerConnection";
        String createConnectionTableSql = "CREATE Table PlayerConnection ("
                + "playerID INT NOT NULL,"
                + "PRIMARY KEY (playerID),"
                + "FOREIGN KEY (playerID) REFERENCES Players(playerID) ON DELETE CASCADE, "
                + "Pin DOUBLE NOT NULL,"
                + "changed_on timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
                + "mapName VARCHAR(30) DEFAULT NULL,"
                + "playerRow int(11) DEFAULT NULL, "
                + "playerCol int(11) DEFAULT NULL)";

        Connection connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(dropSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to drop PlayerConnection table", e);
        }

        try (PreparedStatement stmt = connection.prepareStatement("Drop View IF EXISTS PlayerConnection"))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to drop PlayerConnection view", e);
        }

        try (PreparedStatement stmt = connection.prepareStatement(createConnectionTableSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create PlayerConnection table", e);
        }
    }

    /**
     * create constructor
     *
     * @param pin      - pin of player
     * @param mapName  - name of the map the player is on
     * @param position - position of the player
     * @throws DatabaseException shouldn't
     */
    public PlayerConnectionRowDataGateway(int playerID, int pin, String mapName, Position position) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(
                "Insert INTO PlayerConnection SET PlayerID = ?,Pin = ?, mapName = ?," +
                        "playerRow = ?, playerCol = ?",
                Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setInt(1, playerID);
            stmt.setInt(2, pin);
            stmt.setString(3, mapName);
            stmt.setInt(4, position.getRow());
            stmt.setInt(5, position.getColumn());
            stmt.executeUpdate();

            this.playerID = playerID;
            this.pin = pin;
            this.mapName = mapName;
            this.position = position;

            fetchChangedOn();

        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't create a player record for player with ID " + playerID, e);
        }
    }

    private void fetchChangedOn() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        String sql = "SELECT changed_on FROM PlayerConnection WHERE playerID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, playerID);

            try (ResultSet resultSet = stmt.executeQuery())
            {
                resultSet.next();
                changedOn = resultSet.getString("changed_on");
            }

        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to get the record information from PlayerConnection for playerID: " + playerID, e);
        }
    }

    /**
     * Finder constructor
     *
     * @param playerID the player we are interested in
     * @throws DatabaseException if we cannot find that player in the db
     */
    public PlayerConnectionRowDataGateway(int playerID) throws DatabaseException
    {
        this.playerID = playerID;
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PlayerConnection WHERE playerID = ?"))
        {
            stmt.setInt(1, playerID);

            try (ResultSet resultSet = stmt.executeQuery())
            {
                resultSet.next();
                pin = resultSet.getInt("Pin");
                changedOn = resultSet.getString("changed_on");
                mapName = resultSet.getString("mapName");
                position = new Position(resultSet.getInt("playerRow"), resultSet.getInt(
                        "playerCol"));
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to get the record information from PlayerConnection for playerID: " + playerID, e);
        }
    }

    public void deleteRow() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();

        String sql = "DELETE from PlayerConnection WHERE playerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, playerID);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to delete the record in PlayerConnection for playerId:" + playerID, e);
        }
    }

    public String getChangedOn()
    {
        return changedOn;
    }

    public String getMapName()
    {
        return mapName;
    }

    public int getPin()
    {
        return pin;
    }

    public void setChangedOn(String newTime) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        String sql = "UPDATE PlayerConnection SET changed_on = ? WHERE playerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, newTime);
            stmt.setInt(2, playerID);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to update PlayerConnection column changed_on for player id # " + playerID, e);
        }
        this.changedOn = newTime;
    }

    public void storeMapName(String mapFileTitle) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        String sql = "UPDATE PlayerConnection SET mapName=? WHERE playerID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, mapFileTitle);
            stmt.setInt(2, playerID);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to store map information for player id # " + playerID, e);
        }
        this.mapName = mapFileTitle;
    }

    public void storePin(int pin) throws DatabaseException
    {
        this.pin = pin;
        Connection connection = DatabaseManager.getSingleton().getConnection();
        String sql = "UPDATE PlayerConnection SET Pin= ? where playerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(2, playerID);
            stmt.setInt(1, pin);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to generate pin for player id # " + playerID, e);
        }
    }

    public Position getPosition()
    {
        return position;
    }

    public void storePosition(Position position) throws DatabaseException
    {
        this.position = position;
        Connection connection = DatabaseManager.getSingleton().getConnection();
        String sql = "UPDATE PlayerConnection SET playerRow = ?, playerCol = ? where playerID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(3, playerID);
            stmt.setInt(1, position.getRow());
            stmt.setInt(2, position.getColumn());
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to store position for player id # " + playerID, e);
        }
    }

}

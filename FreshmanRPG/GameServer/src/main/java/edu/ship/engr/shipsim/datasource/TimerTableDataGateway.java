package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.TimerDTO;
import edu.ship.engr.shipsim.model.Command;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;

import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class TimerTableDataGateway
{
    /**
     * Drops the table and recreates it.
     *
     * @throws DatabaseException shouldn't
     */
    public static void createTable() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();

        String dropSql = "DROP TABLE IF EXISTS Timers";
        String createSql = "CREATE TABLE Timers(" +
                "timerID INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                "endsAt TIMESTAMP NOT NULL," +
                "command BLOB NOT NULL," +
                "playerID INT)";

        try (PreparedStatement dropStatement = connection.prepareStatement(dropSql))
        {
            dropStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to drop Timers table", e);
        }

        try (PreparedStatement createStatement = connection.prepareStatement(createSql))
        {
            createStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create Timers table", e);
        }
    }

    /**
     * Creates a timer row, for a non-player based timer.
     * @param endsAt The date object for which we want the timer to trigger at
     * @param command The command that we want to execute when the timer goes off
     * @return The ID of the timer that was created
     */
    public static int createRow(Date endsAt, Command command)
            throws DatabaseException
    {
        int generatedID;
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO Timers (endsAt, command) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setTimestamp(1, new Timestamp(endsAt.getTime()));
            stmt.setObject(2, command);

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys())
            {
                generatedKeys.next();
                generatedID = generatedKeys.getInt(1);
                generatedKeys.close();
                return generatedID;
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Couldn't create a timer record"
            );
        }
    }

    /**
     * Creates a timer row for a player oriented timer
     * @param endsAt the date object for which we want the timer to be triggered at
     * @param command the command that we want to execute when the timer goes off
     * @param playerID the ID of the player who the timer is associated with
     * @return the newly created timer id
     */
    public static int createRow(Date endsAt, Command command, int playerID)
            throws DatabaseException
    {
        int generatedID;
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO Timers (endsAt, command, playerID) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setTimestamp(1, new Timestamp(endsAt.getTime()));
            stmt.setObject(2, command);
            stmt.setInt(3, playerID);

            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys())
            {
                generatedKeys.next();
                generatedID = generatedKeys.getInt(1);
                generatedKeys.close();
                return generatedID;
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Couldn't create a timer record, " + e
            );
        }

    }

    /**
     * Gets all timers for the given player.
     * @param playerID the id of the player we are fetching the timers for
     * @return an array of {@link TimerDTO}'s that belong to the player given
     */
    public static ArrayList<TimerDTO> getPlayerTimers(int playerID) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Timers WHERE playerID = ?"))
        {
            stmt.setInt(1, playerID);

            try (ResultSet queryResults = stmt.executeQuery())
            {
                return parseTimers(queryResults);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Unable to retrieve timers for player (" + playerID + ")"
            );
        }
    }

    /**
     * Gets all non-player timers
     * @return an array of {@link TimerDTO}'s that do not belong to any players.
     */
    public static ArrayList<TimerDTO> getNonPlayerTimers() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Timers WHERE playerID IS NULL"))
        {
            try (ResultSet queryResults = stmt.executeQuery())
            {
                return parseTimers(queryResults);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to retrieve non-player timers.");
        }
    }

    /**
     * Gets the timer by its id
     * @param timerID the id of the timer
     * @return the timer object that exists for that id
     */
    public static TimerDTO getTimerByID(int timerID)
            throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Timers WHERE timerID = ?"))
        {
            stmt.setInt(1, timerID);

            ResultSet queryResults = stmt.executeQuery();

            if (queryResults.next())
            {
                return buildTimerDTO(queryResults);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to get timer from id " + e);
        }
        return null;
    }

    /**
     * Deletes the timer.
     * @param timerID the id of the timer we want to delete.
     */
    public static void deleteTimer(long timerID) throws DatabaseException
    {
        try
        {
            DatabaseManager.getSingleton()
                    .executeUpdate("DELETE FROM Timers WHERE timerID = ?",
                            timerID);
        }
        catch (DatabaseException e)
        {
            throw new DatabaseException("Unable to delete timer (" + timerID + ")");
        }
    }

    /**
     * Given a set of query results, parse them into the dto's
     * @param queryResults a list of results of a select query from the timer table
     * @return a list of {@link TimerDTO}'s that were in the query results
     */
    private static ArrayList<TimerDTO> parseTimers(ResultSet queryResults)
            throws SQLException, DatabaseException
    {
        ArrayList<TimerDTO> results = new ArrayList<>();
        while (queryResults.next())
        {
            TimerDTO dto = buildTimerDTO(queryResults);
            results.add(dto);
        }
        return results;
    }

    /**
     * Compiles a single timer row into a TimerDTO
     * @param queryResults a query result row from the Timers table
     * @return a single timer dto that was parsed from a queryResults
     */
    private static TimerDTO buildTimerDTO(ResultSet queryResults) throws DatabaseException
    {
        try
        {
            Command command;
            ByteArrayInputStream baip = new ByteArrayInputStream((byte[]) queryResults.getObject("command"));
            try (ObjectInputStream ois = new ObjectInputStream(baip))
            {
                Object x = ois.readObject();
                command = (Command) x;
            }
            catch (IOException | ClassNotFoundException e)
            {
                throw new RuntimeException(e);
            }

            return new TimerDTO(
                    queryResults.getInt("timerID"),
                    new Date(queryResults.getTimestamp("endsAt").getTime()),
                    command,
                    queryResults.getInt("playerID")
            );
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method for testing only to reset table in between tests
     * @throws DatabaseException should not
     */
    protected static void rollback() throws DatabaseException
    {
        try
        {
            DatabaseManager.getSingleton()
                    .executeUpdate("DELETE FROM Timers WHERE timerID > 0");
            DatabaseManager.getSingleton().executeUpdate("ALTER TABLE Timers AUTO_INCREMENT = 1");
        }
        catch (DatabaseException e)
        {
            throw new DatabaseException("Error rolling back Timers table.");
        }
    }
}

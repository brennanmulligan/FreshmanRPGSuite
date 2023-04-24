package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.TimerDTO;
import edu.ship.engr.shipsim.model.Command;
import org.jetbrains.annotations.TestOnly;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                "endsAt TIMESTAMP NOT NULL," +
                "command BLOB NOT NULL," +
                "playerID INT NOT NULL)";

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
     * Creates a timer row for a player oriented timer
     * @param endsAt the date object for which we want the timer to be triggered at
     * @param command the command that we want to execute when the timer goes off
     * @param playerID the ID of the player who the timer is associated with
     */
    public static void createRow(Date endsAt, Command command, int playerID)
            throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO Timers (endsAt, command, playerID) VALUES (?, ?, ?)"))
        {
            stmt.setTimestamp(1, new Timestamp(normalizeDate(endsAt)));
            stmt.setObject(2, command);
            stmt.setInt(3, playerID);

            stmt.executeUpdate();
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
    public static ArrayList<TimerDTO> getAllPlayerTimers(int playerID) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement selectStmt = connection.prepareStatement("SELECT * FROM Timers WHERE playerID = ?"))
        {
            selectStmt.setInt(1, playerID);
            try (ResultSet queryResults = selectStmt.executeQuery())
            {
                deleteAllPlayerTimers(playerID);
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
     * Deletes all timers for the given player.
     * @param playerID the id of the player we are deleting the timers of
     */
    public static void deleteAllPlayerTimers(int playerID) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement deleteStmt = connection.prepareStatement("DELETE FROM Timers WHERE playerID = ?"))
        {
            deleteStmt.setInt(1, playerID);
            deleteStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Unable to delete timers for player (" + playerID + ")"
            );
        }
    }

    /**
     * Deletes all expired timers for a given player
     * @param playerID of the player
     */
    public static void deleteExpiredTimers(int playerID)
    {
        try
        {
            DatabaseManager.getSingleton()
                    .executeUpdate("DELETE FROM Timers WHERE playerID = ? AND endsAt <= NOW()",
                            playerID);
        }
        catch (DatabaseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Given a set of query results, parse them into the dto's
     * @param queryResults a list of results of a select query from the timer table
     * @return a list of {@link TimerDTO}'s that were in the query results
     */
    private static ArrayList<TimerDTO> parseTimers(ResultSet queryResults)
            throws SQLException
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
    private static TimerDTO buildTimerDTO(ResultSet queryResults)
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
     * Method that normalizes a date to match the format that it will be stored in the DB
     * @param endsAt the date to be normalized
     * @return the normalized time (long)
     */
    public static long normalizeDate(Date endsAt)
    {
        return (long) ((Math.floor(endsAt.getTime() / 1000)) * 1000);
    }

    /**
     * Method for testing only to reset table in between tests
     * @throws DatabaseException should not
     */
    @TestOnly
    public static void rollback() throws DatabaseException
    {
        try
        {
            DatabaseManager.getSingleton()
                    .executeUpdate("DELETE FROM Timers WHERE TRUE");

        }
        catch (DatabaseException e)
        {
            throw new DatabaseException("Error rolling back Timers table.");
        }
    }
}

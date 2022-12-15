package edu.ship.engr.shipsim.datasource;

import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The RDS implementation
 *
 * @author Merlin
 */
public class LevelTableDataGateway
{

    private static LevelTableDataGateway singleton;

    /**
     * A private constructor only called by the getSingleton method
     */
    private LevelTableDataGateway()
    {
    }

    /**
     * Dump any existing table and create a new one
     *
     * @throws DatabaseException if we can't create it
     */
    public static void createTable() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();

        @Language("MySQL")
        String dropSql = "DROP TABLE IF EXISTS Levels";

        @Language("MySQL")
        String createSql = "Create TABLE Levels (levelDescription VARCHAR(30) NOT NULL, levelUpPoints INT NOT NULL, levelUpMonth INT NOT NULL, levelUpDayOfMonth INT NOT NULL)";

        try (PreparedStatement stmt = connection.prepareStatement(dropSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to drop Levels table", e);
        }

        try (PreparedStatement stmt = connection.prepareStatement(createSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create Levels table", e);
        }
    }

    public static LevelTableDataGateway getSingleton()
    {
        if (singleton == null)
        {
            singleton = new LevelTableDataGateway();
        }
        return singleton;
    }

    /**
     * Add a new row to the table
     *
     * @param description       the description of the level
     * @param levelUpPoints     the number of points you need to get to the next
     *                          level
     * @param levelUpMonth      the month by which the players must get to the next
     *                          level
     * @param levelUpDayOfMonth the day of the month by which the player must
     *                          get to the next level
     * @throws DatabaseException if we can't talk to the db
     */
    public void createRow(String description, int levelUpPoints, int levelUpMonth,
                          int levelUpDayOfMonth)
            throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(
                "Insert INTO Levels SET levelDescription = ?, levelUpPoints = ?, levelUpMonth = ?, levelUpDayOfMonth = ?"))
        {
            stmt.setString(1, description);
            stmt.setInt(2, levelUpPoints);
            stmt.setInt(3, levelUpMonth);
            stmt.setInt(4, levelUpDayOfMonth);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Couldn't create a level record for level with description " +
                            description, e);
        }
    }

    public ArrayList<LevelRecord> getAllLevels() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Levels");
             ResultSet result = stmt.executeQuery())
        {
            ArrayList<LevelRecord> results = new ArrayList<>();
            while (result.next())
            {
                LevelRecord rec = new LevelRecord(result.getString("levelDescription"),
                        result.getInt("levelUpPoints"),
                        result.getInt("levelUpMonth"),
                        result.getInt("LevelUpDayOfMonth"));
                results.add(rec);
            }
            return results;
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't find levels", e);
        }
    }
}

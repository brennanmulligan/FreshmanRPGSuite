package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.RandomFactDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The RDS implementation
 *
 * @author Merlin
 */
public class RandomFactsTableDataGateway
{

    private static RandomFactsTableDataGateway singleton;

    /**
     * @return the one and only
     */
    public static synchronized RandomFactsTableDataGateway getSingleton()
    {
        if (singleton == null)
        {
            singleton = new RandomFactsTableDataGateway();
        }
        return singleton;
    }

    /**
     * A private constructor only called by the getSingleton method
     */
    private RandomFactsTableDataGateway()
    {
        // do nothing this just explicitly makes it private
    }

    /**
     * Dump any existing table and create a new one
     *
     * @throws DatabaseException if we can't create it
     */
    public static void createTable() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();

        String dropSql = "DROP TABLE IF EXISTS RandomFacts";
        String createSql = "Create TABLE RandomFacts (factID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, factText VARCHAR(80) NOT NULL, npcID INT NOT NULL, startDate DATE, endDate DATE)";

        try (PreparedStatement stmt = connection.prepareStatement(dropSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to drop RandomFacts table", e);
        }

        try (PreparedStatement stmt = connection.prepareStatement(createSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create RandomFacts table", e);
        }
    }

    /**
     * @param factText  the text of the fact we are adding
     * @param npcID     the NPC that should spout this fact
     * @param startDate The first date this fact should be spouted
     * @param endDate   the last date this fact should be spouted
     * @throws DatabaseException if we can't make the insertion
     */
    public static void createRow(String factText, int npcID, LocalDate startDate, LocalDate endDate) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(
                "Insert INTO RandomFacts SET factText = ?, npcID = ?, startDate = ?, endDate = ?"))
        {
            stmt.setString(1, factText);
            stmt.setInt(2, npcID);
            stmt.setDate(3, java.sql.Date.valueOf(startDate));
            stmt.setDate(4, java.sql.Date.valueOf(endDate));
            stmt.executeUpdate();

        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't create a random fact record for level with text " + factText, e);
        }
    }

    public ArrayList<RandomFactDTO> getAllFactsForNPC(int npcID) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM RandomFacts");
             ResultSet result = stmt.executeQuery())
        {

            ArrayList<RandomFactDTO> results = new ArrayList<>();
            while (result.next())
            {
                if (result.getInt("npcID") == npcID)
                {
                    RandomFactDTO rec = new RandomFactDTO(result.getInt("factID"), result.getInt("npcID"),
                            result.getString("factText"), result.getDate("startDate").toLocalDate(),
                            result.getDate("endDate").toLocalDate());
                    results.add(rec);
                }
            }
            return results;
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't find Random Facts", e);
        }
    }
}

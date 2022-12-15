package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.DoubloonPrizeDTO;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Mina Kindo, Christian Chroutamel, Andrew Stake
 */
public class DoubloonPrizesTableDataGateway
{


    private static DoubloonPrizesTableDataGateway singleton;

    public static void createTable() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();

        @Language("MySQL")
        String dropSql = "DROP TABLE IF EXISTS DoubloonPrizes";

        @Language("MySQL")
        String createSql = "CREATE TABLE DoubloonPrizes(name VARCHAR(30) NOT NULL, cost INT NOT NULL, description VARCHAR(200) NOT NULL)";

        try (PreparedStatement stmt = connection.prepareStatement(dropSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to drop DoubloonPrizes table", e);
        }

        try (PreparedStatement stmt = connection.prepareStatement(createSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create DoubloonPrizes table", e);
        }
    }

    public static DoubloonPrizesTableDataGateway getSingleton()
    {
        if (singleton == null)
        {
            singleton = new DoubloonPrizesTableDataGateway();
        }
        return singleton;
    }

    public void createRow(String name, int cost, String description)
            throws DatabaseException
    {
        String query =
                "INSERT INTO DoubloonPrizes " + "SET name = ?, cost = ?, description = ?";
        Connection connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, name);
            stmt.setInt(2, cost);
            stmt.setString(3, description);

            stmt.executeUpdate();

        }
        catch (SQLException e)
        {
            throw new DatabaseException("Row creation of doubloon prize not successful", e);
        }
    }

    public ArrayList<DoubloonPrizeDTO> getAllDoubloonPrizes() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM DoubloonPrizes;");
             ResultSet result = stmt.executeQuery())
        {
            ArrayList<DoubloonPrizeDTO> results = new ArrayList<>();
            while (result.next())
            {
                DoubloonPrizeDTO prize = new DoubloonPrizeDTO(result.getString("name"),
                        result.getInt("cost"),
                        result.getString("description"));
                results.add(prize);
            }
            return results;
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not find prizes");
        }
    }
}

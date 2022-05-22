package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The RDS implementation
 *
 * @author Merlin
 *
 */
public class LevelTableDataGatewayRDS implements LevelTableDataGateway
{

	static TableDataGateway getGateway()
	{
		return new LevelTableDataGatewayRDS();
	}

	/**
	 * A private constructor only called by the getSingleton method
	 */
	private LevelTableDataGatewayRDS()
	{
		//do nothing this just explicitly makes it private
	}

	/**
	 * @see datasource.LevelTableDataGateway#getAllLevels()
	 */
	@Override
	public ArrayList<LevelRecord> getAllLevels() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement("SELECT * FROM Levels");
			ResultSet result = stmt.executeQuery();

			ArrayList<LevelRecord> results = new ArrayList<>();
			while (result.next())
			{
				LevelRecord rec = new LevelRecord(result.getString("levelDescription"), result.getInt("levelUpPoints"),
						result.getInt("levelUpMonth"), result.getInt("LevelUpDayOfMonth"));
				results.add(rec);
			}
			return results;
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find levels", e);
		}
	}

	/**
	 * Dump any existing table and create a new one
	 *
	 * @throws DatabaseException if we can't create it
	 */
	public static void createTable() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement("DROP TABLE IF EXISTS Levels");
			stmt.executeUpdate();
			stmt.close();
			stmt =  connection.prepareStatement(
					"Create TABLE Levels (levelDescription VARCHAR(30) NOT NULL, levelUpPoints INT NOT NULL, levelUpMonth INT NOT NULL, levelUpDayOfMonth INT NOT NULL)");
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the Level table", e);
		}
	}

	/**
	 * Add a new row to the table
	 *
	 * @param description the description of the level
	 * @param levelUpPoints the number of points you need to get to the next
	 *            level
	 * @param levelUpMonth the month by which the players must get to the next
	 *            level
	 * @param levelUpDayOfMonth the day of the month by which the player must
	 *            get to the next level
	 * @throws DatabaseException if we can't talk to the db
	 */
	public void createRow(String description, int levelUpPoints, int levelUpMonth, int levelUpDayOfMonth)
			throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement(
					"Insert INTO Levels SET levelDescription = ?, levelUpPoints = ?, levelUpMonth = ?, levelUpDayOfMonth = ?");
			stmt.setString(1, description);
			stmt.setInt(2, levelUpPoints);
			stmt.setInt(3, levelUpMonth);
			stmt.setInt(4, levelUpDayOfMonth);
			stmt.executeUpdate();

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't create a level record for level with description " + description, e);
		}
	}

	@Override
	public void resetTableGateway()
	{

	}
}

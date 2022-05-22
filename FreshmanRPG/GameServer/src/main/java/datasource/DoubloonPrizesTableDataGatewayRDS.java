package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import dataDTO.DoubloonPrizeDTO;

/**
 * @author Mina Kindo, Christian Chroutamel, Andrew Stake
 *
 */
public class DoubloonPrizesTableDataGatewayRDS implements DoubloonPrizesTableDataGateway
{

	static TableDataGateway getGateway()
	{
		return new DoubloonPrizesTableDataGatewayRDS();
	}

	/**
	 * Resets the data
	 */
	@Override
	public void resetTableGateway()
	{

	}

	@Override
	public ArrayList<DoubloonPrizeDTO> getAllDoubloonPrizes() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement("SELECT * FROM DoubloonPrizes;");
			ResultSet result = stmt.executeQuery();
			ArrayList<DoubloonPrizeDTO> results = new ArrayList<>();
			while (result.next())
			{
				DoubloonPrizeDTO prize = new DoubloonPrizeDTO(result.getString("name"), result.getInt("cost"),
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

	public static void createTable() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement("DROP TABLE IF EXISTS DoubloonPrizes");
			stmt.executeUpdate();
			stmt.close();
			stmt =  connection.prepareStatement(
					"CREATE TABLE DoubloonPrizes(name VARCHAR(30) NOT NULL, "
							+ "cost INT NOT NULL, description VARCHAR(200) NOT NULL)");
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the DoubloonPrizes table");
		}

	}

	public void createRow(String name, int cost, String description) throws DatabaseException
	{
		String query = "INSERT INTO DoubloonPrizes " + "SET name = ?, cost = ?, description = ?";
		Connection connection = DatabaseManager.getSingleton().getConnection();

		try
		{
			PreparedStatement stmt =  connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

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
}

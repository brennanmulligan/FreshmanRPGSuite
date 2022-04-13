package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import dataDTO.KnowledgePointPrizeDTO;

/**
 * @author Mina Kindo, Christian Chroutamel, Andrew Stake
 *
 */
public class KnowledgePointPrizesTableDataGatewayRDS extends KnowledgePointPrizesTableDataGateway
{

	private static KnowledgePointPrizesTableDataGatewayRDS singleton;

	@Override
	public void resetData()
	{
		// does nothing

	}


	/**
	 * @return unique instance
	 */
	public static synchronized KnowledgePointPrizesTableDataGatewayRDS getInstance()
	{
		if (singleton == null)
		{
			singleton = new KnowledgePointPrizesTableDataGatewayRDS();
		}

		return singleton;
	}

	@Override
	public ArrayList<KnowledgePointPrizeDTO> getAllKnowledgePointPrizes() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement("SELECT * FROM KnowledgePointPrizes;");
			ResultSet result = stmt.executeQuery();
			ArrayList<KnowledgePointPrizeDTO> results = new ArrayList<>();
			while (result.next())
			{
				KnowledgePointPrizeDTO prize = new KnowledgePointPrizeDTO(result.getString("name"), result.getInt("cost"),
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

	/**
	 * @throws DatabaseException
	 */
	public static void createTable() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement("DROP TABLE IF EXISTS KnowledgePointPrizes");
			stmt.executeUpdate();
			stmt.close();
			stmt =  connection.prepareStatement(
					"CREATE TABLE KnowledgePointPrizes(name VARCHAR(30) NOT NULL, "
							+ "cost INT NOT NULL, description VARCHAR(200) NOT NULL)");
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the KnowledgePointPrizes table");
		}

	}

	/**
	 * @param name
	 * @param cost
	 * @param description
	 * @throws DatabaseException
	 */
	public void createRow(String name, int cost, String description) throws DatabaseException
	{
		String query = "INSERT INTO KnowledgePointPrizes " + "SET name = ?, cost = ?, description = ?";
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
			throw new DatabaseException("Row creation of knowledge point prize not successful", e);
		}
	}
}

package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The RDS version of the gateway
 *
 * @author Merlin
 *
 */
public class NPCRowDataGatewayRDS implements NPCRowDataGateway
{

	private int playerID;
	private String behaviorClass;
	private String filePath;
	private Connection connection;

	/**
	 * finder constructor
	 *
	 * @param playerID the unique ID of the player we are working with
	 * @throws DatabaseException if we cannot find that player in the database
	 */
	public NPCRowDataGatewayRDS(int playerID) throws DatabaseException
	{
		this.playerID = playerID;
		this.connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement(
					"SELECT * FROM NPCs WHERE playerID = ?");
			stmt.setInt(1, playerID);
			ResultSet result = stmt.executeQuery();
			result.next();
			this.behaviorClass = result.getString("behaviorClass");
			this.filePath = result.getString("filePath");

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find an NPC with playerID " + playerID, e);
		}
	}

	/**
	 * Create Constructor
	 *
	 * @param playerID the NPC's playerID
	 * @param behaviorClass the name of the class encoding the behavior for the
	 *            NPC
	 * @throws DatabaseException if we can't add the info to the database
	 */
	public NPCRowDataGatewayRDS(int playerID, String behaviorClass, String filePath) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement(
					"Insert INTO NPCs SET playerID = ?, behaviorClass = ?, filePath = ?");
			stmt.setInt(1, playerID);
			stmt.setString(2, behaviorClass);
			stmt.setString(3, filePath);
			stmt.executeUpdate();

			this.playerID = playerID;

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't create record in NPCs for player with ID " + playerID, e);
		}
	}

	/**
	 * Drop the table if it exists and re-create it empty
	 *
	 * @throws DatabaseException shouldn't
	 */
	public static void createTable() throws DatabaseException
	{
		String sql = "Create TABLE NPCs ("
				+ "playerID INT NOT NULL,"
				+ "behaviorClass VARCHAR(80),"
				+ "filePath VARCHAR(80),"
				+ "PRIMARY KEY (playerID),"
				+ "FOREIGN KEY (playerID) REFERENCES Players(playerID) "
				+ "ON DELETE CASCADE)";

		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement( "DROP TABLE IF EXISTS NPCs");
			stmt.executeUpdate();
			stmt.close();

			stmt =  connection.prepareStatement(sql);
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the NPC table", e);
		}
	}

	/**
	 * @see datasource.NPCRowDataGateway#getBehaviorClass()
	 */
	@Override
	public String getBehaviorClass()
	{
		return behaviorClass;
	}

	/**
	 * @see datasource.NPCRowDataGateway#getFilePath()
	 */
	@Override
	public String getFilePath()
	{
		return filePath;
	}

	/**
	 * @see datasource.NPCRowDataGateway#getPlayerID()
	 */
	@Override
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @see datasource.NPCRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
	}

	/**
	 * Get gateways for all of the NPCs that are managed by a server managing a
	 * given map
	 *
	 * @param mapName the map we are interested in
	 * @return the NPCs
	 * @throws DatabaseException if we have trouble talking to the database
	 */
	public static ArrayList<NPCRowDataGateway> getNPCsForMap(String mapName) throws DatabaseException
	{
		ArrayList<NPCRowDataGateway> gateways = new ArrayList<>();

		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement(
					"SELECT * FROM PlayerConnection INNER JOIN NPCs ON NPCs.playerID = PlayerConnection.playerID");
			ResultSet result = stmt.executeQuery();
			while (result.next())
			{
				if (result.getString("mapName").equals(mapName))
				{
					gateways.add(new NPCRowDataGatewayRDS(result.getInt("NPCs.playerID")));
				}
			}

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to retrieve NPCs for map named " + mapName, e);
		}
		return gateways;
	}

}

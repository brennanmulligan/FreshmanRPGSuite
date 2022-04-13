package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Abraham Loscher
 *
 */
public class MapTableDataGatewayRDS implements MapTableDataGateway
{
	private static MapTableDataGatewayRDS instance;

	private MapTableDataGatewayRDS()
	{

	}

	/**
	 * Retrieves singleton instance
	 *
	 * @return instance of gateway
	 */
	public static MapTableDataGatewayRDS getInstance()
	{
		if (instance == null)
		{
			instance = new MapTableDataGatewayRDS();
		}
		return instance;
	}

	/**
	 * @see datasource.MapTableDataGateway#getMapNames()
	 */
	public ArrayList<String> getMapNames()
	{
		ArrayList<String> data = new ArrayList<>();
		ResultSet rs;

		try
		{
			Connection conn = DatabaseManager.getSingleton().getConnection();

			PreparedStatement stmt = conn.prepareStatement("SELECT mapName FROM Server ORDER BY serverID");
			rs = stmt.executeQuery();
			while (rs.next())
			{
				data.add(rs.getString("MapName"));
			}

		}
		catch (SQLException | DatabaseException e)
		{
			e.printStackTrace();
		}

		return data;
	}

}

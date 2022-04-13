package DatabaseBuilders;

import java.sql.SQLException;

import datasource.DatabaseException;
import datasource.DatabaseManager;
import datasource.ServerRowDataGatewayRDS;
import model.OptionsManager;
import datatypes.ServersForTest;

/**
 * Builds the Player portion of the database
 *
 * @author Merlin
 *
 */
public class BuildTestDBServers
{

	/**
	 *
	 * @param args unused
	 * @throws DatabaseException shouldn't
	 * @throws SQLException shouldn't
	 */
	public static void main(String[] args) throws DatabaseException, SQLException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(false);
		createServerTable();
	}

	private static void createServerTable() throws SQLException, DatabaseException
	{
		DatabaseManager.getSingleton().getConnection();
		ServerRowDataGatewayRDS.createTable();
		for (ServersForTest p : ServersForTest.values())
		{
			new ServerRowDataGatewayRDS(p.getMapName(), p.getHostName(), p.getPortNumber(), p.getMapTitle(), p.getTeleportPositionX(), p.getTeleportPositionY());
		}

	}
}

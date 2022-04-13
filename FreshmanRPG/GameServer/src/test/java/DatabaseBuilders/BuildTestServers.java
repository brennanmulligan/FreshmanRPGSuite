package DatabaseBuilders;

import java.sql.SQLException;

import datasource.DatabaseException;
import datasource.ServerRowDataGatewayRDS;
import model.OptionsManager;
import datatypes.ServersForTest;

/**
 * Builds the Level portion of the database
 *
 * @author Merlin
 *
 */
public class BuildTestServers
{

	/**
	 *
	 * @param args unused
	 * @throws DatabaseException shouldn't
	 * @throws SQLException shouldn'tupdateFriendButton(false);
	 */
	public static void main(String[] args) throws DatabaseException, SQLException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(false);
		OptionsManager.getSingleton().setUsingTestDB(true);
		createLevelTable();
	}

	/**
	 * Create a table of levels
	 *
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	private static void createLevelTable() throws SQLException, DatabaseException
	{
		ServerRowDataGatewayRDS.createTable();
		for (ServersForTest server : ServersForTest.values())
		{
			new ServerRowDataGatewayRDS(server.getMapName(), server.getHostName(), server.getPortNumber(), server.getMapTitle(), server.getTeleportPositionX(), server.getTeleportPositionY());
		}
	}
}

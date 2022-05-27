package DatabaseBuilders;

import java.sql.SQLException;

import datasource.DatabaseException;
import datasource.ServerRowDataGateway;
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
		OptionsManager.getSingleton().setUsingTestDB(true);
		createLevelTable();
	}

	/**
	 * Create a table of levels
	 *
	 */
	private static void createLevelTable() throws DatabaseException
	{
		ServerRowDataGateway.createTable();
		for (ServersForTest server : ServersForTest.values())
		{
			new ServerRowDataGateway(server.getMapName(), server.getHostName(), server.getPortNumber(), server.getMapTitle(), server.getTeleportPositionX(), server.getTeleportPositionY());
		}
	}
}

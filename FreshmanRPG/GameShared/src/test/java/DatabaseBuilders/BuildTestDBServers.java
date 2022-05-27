package DatabaseBuilders;

import java.sql.SQLException;

import datasource.DatabaseException;
import datasource.DatabaseManager;
import datasource.ServerRowDataGateway;
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
		createServerTable();
	}

	private static void createServerTable() throws DatabaseException
	{
		DatabaseManager.getSingleton().getConnection();
		ServerRowDataGateway.createTable();
		for (ServersForTest p : ServersForTest.values())
		{
			new ServerRowDataGateway(p.getMapName(), p.getHostName(), p.getPortNumber(), p.getMapTitle(), p.getTeleportPositionX(), p.getTeleportPositionY());
		}

	}
}

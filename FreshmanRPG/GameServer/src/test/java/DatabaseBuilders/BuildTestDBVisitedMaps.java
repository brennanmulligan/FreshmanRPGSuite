package DatabaseBuilders;

import datasource.DatabaseException;
import datasource.VisitedMapsGateway;
import model.OptionsManager;
import datatypes.PlayersForTest;

/**
 * Build the visited maps database table
 *
 */
public class BuildTestDBVisitedMaps
{
	/**
	 * @param args unused
	 * @throws DatabaseException if database access issue
	 */
	public static void main(String[] args) throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingTestDB(true);
		createVisitedMapsTable();
	}

	private static void createVisitedMapsTable() throws DatabaseException
	{
		VisitedMapsGateway.createTable();

		for (PlayersForTest p : PlayersForTest.values())
		{
			System.out.println("player: " + p.getPlayerName());
			for (String mapTitle : p.getMapsVisited())
			{
				System.out.println("mapTitle:" + mapTitle);
				new VisitedMapsGateway(p.getPlayerID(), mapTitle);
			}
		}

	}
}

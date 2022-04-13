package DatabaseBuilders;

import datasource.DatabaseException;
import datasource.VisitedMapsGatewayRDS;
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
		OptionsManager.getSingleton().setUsingMocKDataSource(false);
		OptionsManager.getSingleton().setUsingTestDB(true);
		createVisitedMapsTable();
	}

	private static void createVisitedMapsTable() throws DatabaseException
	{
		VisitedMapsGatewayRDS.createTable();

		for (PlayersForTest p : PlayersForTest.values())
		{
			System.out.println("player: " + p.getPlayerName());
			for (String mapTitle : p.getMapsVisited())
			{
				System.out.println("mapTitle:" + mapTitle);
				new VisitedMapsGatewayRDS(p.getPlayerID(), mapTitle);
			}
		}

	}
}

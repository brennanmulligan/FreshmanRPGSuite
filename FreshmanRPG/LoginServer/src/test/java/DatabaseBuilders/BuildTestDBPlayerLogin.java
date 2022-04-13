package DatabaseBuilders;

import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayRDS;
import datasource.PlayerLoginRowDataGatewayRDS;
import model.OptionsManager;
import datatypes.PlayersForTest;

/**
 * Builds the login portion of the database
 *
 * @author Merlin
 *
 */
public class BuildTestDBPlayerLogin
{

	/**
	 *
	 * @param args unused
	 * @throws DatabaseException shouldn't
	 */
	public static void main(String[] args) throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		OptionsManager.getSingleton().setUsingTestDB(true);
		createPlayerLoginTable();

	}

	private static void createPlayerLoginTable() throws DatabaseException
	{
		PlayerLoginRowDataGatewayRDS.createPlayerLoginTable();

		for (PlayersForTest p : PlayersForTest.values())
		{
			new PlayerLoginRowDataGatewayRDS(p.getPlayerID(), p.getPlayerName(), p.getPlayerPassword());
		}

	}

}

package DatabaseBuilders;

import datasource.DatabaseException;
import datasource.PlayerLoginRowDataGateway;
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
		OptionsManager.getSingleton().setUsingTestDB(true);
		createPlayerLoginTable();

	}

	private static void createPlayerLoginTable() throws DatabaseException
	{
		PlayerLoginRowDataGateway.createPlayerLoginTable();

		for (PlayersForTest p : PlayersForTest.values())
		{
			new PlayerLoginRowDataGateway(p.getPlayerID(), p.getPlayerName(), p.getPlayerPassword());
		}

	}

}

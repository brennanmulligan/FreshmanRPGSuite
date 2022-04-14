package DatabaseBuilders;

import java.sql.SQLException;

import datasource.DatabaseException;
import datasource.NPCRowDataGatewayRDS;
import datasource.PlayerConnectionRowDataGatewayRDS;
import datasource.PlayerLoginRowDataGatewayRDS;
import datasource.PlayerRowDataGatewayRDS;
import model.OptionsManager;
import datatypes.NPCsForTest;
import datatypes.PlayersForTest;

/**
 * Builds the Player portion of the database
 *
 * @author Merlin
 *
 */
public class BuildTestDBPlayers
{
	/**
	 *
	 * @param args unused
	 * @throws DatabaseException shouldn't
	 * @throws SQLException shouldn't
	 */
	public static void main(String[] args) throws DatabaseException, SQLException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		OptionsManager.getSingleton().setUsingTestDB(true);
		createPlayerTable();
		createNpcTable();
		createPlayerLoginTable();
		createPlayerConnectionTable();
	}

	private static void createPlayerTable() throws SQLException, DatabaseException
	{
		PlayerRowDataGatewayRDS.createTable();

		for (PlayersForTest p : PlayersForTest.values())
		{
			new PlayerRowDataGatewayRDS(p.getAppearanceType(), p.getDoubloons(),
					p.getExperiencePoints(), p.getCrew(), p.getMajor(), p.getSection(), p.getBuffPool(), p.getOnline());
		}

	}

	private static void createNpcTable() throws SQLException, DatabaseException
	{
		NPCRowDataGatewayRDS.createTable();

		for (NPCsForTest p : NPCsForTest.values())
		{
			new NPCRowDataGatewayRDS(p.getPlayerID(), p.getBehaviorClass(), p.getFilePath());
		}
	}

	private static void createPlayerLoginTable() throws DatabaseException
	{
		PlayerLoginRowDataGatewayRDS.createPlayerLoginTable();

		for (PlayersForTest p : PlayersForTest.values())
		{
			new PlayerLoginRowDataGatewayRDS(p.getPlayerID(), p.getPlayerName(), p.getPlayerPassword());
		}

	}

	private static void createPlayerConnectionTable() throws DatabaseException
	{
		System.out.println("Building the PlayerConnection Table");
		PlayerConnectionRowDataGatewayRDS.createPlayerConnectionTable();

		for (PlayersForTest p : PlayersForTest.values())
		{
			System.out.println(p.getPlayerID() + ": " + p.getMapName());
			new PlayerConnectionRowDataGatewayRDS(p.getPlayerID(), p.getPin(), p.getMapName(), p.getPosition());
		}

	}
}

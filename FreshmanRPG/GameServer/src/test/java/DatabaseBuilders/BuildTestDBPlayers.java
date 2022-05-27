package DatabaseBuilders;

import java.sql.SQLException;

import datasource.*;
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
		OptionsManager.getSingleton().setUsingTestDB(true);
		createPlayerTable();
		createNpcTable();
		createPlayerLoginTable();
		createPlayerConnectionTable();
	}

	private static void createPlayerTable() throws DatabaseException
	{
		PlayerRowDataGateway.createTable();

		for (PlayersForTest p : PlayersForTest.values())
		{
			new PlayerRowDataGateway(p.getAppearanceType(), p.getDoubloons(),
					p.getExperiencePoints(), p.getCrew(), p.getMajor(), p.getSection(), p.getBuffPool(), p.getOnline());
		}

	}

	private static void createNpcTable() throws DatabaseException
	{
		NPCRowDataGateway.createTable();

		for (NPCsForTest p : NPCsForTest.values())
		{
			new NPCRowDataGateway(p.getPlayerID(), p.getBehaviorClass(), p.getFilePath());
		}
	}

	private static void createPlayerLoginTable() throws DatabaseException
	{
		PlayerLoginRowDataGateway.createPlayerLoginTable();

		for (PlayersForTest p : PlayersForTest.values())
		{
			new PlayerLoginRowDataGateway(p.getPlayerID(), p.getPlayerName(), p.getPlayerPassword());
		}

	}

	private static void createPlayerConnectionTable() throws DatabaseException
	{
		System.out.println("Building the PlayerConnection Table");
		PlayerConnectionRowDataGateway.createPlayerConnectionTable();

		for (PlayersForTest p : PlayersForTest.values())
		{
			System.out.println(p.getPlayerID() + ": " + p.getMapName());
			new PlayerConnectionRowDataGateway(p.getPlayerID(), p.getPin(), p.getMapName(), p.getPosition());
		}

	}
}

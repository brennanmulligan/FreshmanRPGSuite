package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dataDTO.PlayerDTO;
import datasource.AdventureStateTableDataGatewayMock;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;
import datasource.PlayerLoginRowDataGatewayMock;
import datasource.PlayerRowDataGatewayMock;
import datasource.QuestStateTableDataGatewayMock;
import model.reports.AllPlayersReport;

/**
 * @author Darnell martin
 *
 */
public class CommandAddPlayerTest
{

	/**
	 * Resets the data in the mocks and sets test mode.
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		new PlayerRowDataGatewayMock().resetData();
		new PlayerLoginRowDataGatewayMock().resetData();
		QuestStateTableDataGatewayMock.getSingleton().resetData();
		AdventureStateTableDataGatewayMock.getSingleton().resetData();
		QuestManager.resetSingleton();
		new PlayerConnectionRowDataGatewayMock(1).resetData();
		PlayerManager.resetSingleton();
	}

	/**
	 * test that the player exists in the database after getting added. And the
	 * report gets sent out.
	 *
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	@Test
	public void testExecute() throws DatabaseException
	{
		final int crew = 1;
		final int major = 2;
		final int sectionID = 2;
		final String name = "Mock Player Name";
		final String password = "mock_password";

		CommandAddPlayerInManager cmd = new CommandAddPlayerInManager(name, password, crew, major, sectionID);

		cmd.execute();

		GameManagerPlayerManager manager = GameManagerPlayerManager.getInstance();
		ArrayList<PlayerDTO> list = manager.getPlayers();
		for (PlayerDTO player : list)
		{
			if ((player.getPlayerName().equals(name)))
			{
				assertEquals(player.getPlayerName(), name);
				assertEquals(player.getAppearanceType(), "default_player");
				assertEquals(player.getSection(), 2);
				assertEquals(player.getMajor().getID(), 2);
				assertEquals(player.getCrew().getID(), 1);
				return;
			}
		}
		fail("Player Does not exist");
	}

	/**
	 * test that the report gets sent out.
	 *
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	@Test
	public void testReport() throws DatabaseException
	{
		final int crew = 1;
		final int major = 2;
		final int sectionID = 2;
		final String name = "Mock Player Name";
		final String password = "mock_password";

		MockQualifiedObserver obs = new MockQualifiedObserver(AllPlayersReport.class);
		CommandAddPlayerInManager cmd = new CommandAddPlayerInManager(name, password, crew, major, sectionID);

		cmd.execute();

		GameManagerPlayerManager manager = GameManagerPlayerManager.getInstance();
		ArrayList<PlayerDTO> list = manager.getAllPlayers();
		AllPlayersReport listReport = new AllPlayersReport(list);
		assertEquals(listReport.getPlayerInfo(), ((AllPlayersReport) obs.getReport()).getPlayerInfo());
	}

}
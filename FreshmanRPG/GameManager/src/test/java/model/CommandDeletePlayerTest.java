package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dataDTO.PlayerDTO;
import datasource.ObjectiveStateTableDataGatewayMock;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;
import datasource.PlayerLoginRowDataGatewayMock;
import datasource.PlayerRowDataGatewayMock;
import datasource.QuestStateTableDataGatewayMock;
import model.reports.AllPlayersReport;
import datatypes.PlayersForTest;

/**
 * Tests the functionality of CommandDeletePlayer.
 *
 */
public class CommandDeletePlayerTest
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
		ObjectiveStateTableDataGatewayMock.getSingleton().resetData();
		QuestManager.resetSingleton();
		new PlayerConnectionRowDataGatewayMock(1).resetData();
		GameManagerPlayerManager.resetSingleton();
	}

	/**
	 * When execute is called, this command should tell it's receiver to delete a
	 * player.
	 *
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	@Test
	public void testExecuteCommand() throws DatabaseException
	{
		PlayersForTest player = PlayersForTest.MARTY;
		CommandDeletePlayer command = new CommandDeletePlayer(player.getPlayerID());

		assertTrue(command.execute());

		GameManagerPlayerManager playerManager = GameManagerPlayerManager.getInstance();

		assertEquals(PlayersForTest.values().length - 1, playerManager.getPlayers().size());
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
		MockQualifiedObserver obs = new MockQualifiedObserver(AllPlayersReport.class);
		CommandDeletePlayer cmd = new CommandDeletePlayer(PlayersForTest.ANDY.getPlayerID());

		cmd.execute();

		GameManagerPlayerManager manager = GameManagerPlayerManager.getInstance();
		ArrayList<PlayerDTO> list = manager.getAllPlayers();
		AllPlayersReport listReport = new AllPlayersReport(list);
		assertEquals(listReport.getPlayerInfo(), ((AllPlayersReport) obs.getReport()).getPlayerInfo());
	}

}

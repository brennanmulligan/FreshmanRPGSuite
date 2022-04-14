package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import datasource.ObjectiveStateTableDataGatewayMock;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;
import datasource.PlayerLoginRowDataGatewayMock;
import datasource.PlayerRowDataGatewayMock;
import datasource.QuestStateTableDataGatewayMock;
import model.reports.AllPlayersReport;


/**
 * Command to get all players
 * @author Darin Alleman / Darnell Martin
 *
 */
public class CommandGetAllPlayersTest
{

	/**
	 * Resets the data in the mocks and sets test mode.
	 *
	 * @throws DatabaseException shouldn't
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
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testReportIsSent() throws DatabaseException
	{
		MockQualifiedObserver obs = new MockQualifiedObserver(AllPlayersReport.class);
		CommandGetAllPlayers cmd = new CommandGetAllPlayers();
		cmd.execute();

		GameManagerPlayerManager pm = GameManagerPlayerManager.getInstance();
		AllPlayersReport mockReport = new AllPlayersReport(pm.getPlayers());
		assertEquals(mockReport.getPlayerInfo().size(), ((AllPlayersReport) obs.getReport()).getPlayerInfo().size());
		assertEquals(mockReport.getPlayerInfo(), ((AllPlayersReport) obs.getReport()).getPlayerInfo());
	}

	/**
	 * Check to see if the command returns false when a database error occurs.
	 */
	@Test
	public void testErrorIsThrown()
	{
		try
		{
			GameManagerPlayerManager.setSingleton(new FailingPlayerManager());
		}
		catch (DatabaseException e)
		{
			fail("Test failed because the singleton instance could not be replaced.");
		}

		CommandGetAllPlayers command = new CommandGetAllPlayers();
		assertFalse(command.execute());
		GameManagerPlayerManager.resetSingleton();
	}

}

/**
 * A mock class that forces an exception to be thrown when you try to refresh the players list
 *
 */
class FailingPlayerManager extends GameManagerPlayerManager
{

	/**
	 * @throws DatabaseException shouldn't
	 */
	FailingPlayerManager() throws DatabaseException
	{
		super();
	}

	/**
	 * @see model.GameManagerPlayerManager#refreshPlayerList()
	 */
	@Override
	public void refreshPlayerList() throws DatabaseException
	{
		throw new DatabaseException("Test exception");
	}

}

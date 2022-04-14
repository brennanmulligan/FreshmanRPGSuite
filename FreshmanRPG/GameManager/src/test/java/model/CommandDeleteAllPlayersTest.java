package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import datasource.ObjectiveStateTableDataGatewayMock;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;
import datasource.PlayerLoginRowDataGatewayMock;
import datasource.PlayerRowDataGatewayMock;
import datasource.QuestStateTableDataGatewayMock;

/**
 * Tests for command to delete all quizbot questions in table before importing.
 *
 * @author Jordan Long
 *
 */
public class CommandDeleteAllPlayersTest
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
	 * Make sure the command deletes
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void deleteAllPlayers() throws DatabaseException
	{
		CommandDeleteAllPlayers command = new CommandDeleteAllPlayers();

		assertTrue(command.execute());

		GameManagerPlayerManager playerManager = GameManagerPlayerManager.getInstance();
		assertEquals(0, playerManager.getNumberOfPlayers());

	}

}

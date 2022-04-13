package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import datatypes.Crew;
import datatypes.Major;
import model.reports.AllPlayersReport;
import datatypes.PlayersForTest;

/**
 * Tests the functionality of the PlayerManager class.
 *
 */
public class PlayerManagerTest
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
		GameManagerPlayerManager.resetSingleton();
	}

	/**
	 * When instantiated, a PlayerManager should have all players in it's local
	 * list.
	 *
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	@Test
	public void testPopulateList() throws DatabaseException
	{
		GameManagerPlayerManager playerManager = GameManagerPlayerManager.getInstance();
		ArrayList<PlayerDTO> players = playerManager.getPlayers();
		assertNotNull(players);

		assertEquals(PlayersForTest.values().length, players.size());
	}

	/**
	 * When we add a player, the PlayerManager should keep track of it and a
	 * report should be sent.
	 *
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	@Test
	public void testAddPlayer() throws DatabaseException
	{
		final int crew = 1;
		final int major = 1;
		final int section = 2;
		final String name = "MockPlayerName";
		final String password = "mock_password";

		GameManagerPlayerManager playerManager = GameManagerPlayerManager.getInstance();

		MockQualifiedObserver mock = new MockQualifiedObserver(AllPlayersReport.class);

		// report false before player added
		assertFalse(mock.didReceiveReport());

		int originalSize = playerManager.getNumberOfPlayers();

		PlayerDTO player = playerManager.addPlayer(name, password, crew, major, section);

		PlayerLogin loginInfo = new PlayerLogin(player.getPlayerID());
		assertNotNull(loginInfo.getPlayerName());
		assertNotNull(loginInfo.getPlayerID());
		assertNotNull(loginInfo.getPlayerPassword());

		assertEquals(++originalSize, playerManager.getNumberOfPlayers());
		assertTrue(playerManager.getPlayers().contains(player));

		assertEquals("default_player", player.getAppearanceType());

		// no longer null
		assertNotNull(mock.getReport());
	}


	/**
	 * When a player is removed, PlayerManager should no longer keep track of
	 * it.
	 *
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	@Test
	public void testRemovePlayer() throws DatabaseException
	{
		PlayersForTest player = PlayersForTest.HERSH;
		PlayerDTO playerDTO = new PlayerDTO(player.getPlayerID(), player.getPlayerName(), player.getPlayerPassword(),
				player.getAppearanceType(), player.getKnowledgeScore(), player.getPosition(), player.getMapName(),
				player.getExperiencePoints(), player.getCrew(), player.getMajor(), player.getSection(), player.getMapsVisited());
		GameManagerPlayerManager playerManager = GameManagerPlayerManager.getInstance();

		playerManager.removePlayer(player.getPlayerID());

		assertEquals(PlayersForTest.values().length - 1, playerManager.getNumberOfPlayers());
		assertFalse(playerManager.getPlayers().contains(playerDTO));
	}

	/**
	 * Make sure that a player is being edited
	 *
	 * @throws DatabaseException
	 * 				- Shouldn't
	 * @throws IllegalQuestChangeException
	 * 				- Shouldnt't
	 */
	@Test
	public void testEditPlayer() throws DatabaseException, IllegalQuestChangeException
	{
		GameManagerPlayerManager playerManager = GameManagerPlayerManager.getInstance();

		assertTrue(playerManager.editPlayer(1, "test",
				100, 200, Crew.NULL_POINTER, Major.SOFTWARE_ENGINEERING, 10,
				"Mock Player Name", "mock_password"));

		GameManagerPlayerManager.resetSingleton();
		playerManager = GameManagerPlayerManager.getInstance();

		PlayerDTO playerDTO = playerManager.getPlayers().get(0);

		assertEquals("test", playerDTO.getAppearanceType());

		assertEquals(100, playerDTO.getKnowledgePoints());
		assertEquals(200, playerDTO.getExperiencePoints());
		assertEquals(Crew.NULL_POINTER, playerDTO.getCrew());
		assertEquals(Major.SOFTWARE_ENGINEERING, playerDTO.getMajor());
		assertEquals(10, playerDTO.getSection());
		assertEquals("Mock Player Name", playerDTO.getPlayerName());
		assertEquals("mock_password", playerDTO.getPlayerPassword());


		PlayerLogin playerLogin = new PlayerLogin(playerDTO.getPlayerID());
		assertEquals("mock_password", playerLogin.getPlayerPassword());
		assertNotNull(playerLogin.getPlayerName());
		assertNotNull(playerLogin.getPlayerID());


	}


}
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import datatypes.Crew;
import datatypes.Major;
import model.reports.AllPlayersReport;
import datatypes.PlayersForTest;

/**
 * Tests the functionality of the PlayerManager class.
 *
 */
public class GameManagerPlayerManagerTest
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
	 * When we add a player, the PlayerManager should keep track of it and a report
	 * should be sent.
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

		MockObserver mock = new MockObserver();
		mock.setUpListening();

		// report null before player added
		assertNull(mock.report);

		int originalSize = playerManager.getNumberOfPlayers();

		PlayerDTO player = playerManager.addPlayer(name, password, crew, major, section);

		PlayerLogin loginInfo = new PlayerLogin(player.getPlayerID());
		assertNotNull(loginInfo.getPlayerName());
		assertNotNull(loginInfo.getPlayerID());
		assertNotNull(loginInfo.getPlayerPassword());

		assertEquals(++originalSize, playerManager.getNumberOfPlayers());
		assertTrue(playerManager.getPlayers().contains(player));

		// no longer null
		assertNotNull(mock.report);
	}

	/**
	 * Used to test reports are sent
	 *
	 */
	class MockObserver implements QualifiedObserver
	{

		private QualifiedObservableReport report;

		/**
		 *
		 */
		public void setUpListening()
		{
			QualifiedObservableConnector.getSingleton().registerObserver(this, AllPlayersReport.class);
		}

		/**
		 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
		 */
		@Override
		public void receiveReport(QualifiedObservableReport report)
		{
			this.report = report;
		}
	}

	/**
	 * When a player is removed, PlayerManager should no longer keep track of it.
	 *
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	@Test
	public void testRemovePlayer() throws DatabaseException
	{
		PlayersForTest player = PlayersForTest.HERSH;
		PlayerDTO playerDTO = new PlayerDTO(player.getPlayerID(), player.getPlayerName(), player.getPlayerPassword(),
				player.getAppearanceType(), player.getDoubloons(), player.getPosition(), player.getMapName(),
				player.getExperiencePoints(), player.getCrew(), player.getMajor(), player.getSection(), player.getMapsVisited(), new ArrayList<>());
		GameManagerPlayerManager playerManager = GameManagerPlayerManager.getInstance();

		playerManager.removePlayer(player.getPlayerID());

		assertEquals(PlayersForTest.values().length - 1, playerManager.getPlayers().size());
		assertFalse(playerManager.getPlayers().contains(playerDTO));
	}

	/**
	 * Make sure that a player is being edited
	 *
	 * @throws DatabaseException
	 *             - Shouldn't
	 * @throws IllegalQuestChangeException
	 *             - Shouldnt't
	 */
	@Test
	public void testEditPlayer() throws DatabaseException, IllegalQuestChangeException
	{
		GameManagerPlayerManager playerManager = GameManagerPlayerManager.getInstance();

		assertTrue(playerManager.editPlayer(1, "test", 100, 200, Crew.NULL_POINTER, Major.SOFTWARE_ENGINEERING, 10,
				"Mock Player Name", "mock_password"));

		GameManagerPlayerManager.resetSingleton();
		playerManager = GameManagerPlayerManager.getInstance();

		PlayerDTO playerDTO = playerManager.getPlayers().get(0);

		assertEquals("test", playerDTO.getAppearanceType());

		assertEquals(100, playerDTO.getDoubloons());
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

	/**
	 * Make sure that a player is updated when the player already exists
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException
	 *             shouldn't
	 */
	@Test
	public void testSavePlayerWhenPlayerExists() throws DatabaseException, IllegalQuestChangeException
	{
		GameManagerPlayerManager playerManager = GameManagerPlayerManager.getInstance();
		PlayerDTO dto = playerManager.getPlayers().get(0);
		dto.setPlayerPassword("newPasswordGobblyGook");
		playerManager.savePlayer(dto.getPlayerName(), dto.getPlayerPassword(), dto.getCrew().getID(),
				dto.getMajor().getID(), dto.getSection());
		ArrayList<PlayerDTO> list = playerManager.getPlayers();
		assertEquals(list.get(0).getPlayerPassword(), "newPasswordGobblyGook");
	}

	/**
	 * Make sure that a player is added if they don't exist
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException
	 *             shouldn't
	 */
	@Test
	public void testSavePlayerWhenPlayerDoesNotExist() throws DatabaseException, IllegalQuestChangeException
	{
		GameManagerPlayerManager playerManager = GameManagerPlayerManager.getInstance();
		playerManager.savePlayer("PlayerName", "PlayerPassword", Crew.OFF_BY_ONE.getID(),
				Major.SOFTWARE_ENGINEERING.getID(), 1);
		ArrayList<PlayerDTO> list = playerManager.getPlayers();

		assertEquals(list.get(list.size() - 1).getPlayerPassword(), "PlayerPassword");
		assertEquals(list.get(list.size() - 1).getPlayerName(), "PlayerName");
		assertEquals(list.get(list.size() - 1).getCrew(), Crew.OFF_BY_ONE);
		assertEquals(list.get(list.size() - 1).getMajor(), Major.SOFTWARE_ENGINEERING);
		assertEquals(list.get(list.size() - 1).getSection(), 1);

	}

}
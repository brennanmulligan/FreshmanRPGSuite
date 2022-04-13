package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;


import java.sql.SQLException;
import java.util.ArrayList;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datatypes.PlayerScoreRecord;
import datatypes.Position;
import model.reports.AddExistingPlayerReport;
import model.reports.PlayerConnectionReport;
import model.reports.PlayerDisconnectedReport;
import datatypes.PlayersForTest;

/**
 * @author Merlin
 *
 */
public class PlayerManagerTest
{

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		QualifiedObservableConnector.resetSingleton();
		PlayerManager.resetSingleton();
	}

	/**
	 * Make sure PlayerManager is a resetable singleton
	 */
	@Test
	public void isSingleton()
	{
		PlayerManager pm1 = PlayerManager.getSingleton();
		PlayerManager pm2 = PlayerManager.getSingleton();
		assertSame(pm1, pm2);
		PlayerManager.resetSingleton();
		assertNotSame(pm1, PlayerManager.getSingleton());
	}

	/**
	 * Make sure we can add a player to the listS
	 */
	@Test
	public void canAddPlayer()
	{
		PlayerManager.getSingleton().addPlayer(1);
		assertEquals(1, PlayerManager.getSingleton().numberOfPlayers());
		Player p = PlayerManager.getSingleton().getPlayerFromID(1);
		assertEquals(1, p.getPlayerID());
		assertTrue(PlayerManager.getSingleton().getConnectedPlayers().contains(p));
	}

	/**
	 * Make sure that the PlayerManager notifies with the correct object type
	 */
	@Test
	public void notifiesOnAddPlayer()
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, PlayerConnectionReport.class);
		obs.receiveReport(EasyMock.isA(PlayerConnectionReport.class));
		EasyMock.replay(obs);

		PlayerManager.getSingleton().addPlayer(2);
		EasyMock.verify(obs);
	}

	/**
	 * When a player is added, we need to send it reports about all of the other
	 * players in the system
	 */
	@Test
	public void notifiesAboutExistingPlayersOnAddPlayer()
	{
		PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());

		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, AddExistingPlayerReport.class);
		AddExistingPlayerReport expected = new AddExistingPlayerReport(PlayersForTest.MATT.getPlayerID(),
				PlayersForTest.MERLIN.getPlayerID(), PlayersForTest.MERLIN.getPlayerName(),
				PlayersForTest.MERLIN.getAppearanceType(), PlayersForTest.MERLIN.getPosition(),
				PlayersForTest.MERLIN.getCrew(), PlayersForTest.MERLIN.getMajor(), PlayersForTest.MERLIN.getSection());
		;
		obs.receiveReport(expected);
		EasyMock.replay(obs);

		PlayerManager.getSingleton().addPlayer(PlayersForTest.MATT.getPlayerID());
	}

	/**
	 * Make sure that we can get a players id from the player name
	 *
	 * @throws PlayerNotFoundException shouldn't
	 */
	@Test
	public void canGetPlayerIDFromPlayerNameOnlyOne() throws PlayerNotFoundException
	{
		PlayerManager.getSingleton().addPlayer(1);
		assertEquals(1, PlayerManager.getSingleton().getPlayerIDFromPlayerName("John"));
	}

	/**
	 * Make sure that we can get a players id from the player name
	 *
	 * @throws PlayerNotFoundException shouldn't
	 */
	@Test
	public void canGetPlayerIDFromPlayerName() throws PlayerNotFoundException
	{
		PlayerManager.getSingleton().addPlayer(1);
		PlayerManager.getSingleton().addPlayer(2);
		assertEquals(2, PlayerManager.getSingleton().getPlayerIDFromPlayerName("Merlin"));
	}

	/**
	 * Test the removal of a player
	 * @throws DatabaseException thrown when cannot find player by id
	 * @throws IllegalQuestChangeException thrown if you try to change quest state to illegal state
	 */
	@Test
	public void canRemovePlayer() throws DatabaseException, IllegalQuestChangeException
	{
		PlayerManager.getSingleton().addPlayer(1);
		assertEquals(1, PlayerManager.getSingleton().getPlayerFromID(1).getPlayerID());

		PlayerManager.getSingleton().removePlayer(1);
		assertNull(PlayerManager.getSingleton().getPlayerFromID(1));

	}

	/**
	 * Make sure the correct exception is thrown if we search for a player whose
	 * name we don't know
	 *
	 * @throws PlayerNotFoundException should
	 */
	@Test(expected = PlayerNotFoundException.class)
	public void playerNameNotFound() throws PlayerNotFoundException
	{
		PlayerManager.getSingleton().getPlayerIDFromPlayerName("henry");
	}

	/**
	 * Test that a player can be persisted by saving an attribute and fetching
	 * the player again
	 *
	 * @throws DatabaseException shouldn't
	 * @throws IllegalQuestChangeException the state changed illegally
	 */
	@Test
	public void playerIsSaved() throws DatabaseException, IllegalQuestChangeException
	{
		Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
		player.setPlayerPosition(new Position(100, 100));
		assertTrue(PlayerManager.getSingleton().persistPlayer(player.getPlayerID()));

		PlayerManager.resetSingleton();

		Player fetched = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
		assertEquals(new Position(100, 100), fetched.getPlayerPosition());
	}

	/**
	 * Test that the known npcs will be in the database
	 *
	 * @throws DatabaseException shouldn't
	 * @throws SQLException shouldn't
	 */
	@Test
	public void testNpcsLoaded() throws DatabaseException, SQLException
	{
		OptionsManager om = OptionsManager.getSingleton();
		om.setUsingMocKDataSource(true);
		om.updateMapInformation(PlayersForTest.QUIZBOT.getMapName(), "localhost", 1874);
		PlayerManager.getSingleton().loadNpcs(false);

		assertNotNull(PlayerManager.getSingleton().getPlayerFromID(PlayersForTest.QUIZBOT.getPlayerID()));

	}

	/**
	 * Make sure it can get the high score list
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void canGetTopTen() throws DatabaseException
	{
		ArrayList<PlayerScoreRecord> result = PlayerManager.getSingleton().getTopTenPlayers();
		assertEquals(10, result.size());
	}

	/**
	 * Test that we can receive a report
	 */
	@Test
	public void receiveReport()
	{
		PlayerManager pm = PlayerManager.getSingleton();
		pm.addPlayer(10);
		assertNotNull(pm.getPlayerFromID(10));
		PlayerDisconnectedReport report = new PlayerDisconnectedReport(10);
		pm.receiveReport(report);
		assertNull(pm.getPlayerFromID(10));
	}

	/**
	 * Test we can add a map to the player through the constructor in the playerManager
	 */
	@Test
	public void testAddMap()
	{
		PlayerManager pm = PlayerManager.getSingleton();
		pm.addPlayer(2);
		assertEquals(PlayersForTest.MERLIN.getMapsVisited(), pm.getPlayerFromID(2).getPlayerVisitedMaps());
	}
}
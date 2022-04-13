package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import datatypes.PlayersForTest;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datatypes.Position;
import model.reports.PlayerMovedReport;
import model.reports.PlayerReadyToTeleportReport;

/**
 *
 * @author Merlin
 *
 */
public class CommandMovePlayerSilentlyAndPersistTest
{

	/**
	 * Reset PlayerManager
	 */
	@Before
	public void setup()
	{
		PlayerManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	/**
	 * Update a player's position from id
	 *
	 * @throws PlayerNotFoundException
	 *             shouldn't
	 */
	@Test
	public void testValidPlayer() throws PlayerNotFoundException
	{
		Position startPosition = new Position(0, 0);
		Position newPosition = new Position(10, 10);

		PlayerManager.getSingleton().addPlayer(1);
		Player p = PlayerManager.getSingleton().getPlayerFromID(1);
		p.setPlayerPosition(startPosition);

		assertEquals(startPosition, p.getPlayerPosition());

		CommandMovePlayerSilentlyAndPersist cmd = new CommandMovePlayerSilentlyAndPersist(1, "newMap", newPosition);
		cmd.execute();

		assertEquals(newPosition, p.getPlayerPosition());
		assertEquals("newMap", p.getMapName());
	}

	/**
	 * Make sure anyone who is observing for movement reports doesn't hear about
	 * this one
	 */
	@Test
	public void doesntNotifyObservers()
	{
		PlayerManager.getSingleton().addPlayer(1);
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, PlayerMovedReport.class);
		EasyMock.replay(obs);

		CommandMovePlayerSilentlyAndPersist cmd = new CommandMovePlayerSilentlyAndPersist(1, "newMap",
				new Position(4, 3));
		cmd.execute();

		EasyMock.verify(obs);
	}

	/**
	 * Update a player's position from id
	 *
	 * @throws PlayerNotFoundException
	 *             shouldn't
	 */
	@Test
	public void testNoPlayer() throws PlayerNotFoundException
	{
		Position newPosition = new Position(10, 10);

		CommandMovePlayerSilentlyAndPersist cmd = new CommandMovePlayerSilentlyAndPersist(-1, "a map", newPosition);
		assertFalse(cmd.execute());
	}

	/**
	 * Test that persistence happens
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException
	 *             the state changed illegally
	 */
	@Test
	public void testPersists() throws DatabaseException, IllegalQuestChangeException
	{
		Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
		player.setPlayerPositionWithoutNotifying(new Position(101, 101));
		player.setAppearanceType("appearance");

		CommandMovePlayerSilentlyAndPersist cmd = new CommandMovePlayerSilentlyAndPersist(player.getPlayerID(), "a map",
				new Position(101, 101));
		cmd.execute();

		PlayerManager.resetSingleton();

		Player fetched = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
		assertEquals(player.getPlayerPosition(), fetched.getPlayerPosition());
		assertEquals(player.getAppearanceType(), fetched.getAppearanceType());
	}

	/**
	 * Make sure that a report is thrown after command execution.
	 */
	@Test
	public void testThrowsPlayerPersistedReport()
	{
		int id = PlayersForTest.MERLIN.getPlayerID();
		PlayerManager.getSingleton().addPlayer(id);
		CommandMovePlayerSilentlyAndPersist command = new CommandMovePlayerSilentlyAndPersist(id, "test map",
				new Position(3, 5));
		PlayerReadyToTeleportReport expected = new PlayerReadyToTeleportReport(id, "test map");
		QualifiedObserver observer = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(observer, PlayerReadyToTeleportReport.class);
		observer.receiveReport(expected);
		EasyMock.replay(observer);

		assertTrue(command.execute());
		EasyMock.verify(observer);
	}

}

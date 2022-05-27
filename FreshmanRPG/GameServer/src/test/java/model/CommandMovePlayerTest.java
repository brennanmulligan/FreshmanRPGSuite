package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import datasource.ServerSideTest;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import datatypes.Position;
import model.reports.PlayerMovedReport;

/**
 *
 * @author Merlin
 *
 */
public class CommandMovePlayerTest extends ServerSideTest
{

	/**
	 * Reset PlayerManager
	 */
	@Before
	public void localSetup()
	{
		PlayerManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}

	/**
	 * Update a player's position from id
	 *
	 */
	@Test
	public void testValidPlayer()
	{
		Position startPosition = new Position(0, 0);
		Position newPosition = new Position(10, 10);

		PlayerManager.getSingleton().addPlayer(1);
		Player p = PlayerManager.getSingleton().getPlayerFromID(1);
		p.setPlayerPosition(startPosition);

		assertEquals(startPosition, p.getPlayerPosition());

		CommandMovePlayer cmd = new CommandMovePlayer(1, newPosition);
		cmd.execute();

		assertEquals(newPosition, p.getPlayerPosition());
	}

	/**
	 * Make sure anyone who is observing for movement reports hears about this
	 * one
	 */
	@Test
	public void notifyObservers()
	{
		PlayerManager.getSingleton().addPlayer(1);
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, PlayerMovedReport.class);
		obs.receiveReport(EasyMock.anyObject(PlayerMovedReport.class));
		EasyMock.replay(obs);

		CommandMovePlayer cmd = new CommandMovePlayer(1, new Position(4, 3));
		cmd.execute();

		EasyMock.verify(obs);
	}

	/**
	 * Update a player's position from id
	 *
	 */
	@Test
	public void testNoPlayer()
	{
		Position newPosition = new Position(10, 10);

		CommandMovePlayer cmd = new CommandMovePlayer(-1, newPosition);
		assertFalse(cmd.execute());
	}

}

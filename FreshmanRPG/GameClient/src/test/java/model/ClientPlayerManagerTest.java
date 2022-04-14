package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;

import dataDTO.VanityDTO;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;
import model.reports.LoginInitiatedReport;
import model.reports.PlayerConnectedToAreaServerReport;
import view.player.Vanity;

/**
 * Tests the player manager to make sure it maintains the list of players
 * correctly
 * 
 * @author merlin
 * 
 */
public class ClientPlayerManagerTest
{

	/**
	 * Always start with a new singleton
	 */
	@Before
	public void reset()
	{
		ClientPlayerManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}

	/**
	 * There should be only one player
	 */
	@Test
	public void testSingleton()
	{
		ClientPlayerManager player1 = ClientPlayerManager.getSingleton();
		assertSame(player1, ClientPlayerManager.getSingleton());
		ClientPlayerManager.resetSingleton();
		assertNotSame(player1, ClientPlayerManager.getSingleton());
	}

	/**
	 * Make sure we can add players and retrieve them by their player names
	 */
//	@Test
//	public void canAddAndRetrievePlayers()
//	{
//		Position pos = new Position(1, 2);
//		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
//		pm.initializePlayer(1, "Player 1", "Player 1 Body", "Player 1 Hat", pos,
//				Crew.NULL_POINTER, Major.COMPUTER_ENGINEERING, 10);
//		ClientPlayer player = pm.getPlayerFromID(1);
//		assertEquals("Player 1", player.getName() );
//		assertEquals("Player 1 Hat", player.getHatID());
//		assertEquals("Player 1 Body", player.getBodyID());
//		assertEquals(pos, player.getPosition());
//		assertEquals(Crew.NULL_POINTER, player.getCrew());
//		assertEquals(Major.COMPUTER_ENGINEERING, player.getMajor());
//		assertEquals(10, player.getSection());
//
//
//		//triangulate
//		pm.initializePlayer(2, "Player 2", "Player 2 Body", "Player 2 Hat",
//				new Position(2,3), Crew.OFF_BY_ONE, Major.COMPUTER_SCIENCE, 11);
//		player = pm.getPlayerFromID(2);
//		assertEquals("Player 2", player.getName() );
//		assertEquals("Player 2 Hat", player.getHatID());
//		assertEquals("Player 2 Body", player.getBodyID());
//		assertEquals(new Position(2,3), player.getPosition());
//		assertEquals(Crew.OFF_BY_ONE, player.getCrew());;
//		assertEquals(Major.COMPUTER_SCIENCE, player.getMajor());
//		assertEquals(11, player.getSection());
//
//	}

	/**
	 * Just make sure he remembers when a login is started
	 */
	@Test
	public void canStartToLogin()
	{
		ClientPlayerManager p = ClientPlayerManager.getSingleton();
		assertFalse(p.isLoginInProgress());
		p.initiateLogin("Fred", "mommy");
		assertTrue(p.isLoginInProgress());
	}

	/**
	 * Make sure that observers who want to be told when a login is initiated
	 * are told
	 */
	@Test
	public void notifiesOnLoginInitiation()
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		LoginInitiatedReport report = new LoginInitiatedReport("Fred", "daddy");
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				LoginInitiatedReport.class);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		ClientPlayerManager.getSingleton().initiateLogin("Fred", "daddy");

		EasyMock.verify(obs);
	}

	/**
	 * Test all the conditions behind setting this client's player to ensure the
	 * method is secure
	 */
	@Test
	public void testSettingThisClientsPlayer()
	{
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();

		// test setting player without having tried logging in
		try
		{
			pm.finishLogin(1);
		}
		catch (AlreadyBoundException | NotBoundException e)
		{
			assertTrue(e instanceof NotBoundException);
		}

		// test setting the player while trying to log in
		try
		{
			pm.initiateLogin("bilbo", "baggins");
			pm.finishLogin(1);
		}
		catch (AlreadyBoundException | NotBoundException e)
		{
			fail("Login should have been processed, and setting should work");
		}

		// player shouldn't be able to be set after having logged in without
		// first logging out
		try
		{
			pm.finishLogin(2);
			fail("Login should have already occured and it should not allow a new player to be set");
		}
		catch (AlreadyBoundException | NotBoundException e)
		{
			assertTrue(e instanceof AlreadyBoundException);
		}
	}

	/**
	 * Initialize player should send a PlayerConnectedToAreaServerReport
	 */
	@Test
	public void testInitializePlayerFiresReport()
	{
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		Position pos = new Position(1, 2);
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		List<VanityDTO> vanityDTOS = new ArrayList<>();
		VanityDTO vanityDTO = new VanityDTO();
		vanityDTOS.add(vanityDTO);
		PlayerConnectedToAreaServerReport report = new PlayerConnectedToAreaServerReport(
				1, "Player 1", pos, Crew.NULL_POINTER, Major.COMPUTER_ENGINEERING, false, vanityDTOS);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, PlayerConnectedToAreaServerReport.class);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		pm.initializePlayer(1, "Player 1", vanityDTOS, pos,
				Crew.NULL_POINTER, Major.COMPUTER_ENGINEERING, 10);

		EasyMock.verify(obs);
	}
}

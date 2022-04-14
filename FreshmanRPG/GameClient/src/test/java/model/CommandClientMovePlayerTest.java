package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;

import dataDTO.VanityDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;

/**
 * @author Josh
 * 
 */
public class CommandClientMovePlayerTest
{
	/**
	 * Create the passability map to simulate a map being loaded in.
	 */
	@Before
	public void setup()
	{
		boolean[][] passability =
		{
		{ true, true, true },
		{ true, false, true },
		{ true, true, true } };

		MapManager.getSingleton().setPassability(passability);
	}

	/**
	 * Reset the MapManager after we're done.
	 */
	@After
	public void cleanup()
	{
		MapManager.resetSingleton();
		ClientPlayerManager.resetSingleton();
	}

	/**
	 * Testing the command to move our player
	 * 
	 * @throws NotBoundException shouldn't
	 * @throws AlreadyBoundException shouldnt
	 */
	@Test
	public void testMovePlayer() throws AlreadyBoundException,
			NotBoundException
	{
		Position pos = new Position(1, 2);
		ClientPlayerManager.getSingleton().initiateLogin("1", "1");
		ClientPlayerManager.getSingleton().finishLogin(1);
		ClientPlayerManager.getSingleton().getThisClientsPlayer().setPosition(pos);
		assertEquals(new Position(1, 2), ClientPlayerManager.getSingleton().getThisClientsPlayer().getPosition());

		CommandClientMovePlayer cm = new CommandClientMovePlayer(1, new Position(1, 0));
		assertTrue(cm.execute());
		assertEquals(new Position(1, 0), ClientPlayerManager.getSingleton().getThisClientsPlayer().getPosition());
	}

	/**
	 * Let other people move anywhere they want
	 * @throws AlreadyBoundException shouldn't
	 * @throws NotBoundException shouldn't
	 */
//	@Test
//	public void testIllegalMoveNotThisClient() throws AlreadyBoundException,
//			NotBoundException
//	{
//		Position pos = new Position(1, 2);
//		ClientPlayer someGuy = ClientPlayerManager.getSingleton().initializePlayer(2, "1",
//				"1","1", pos, Crew.NULL_POINTER, Major.COMPUTER_SCIENCE, 1);
//		ClientPlayerManager.getSingleton().initiateLogin("1", "1");
//		ClientPlayerManager.getSingleton().finishLogin(1);
//		assertEquals(new Position(1, 2), someGuy.getPosition());
//
//		CommandClientMovePlayer cm = new CommandClientMovePlayer(someGuy.getID(),
//				new Position(1, 1));
//		assertTrue(cm.execute());
//		assertEquals(new Position(1, 1), someGuy.getPosition());
//	}

	/**
	 * Test attempting to move into an impassable position
	 * 
	 * @throws NotBoundException shouldn't
	 * @throws AlreadyBoundException shouldn't
	 */
	@Test
	public void testIllegalMove() throws AlreadyBoundException,
			NotBoundException
	{
		Position pos = new Position(1, 2);
		VanityDTO vanityDTO = new VanityDTO();
		List<VanityDTO> vanityDTOS = new ArrayList<>();
		vanityDTOS.add(vanityDTO);
		ClientPlayer me = ClientPlayerManager.getSingleton().initializePlayer(1, "1", vanityDTOS,
				pos, Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING, 1);
		ClientPlayerManager.getSingleton().initiateLogin("1", "1");
		ClientPlayerManager.getSingleton().finishLogin(1);
		assertEquals(new Position(1, 2), me.getPosition());

		CommandClientMovePlayer cm = new CommandClientMovePlayer(me.getID(), new Position(
				1, 1));
		assertFalse(cm.execute());
		assertEquals(new Position(1, 2), me.getPosition());
	}
}

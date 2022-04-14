package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataDTO.VanityDTO;
import datatypes.VanityType;
import org.junit.Before;
import org.junit.Test;

import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;
import view.player.Vanity;

/**
 * Tests the CommandAddOtherPlayer class
 * 
 * @author merlin
 * 
 */
public class CommandInitializePlayerTest
{

	/**
	 * Reset the PlayerManager
	 */
	@Before
	public void setup()
	{
		ClientPlayerManager.resetSingleton();
	}

	/**
	 * Just make sure that new player is added to the player manager correctly
	 */
	@Test
	public void addsNewPlayerWhoIsNotThisClientsPlayer()
	{
		Position pos = new Position(1, 2);
		VanityDTO vanityDTO = new VanityDTO();
		List<VanityDTO> vanityDTOS = new ArrayList<>();
		vanityDTOS.add(vanityDTO);
		CommandInitializePlayer cmd = new CommandInitializePlayer(4, "Henry",
				vanityDTOS, pos, Crew.NULL_POINTER, Major.COMPUTER_SCIENCE, 10);
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		assertNull(pm.getPlayerFromID(4));
		assertTrue(cmd.execute());
		ClientPlayer p = pm.getPlayerFromID(4);
		assertEquals(4, p.getID());
		assertEquals("Henry", p.getName());
		assertEquals(pos, p.getPosition());
		assertEquals(vanityDTOS, p.getVanities());
		assertEquals(Crew.NULL_POINTER, p.getCrew());
		assertEquals(Major.COMPUTER_SCIENCE, p.getMajor());
		assertEquals(10, p.getSection());
	}

	/**
	 * Just make sure that existing player is updated to the player manager
	 * correctly
	 */
	@Test
	public void updatesExistingPlayerWhoIsNotThisClientsPlayer()
	{
		Position pos = new Position(1, 2);

		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		VanityDTO vanityDTO = new VanityDTO();
		List<VanityDTO> vanityDTOS = new ArrayList<>();
		vanityDTOS.add(vanityDTO);
		ClientPlayer p = ClientPlayerManager.getSingleton().initializePlayer(4, "4", vanityDTOS,
				pos, Crew.OFF_BY_ONE, Major.COMPUTER_ENGINEERING, 4);
		assertNotNull(pm.getPlayerFromID(4));

		CommandInitializePlayer cmd = new CommandInitializePlayer(4, "Henry",
				vanityDTOS, pos, Crew.NULL_POINTER, Major.COMPUTER_ENGINEERING, 10);
		assertTrue(cmd.execute());
		p = pm.getPlayerFromID(4);
		assertEquals(4, p.getID());
		assertEquals("Henry", p.getName());
		assertEquals(pos, p.getPosition());
		assertEquals(vanityDTOS, p.getVanities());
		assertEquals(Crew.NULL_POINTER, p.getCrew());
		assertEquals(Major.COMPUTER_ENGINEERING, p.getMajor());
		assertEquals(10, p.getSection());
	}

	/**
	 * Update the already existing thisClientsPlayer
	 * 
	 * @throws NotBoundException
	 *             shouldn't
	 * @throws AlreadyBoundException
	 *             shouldn't
	 */
	@Test
	public void updatesExistingThisClientsPlayer() throws AlreadyBoundException,
			NotBoundException
	{
		Position pos = new Position(1, 2);
		VanityDTO vanityDTO = new VanityDTO();
		List<VanityDTO> vanityDTOS = new ArrayList<>();
		vanityDTOS.add(vanityDTO);
		CommandInitializePlayer cmd = new CommandInitializePlayer(4, "Henry",
				vanityDTOS, pos, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING, 10);
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		pm.initiateLogin("not", "needed");
		ClientPlayer p = pm.finishLogin(4);
		assertNotNull(pm.getPlayerFromID(4));
		assertTrue(cmd.execute());
		p = pm.getPlayerFromID(4);
		assertEquals(4, p.getID());
		assertEquals("Henry", p.getName());
		assertEquals(pos, p.getPosition());
		assertEquals(vanityDTOS, p.getVanities());
		assertEquals(Crew.OUT_OF_BOUNDS, p.getCrew());
		assertEquals(Major.COMPUTER_ENGINEERING, p.getMajor());
		assertEquals(10, p.getSection());
	}

}

package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import dataDTO.VanityDTO;
import datatypes.VanityType;
import org.junit.Before;
import org.junit.Test;

import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests the functionality of CommandChangePlayerAppearance.
 */
public class CommandChangePlayerAppearanceTest
{

	/**
	 * Setup for testing.
	 */
	@Before
	public void setUp()
	{
		ClientPlayerManager.resetSingleton();
	}

	/**
	 * When this command is executed, the client player's appearance should be updated.
	 */
	@Test
	public void testChangesAppearance()
	{
		final ClientPlayerManager manager = ClientPlayerManager.getSingleton();
		final int playerId = 1;
		List<VanityDTO> vanityDTOS = new ArrayList<>();
		VanityDTO vanityDTO = new VanityDTO();
		vanityDTOS.add(vanityDTO);
		manager.initializePlayer(playerId, "Robert", vanityDTOS, new Position(1,1),
				Crew.NULL_POINTER, Major.COMPUTER_ENGINEERING, 3);
        final VanityDTO newHat = new VanityDTO(1,"", "", "",VanityType.HAT);
        final VanityDTO newBody = new VanityDTO(2, "", "", "", VanityType.BODY);
		List<VanityDTO> vanityDTOS2 = new ArrayList<>();
		vanityDTOS2.add(newHat);
		vanityDTOS2.add(newBody);
		final CommandChangePlayerAppearance command = new CommandChangePlayerAppearance(playerId, vanityDTOS2);
		final boolean success = command.execute();

		assertTrue( success );
        assertEquals(newBody, manager.getPlayerFromID(1).getVanities().get(0));
        assertEquals(newHat, manager.getPlayerFromID(1).getVanities().get(1));
	}

}

package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;

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
		manager.initializePlayer(playerId, "Robert", "Ninja", new Position(1,1), Crew.NULL_POINTER,
				Major.COMPUTER_ENGINEERING, 3);
		final String appearanceType = "RedHat";
		final CommandChangePlayerAppearance command = new CommandChangePlayerAppearance(playerId, appearanceType);
		final boolean success = command.execute();

		assertTrue( success );
		assertEquals( appearanceType, manager.getPlayerFromID(1).getAppearanceType() );
	}

}

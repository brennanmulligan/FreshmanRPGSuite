package model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import datatypes.PlayersForTest;

/**
 * Tests that the GetAllConnectedPlayers command gets executed properly.
 * @author Nathaniel, Kanza
 *
 */
public class CommandReceiveTerminalTextTest

{
	/**
	 * If it executes, we're golden -Nathaniel
	 */
	@Test
	public void testExecute()
	{
		int playerID = PlayersForTest.MERLIN.getPlayerID();

		PlayerManager.getSingleton().getConnectedPlayers();
		CommandReceiveTerminalText cgaop = new CommandReceiveTerminalText(playerID, "who");

		assertTrue(cgaop.execute());


	}
}

package model.terminal;

import static org.junit.Assert.*;

import org.junit.Test;

import datatypes.PlayersForTest;

/**
 * @author Nathaniel and Ben
 *
 */
public class CommandTerminalTextLSTest
{

	/**
	 * Tests that we get the correct description of the command
	 */
	@Test
	public void testGetDescription()
	{
		String actual = TerminalManager.getSingleton().getTerminalCommandObject("ls").getDescription();
		String expected = "Lists maps the player has visited.";
		assertEquals(expected, actual);
	}

	/**
	 * Tests that we get the correct identifier for the command
	 */
	@Test
	public void testGetTerminalIdentifier()
	{
		String actual = TerminalManager.getSingleton().getTerminalCommandObject("ls").getTerminalIdentifier();
		String expected = "ls";
		assertEquals(expected, actual);
	}

	/**
	 * Tests that we can get the list of servers a player has visited
	 */
	@Test
	public void testMapTeleportList()
	{
		CommandTerminalTextLS ls = (CommandTerminalTextLS) TerminalManager.getSingleton().getTerminalCommandObject("ls");
		String expected = "Quiznasium, Current";
		String actual = ls.execute(PlayersForTest.MERLIN.getPlayerID(), new String[]{""});
		assertEquals(expected, actual);
	}
}

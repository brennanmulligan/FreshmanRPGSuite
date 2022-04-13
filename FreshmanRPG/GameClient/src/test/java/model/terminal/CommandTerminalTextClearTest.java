package model.terminal;

import static org.junit.Assert.*;

import datatypes.PlayersForTest;
import org.junit.Test;

/**
 * @author Nathaniel and Nahesha
 *
 */
public class CommandTerminalTextClearTest
{

	/**
	 * Tests that the execute returns null.  Because it shouldn't do anything.
	 */
	@Test
	public void testExecute()
	{
		CommandTerminalTextClear tcc = new CommandTerminalTextClear();
		assertNull(tcc.execute(PlayersForTest.DATBOI.getPlayerID(), new String[] {""}));
	}
	
	/**
	 * Tests that we get the right identifier for the command.
	 */
	@Test
	public void testGetIdentifier()
	{
		CommandTerminalTextClear tcc = new CommandTerminalTextClear();
		assertEquals("clear", tcc.getTerminalIdentifier());
	}

}

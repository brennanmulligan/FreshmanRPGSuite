package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests functionality for a command for receiving key input from the user.
 *
 * @author Ian Keefer & TJ Renninger
 *
 */
public class CommandKeyInputMessageReceivedTest
{

	/**
	 * Test creation of a CommandKeyInputMessageReceived
	 */
	@Test
	public void testInitializaiton()
	{
		String input = "q";
		int id = 1;
		CommandKeyInputMessageReceived command = new CommandKeyInputMessageReceived(input, id);
		assertEquals(input, command.getInput());

	}

	/**
	 * Test execution of a CommandKeyInputMessageReceived
	 */
	@Test
	public void testExecute()
	{
		String input = "q";
		int id = 1;
		CommandKeyInputMessageReceived command = new CommandKeyInputMessageReceived(input, id);
		assertTrue(command.execute());
	}

}

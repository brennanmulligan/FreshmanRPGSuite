package model;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Merlin
 * 
 */
public class CommandLoginTest
{

	/**
	 * reset the player to make sure the login in cleat
	 */
	@Before
	public void setup()
	{
		ClientPlayerManager.resetSingleton();
	}

	/**
	 * We just need to tell the player to initiate this
	 */
	@Test
	public void shouldTellPlayer()
	{
		CommandLogin cmd = new CommandLogin("Fred", "pw");
		cmd.execute();
		ClientPlayerManager p = ClientPlayerManager.getSingleton();
		assertTrue(p.isLoginInProgress());

	}
}

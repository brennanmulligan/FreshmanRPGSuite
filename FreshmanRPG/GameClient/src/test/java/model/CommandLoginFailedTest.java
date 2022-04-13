package model;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Matt and Andy
 *
 */
public class CommandLoginFailedTest 
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
		CommandLoginFailed cmd = new CommandLoginFailed();
		cmd.execute();
		ClientPlayerManager p = ClientPlayerManager.getSingleton();
		assertFalse(p.isLoginInProgress());

	}

}

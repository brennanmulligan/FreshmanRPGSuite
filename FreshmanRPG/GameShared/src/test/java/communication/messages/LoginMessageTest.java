package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests a login message
 *
 * @author merlin
 *
 */
public class LoginMessageTest
{
	/**
	 * Make sure its toString is correct
	 */
	@Test
	public void testToString()
	{
		LoginMessage msg = new LoginMessage("fred", "xxx");
		assertEquals("Login Message: playerName = fred and password = xxx", msg.toString());
		assertEquals("fred", msg.getPlayerName());
		assertEquals("xxx", msg.getPassword());
	}

}

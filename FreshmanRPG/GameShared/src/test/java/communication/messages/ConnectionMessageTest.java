package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests a login message
 *
 * @author merlin
 *
 */
public class ConnectionMessageTest
{
	/**
	 * Make sure its toString is correct
	 */
	@Test
	public void testToString()
	{
		ConnectMessage msg = new ConnectMessage(50, 32432);
		assertEquals("Connect Message: playerID = 50 and pin = 32432.0", msg.toString());
		assertEquals(50, msg.getPlayerID());
		assertEquals(32432, msg.getPin(), 0.0001);
	}

}

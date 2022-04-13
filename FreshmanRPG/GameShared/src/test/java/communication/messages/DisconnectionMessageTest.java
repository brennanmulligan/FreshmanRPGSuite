package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests a disconnect from area server message
 *
 * @author nhydock
 *
 */
public class DisconnectionMessageTest
{
	/**
	 * Make sure its toString is correct
	 */
	@Test
	public void testToString()
	{
		DisconnectMessage msg = new DisconnectMessage(50);
		assertEquals("Disconnect Message: playerID = 50", msg.toString());
		assertEquals(50, msg.getPlayerID());
	}

}

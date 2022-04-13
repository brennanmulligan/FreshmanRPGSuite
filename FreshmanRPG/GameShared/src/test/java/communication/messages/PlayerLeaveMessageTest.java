package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests a player disconnect message
 *
 * @author nhydock
 *
 */
public class PlayerLeaveMessageTest
{
	/**
	 * Make sure its toString reporting is correct
	 */
	@Test
	public void testToString()
	{
		PlayerLeaveMessage msg = new PlayerLeaveMessage(1);
		assertEquals("PlayerLeaveMessage: playerID = 1", msg.toString());
	}

}

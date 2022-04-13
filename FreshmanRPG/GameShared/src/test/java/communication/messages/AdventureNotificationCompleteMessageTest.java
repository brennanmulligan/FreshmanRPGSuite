package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the AdventureNotificationCompleteMessage functionality
 *
 * @author Ryan
 *
 */
public class AdventureNotificationCompleteMessageTest
{

	/**
	 * Tests constructor & getters for AdventureNotificationCompleteMessage
	 */
	@Test
	public void testInitialization()
	{
		AdventureNotificationCompleteMessage msg = new AdventureNotificationCompleteMessage(1, 2, 1);
		assertEquals(1, msg.getPlayerID());
		assertEquals(2, msg.getQuestID());
		assertEquals(1, msg.getAdventureID());
	}

}

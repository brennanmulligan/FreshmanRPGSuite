package communication.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import datatypes.AdventureStateEnum;

/**
 * @author Ryan
 *
 */
public class AdventureStateChangeMessageTest
{

	/**
	 * Test initialization of message
	 */
	@Test
	public void testInit()
	{
		AdventureStateChangeMessage msg = new AdventureStateChangeMessage(1, 2, 3, "Big Adventure",
				AdventureStateEnum.TRIGGERED, true, "Provost");

		assertEquals(1, msg.getPlayerID());
		assertEquals(2, msg.getQuestID());
		assertEquals(3, msg.getAdventureID());
		assertEquals("Big Adventure", msg.getAdventureDescription());
		assertEquals(AdventureStateEnum.TRIGGERED, msg.getNewState());
		assertTrue(msg.isRealLifeAdventure());
		assertEquals("Provost", msg.getWitnessTitle());
	}

}

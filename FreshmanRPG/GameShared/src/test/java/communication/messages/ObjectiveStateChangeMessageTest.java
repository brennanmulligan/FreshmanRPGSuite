package communication.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import datatypes.ObjectiveStateEnum;

/**
 * @author Ryan
 *
 */
public class ObjectiveStateChangeMessageTest
{

	/**
	 * Test initialization of message
	 */
	@Test
	public void testInit()
	{
		ObjectiveStateChangeMessage msg = new ObjectiveStateChangeMessage(1, 2, 3, "Big Objective",
				ObjectiveStateEnum.TRIGGERED, true, "Provost");

		assertEquals(1, msg.getPlayerID());
		assertEquals(2, msg.getQuestID());
		assertEquals(3, msg.getObjectiveID());
		assertEquals("Big Objective", msg.getObjectiveDescription());
		assertEquals(ObjectiveStateEnum.TRIGGERED, msg.getNewState());
		assertTrue(msg.isRealLifeObjective());
		assertEquals("Provost", msg.getWitnessTitle());
	}

}

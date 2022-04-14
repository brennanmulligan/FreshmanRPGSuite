package view.screen.popup;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test for objective complete behavior
 * @author Ryan
 *
 */
public class ObjectiveCompleteBehaviorTest
{

	/**
	 * Test the getters and initialization of the behavior
	 */
	@Test
	public void testInitialization() 
	{
		ObjectiveNotificationCompleteBehavior behavior = new ObjectiveNotificationCompleteBehavior(1, 1, 1);
		
		assertEquals(1, behavior.getPlayerID());
		assertEquals(1, behavior.getQuestID());
		assertEquals(1, behavior.getObjectiveID());
		
	}

}

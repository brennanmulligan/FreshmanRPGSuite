package edu.ship.engr.shipsim.view.screen.popup;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for objective complete behavior
 *
 * @author Ryan
 */
@GameTest("GameClient")
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

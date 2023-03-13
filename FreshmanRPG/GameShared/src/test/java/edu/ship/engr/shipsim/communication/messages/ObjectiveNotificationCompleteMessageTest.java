package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the ObjectiveNotificationCompleteMessage functionality
 *
 * @author Ryan
 */
@GameTest("GameShared")
public class ObjectiveNotificationCompleteMessageTest
{

    /**
     * Tests constructor & getters for ObjectiveNotificationCompleteMessage
     */
    @Test
    public void testInitialization()
    {
        ObjectiveNotificationCompleteMessage msg = new ObjectiveNotificationCompleteMessage(1, false,2, 1);
        assertEquals(1, msg.getRelevantPlayerID());
        assertEquals(2, msg.getQuestID());
        assertEquals(1, msg.getObjectiveID());
    }

}

package edu.ship.engr.shipsim.communication.messages;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the ObjectiveNotificationCompleteMessage functionality
 *
 * @author Ryan
 */
public class ObjectiveNotificationCompleteMessageTest
{

    /**
     * Tests constructor & getters for ObjectiveNotificationCompleteMessage
     */
    @Test
    public void testInitialization()
    {
        ObjectiveNotificationCompleteMessage msg = new ObjectiveNotificationCompleteMessage(1, 2, 1);
        assertEquals(1, msg.getPlayerID());
        assertEquals(2, msg.getQuestID());
        assertEquals(1, msg.getObjectiveID());
    }

}

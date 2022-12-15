package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.ObjectiveNotificationCompleteMessage;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for ObjectiveNotificationCompleteMessageHandler
 *
 * @author Ryan
 */
@GameTest("GameServer")
@ResetModelFacade
@ResetPlayerManager
public class ObjectiveNotificationCompleteMessageHandlerTest
{
    /**
     * Test what message type we handle
     */
    @Test
    public void testMessageWeHandle()
    {
        ObjectiveNotificationCompleteMessageHandler h = new ObjectiveNotificationCompleteMessageHandler();

        assertEquals(ObjectiveNotificationCompleteMessage.class, h.getMessageTypeWeHandle());
    }
}

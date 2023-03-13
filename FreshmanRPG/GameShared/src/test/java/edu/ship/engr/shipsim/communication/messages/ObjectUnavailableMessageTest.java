package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the functionality of the ObjectInRangeMessage
 *
 * @author ag0612
 * @author jk1964
 * @author tc9538
 */
@GameTest("GameShared")
public class ObjectUnavailableMessageTest
{

    /**
     * Tests that an ObjectInRangeMessage can return a playerID
     */
    @Test
    public void testGetPlayerID()
    {
        int playerID = 23;
        InteractionDeniedMessage msg = new InteractionDeniedMessage(playerID, false);
        assertEquals(playerID, msg.getRelevantPlayerID());
    }

    /**
     * Makes sure the toString correct
     */
    @Test
    public void testToString()
    {
        int playerID = 5;
        InteractionDeniedMessage msg = new InteractionDeniedMessage(playerID, false);
        assertEquals("ObjectUnavailableMessage: playerID = 5", msg.toString());
    }

}

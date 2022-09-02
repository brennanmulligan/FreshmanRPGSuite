package edu.ship.engr.shipsim.communication.messages;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functionality of the ObjectInRangeMessage
 *
 * @author ag0612
 * @author jk1964
 * @author tc9538
 */
public class ObjectUnavailableMessageTest
{

    /**
     * Tests that an ObjectInRangeMessage can return a playerID
     */
    @Test
    public void testGetPlayerID()
    {
        int playerID = 23;
        InteractionDeniedMessage msg = new InteractionDeniedMessage(playerID);
        assertEquals(playerID, msg.getPlayerID());
    }

    /**
     * Makes sure the toString correct
     */
    @Test
    public void testToString()
    {
        int playerID = 5;
        InteractionDeniedMessage msg = new InteractionDeniedMessage(playerID);
        assertEquals("ObjectUnavailableMessage: playerID = 5", msg.toString());
    }

}

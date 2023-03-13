package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests a login message
 *
 * @author merlin
 */
@GameTest("GameShared")
public class ConnectionMessageTest
{
    /**
     * Make sure its toString is correct
     */
    @Test
    public void testToString()
    {
        ConnectMessage msg = new ConnectMessage(50,false, 32432);
        assertEquals("Connect Message: playerID = 50 and pin = 32432.0", msg.toString());
        assertEquals(50, msg.getRelevantPlayerID());
        assertEquals(32432, msg.getPin(), 0.0001);
        assertFalse(msg.isQuietMessage());
    }

}

package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for testing NoMoreBuffMessage
 */
@GameTest("GameShared")
public class NoMoreBuffMessageTest
{
    /**
     * test that the tostring method of the nomorebuff message returns the correct string
     */
    @Test
    public void testToString()
    {
        NoMoreBuffMessage message = new NoMoreBuffMessage(1, false);

        assertEquals("NoMoreBuffMessage: No remaining buff for playerID = 1", message.toString());
    }
}

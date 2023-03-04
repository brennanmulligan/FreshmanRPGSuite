package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests functionality for a key input message
 *
 * @author Ian Keefer & TJ Renninger
 */
@GameTest("GameShared")
public class KeyInputMessageTest
{

    /**
     * Test creation of a key input message
     */
    @Test
    public void testInitialization()
    {
        String input = "q";
        KeyInputMessage keyInputMessage = new KeyInputMessage(input,false);
        assertEquals(input, keyInputMessage.getInput());
    }

}

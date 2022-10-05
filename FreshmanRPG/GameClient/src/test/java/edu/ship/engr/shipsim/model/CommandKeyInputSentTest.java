package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Command to test key input command
 *
 * @author Ian Keefer & TJ Renninger
 */
@GameTest("GameClient")
public class CommandKeyInputSentTest
{

    /**
     * Test initialization of a CommandKeyInputSent
     */
    @Test
    public void testInitialization()
    {
        String input = "q";
        CommandKeyInputSent cis = new CommandKeyInputSent(input);
        assertEquals(input, cis.getInput());
        assertTrue(cis.execute());
    }

}

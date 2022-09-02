package edu.ship.engr.shipsim.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Command to test key input command
 *
 * @author Ian Keefer & TJ Renninger
 */
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

package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests functionality for a command for receiving key input from the user.
 *
 * @author Ian Keefer & TJ Renninger
 */
@GameTest("GameServer")
public class CommandKeyInputMessageReceivedTest
{

    /**
     * Test creation of a CommandKeyInputMessageReceived
     */
    @Test
    public void testInitializaiton()
    {
        String input = "q";
        int id = 1;
        CommandKeyInputMessageReceived command = new CommandKeyInputMessageReceived(input, id);
        assertEquals(input, command.getInput());

    }

    /**
     * Test execution of a CommandKeyInputMessageReceived
     */
    @Test
    public void testExecute()
    {
        String input = "q";
        int id = 1;
        CommandKeyInputMessageReceived command = new CommandKeyInputMessageReceived(input, id);
        assertTrue(command.execute());
    }

}

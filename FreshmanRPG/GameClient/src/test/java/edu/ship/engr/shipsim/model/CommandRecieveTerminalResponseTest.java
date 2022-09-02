package edu.ship.engr.shipsim.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the CommandRecieveTerminalText class
 *
 * @author Nathaniel, Ben
 */
public class CommandRecieveTerminalResponseTest
{

    int playerID = 1;
    String terminalText = "Yep, that's some text";

    /**
     * Tests that we can create Command and set/get its fields
     */
    @Test
    public void testCreateCommand()
    {
        int playerID = 1;
        String terminalText = "Yep, that's some text";

        CommandRecieveTerminalResponse cmd = new CommandRecieveTerminalResponse(playerID, terminalText);
        assertEquals(playerID, cmd.getPlayerID());
        assertEquals(terminalText, cmd.getTerminalResult());
    }

    /**
     * Tests that the command executes
     */
    @Test
    public void testExecute()
    {
        CommandRecieveTerminalResponse cmd = new CommandRecieveTerminalResponse(playerID, terminalText);
        assertTrue(cmd.execute());
    }

}

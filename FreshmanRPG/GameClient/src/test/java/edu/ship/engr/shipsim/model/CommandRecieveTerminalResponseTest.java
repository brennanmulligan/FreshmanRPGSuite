package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the CommandRecieveTerminalText class
 *
 * @author Nathaniel, Ben
 */
@GameTest("GameClient")
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

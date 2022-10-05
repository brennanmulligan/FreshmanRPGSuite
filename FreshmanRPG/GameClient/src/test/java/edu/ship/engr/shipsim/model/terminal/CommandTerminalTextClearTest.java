package edu.ship.engr.shipsim.model.terminal;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Nathaniel and Nahesha
 */
@GameTest("GameClient")
public class CommandTerminalTextClearTest
{

    /**
     * Tests that the execute returns null.  Because it shouldn't do anything.
     */
    @Test
    public void testExecute()
    {
        CommandTerminalTextClear tcc = new CommandTerminalTextClear();
        assertNull(tcc.execute(PlayersForTest.DATBOI.getPlayerID(), new String[]{""}));
    }

    /**
     * Tests that we get the right identifier for the command.
     */
    @Test
    public void testGetIdentifier()
    {
        CommandTerminalTextClear tcc = new CommandTerminalTextClear();
        assertEquals("clear", tcc.getTerminalIdentifier());
    }

}

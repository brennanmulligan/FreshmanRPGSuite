package edu.ship.engr.shipsim.model.terminal;

import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Nathaniel and Ben
 */
public class CommandTerminalTextLocationsTest extends ServerSideTest
{

    /**
     * Tests that we get the correct description of the command
     */
    @Test
    public void testGetDescription()
    {
        String actual = TerminalManager.getSingleton().getTerminalCommandObject("ls").getDescription();
        String expected = "Lists maps the player has visited.";
        assertEquals(expected, actual);
    }

    /**
     * Tests that we get the correct identifier for the command
     */
    @Test
    public void testGetTerminalIdentifier()
    {
        String actual = TerminalManager.getSingleton().getTerminalCommandObject("ls").getTerminalIdentifier();
        String expected = "ls";
        assertEquals(expected, actual);
    }

    /**
     * Tests that we can get the list of servers a player has visited
     */
    @Test
    public void testMapTeleportList()
    {
        CommandTerminalTextLocations
                ls = (CommandTerminalTextLocations) TerminalManager.getSingleton().getTerminalCommandObject("ls");
        String expected = "Rec Center, Quad";
        String actual = ls.execute(PlayersForTest.MERLIN.getPlayerID(), new String[]{""});
        assertEquals(expected, actual);
    }
}

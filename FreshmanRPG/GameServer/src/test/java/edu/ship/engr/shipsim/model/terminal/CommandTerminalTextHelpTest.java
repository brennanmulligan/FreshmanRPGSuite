package edu.ship.engr.shipsim.model.terminal;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Nathaniel and Nahesha
 */
@GameTest("GameServer")
public class CommandTerminalTextHelpTest
{

    /**
     * Tests that we get the correct response if a command does exist
     */
    @Test
    public void testExecuteFindsCommand()
    {
        CommandTerminalTextHelp
                ttm = (CommandTerminalTextHelp) TerminalManager.getSingleton().getTerminalCommandObject("man");
        CommandTerminalTextWho ttw = (CommandTerminalTextWho) TerminalManager.getSingleton().getTerminalCommandObject("who");
        String found = "Name: who \n Description: " + ttw.getDescription();
        assertEquals(found, ttm.execute(PlayersForTest.DATBOI.getPlayerID(), new String[]{"who"}));
    }

    /**
     * Tests that we get the correct response if a command doesn't exist
     */
    @Test
    public void testExecuteDoesNotFindCommand()
    {
        CommandTerminalTextHelp
                ttm = (CommandTerminalTextHelp) TerminalManager.getSingleton().getTerminalCommandObject("man");
        String notFound = "No manual entry for NonExistent.";
        assertEquals(notFound, ttm.execute(PlayersForTest.FRANK.getPlayerID(), new String[]{"NonExistent"}));
    }

    /**
     * Test that typing just man says "What manual page do you want?"
     */
    @Test
    public void testExectuteManWithEmptyParam()
    {
        CommandTerminalTextHelp
                ttm = (CommandTerminalTextHelp) TerminalManager.getSingleton().getTerminalCommandObject("man");

        // Using this string builder to mock what the functionality would look like
        StringBuilder sb = new StringBuilder();
        sb.append("The following is a list of commands, their syntax, and description:\n");
        sb.append("-- cd <map_name> - For every new location you visit, you can teleport to it.\n");
        sb.append("-- finger <player_name> - Find the profile for another player.\n");
        sb.append("-- friend list - Returns a list of the player's friends.\n");
        sb.append("-- friend add <player_name> - Sends a friend request to that player, can input multiple names.\n");
        sb.append("-- friend accept <player_name> - Accepts a friend request from that player, can input multiple names.\n");
        sb.append("-- ls - Lists maps the player has visited.\n");
        sb.append("-- man <command> - Displays the description of a command.\n");
        sb.append("-- who - Prints information about users who are currently logged in.\n");
        sb.append("-- whoami - Prints out information on your player.\n");
        sb.append("-- exit - Type 'exit' to logout of your account.\n");

        String notFound = sb.toString();
        // Previous functionality of man
        // String notFound = "What manual page do you want?";
        assertEquals(notFound, ttm.execute(PlayersForTest.FRANK.getPlayerID(), new String[]{""}));
    }

}

package edu.ship.engr.shipsim.model.terminal;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for CommandTerminalTextWhoIs
 *
 * @author as3871
 */
@GameTest("GameServer")
public class CommandTerminalTextWhoIsTest
{

    /**
     * Tests that we get the correct description of the command
     */
    @Test
    public void testGetDescription()
    {
        String actual = TerminalManager.getSingleton().getTerminalCommandObject("finger").getDescription();
        String expected = "Find the profile for another player.";
        assertEquals(expected, actual);
    }

    /**
     * Tests that we get the correct identifier for the command
     */
    @Test
    public void testGetTerminalIdentifier()
    {
        String actual = TerminalManager.getSingleton().getTerminalCommandObject("finger").getTerminalIdentifier();
        String expected = "finger";
        assertEquals(expected, actual);
    }

    /**
     * Tests that you can finger another person who is online and not on the same map
     */
    @Test
    public void testThatAnOnlinePlayerCanFingerAnotherNotOnSameMap()
    {
        CommandTerminalTextWhoIs fingerCommand = new CommandTerminalTextWhoIs();
        String actual = fingerCommand.execute(PlayersForTest.NICK.getPlayerID(), new String[]{"Merlin"});
        assertTrue(PlayersForTest.MERLIN.getOnline());
        assertNotEquals(PlayersForTest.MERLIN.getMapName(), PlayersForTest.NICK.getMapName());

        //build expected player profile
        PlayersForTest merlin = PlayersForTest.MERLIN;
        String expected = "Name: " + merlin.getPlayerName() + "," +
                "Crew: " + merlin.getCrew() + "," +
                "Major: " + merlin.getMajor() + "," +
                "Doubloons: " + merlin.getDoubloons() + "," +
                "Current Experience: " + merlin.getExperiencePoints() + ",";
        expected = fingerCommand.formatString(expected);
        assertEquals(expected, actual);
    }

    /**
     * Tests that we can finger a player that is offline
     */
    @Test
    public void testThatAnOnlinePlayerCanFingerAnOfflinePlayer()
    {
        CommandTerminalTextWhoIs fingerCommand = new CommandTerminalTextWhoIs();
        assertFalse(PlayersForTest.MARTY.getOnline());
        String actual = fingerCommand.execute(PlayersForTest.ANDY.getPlayerID(), new String[]{"Marty"});
        assertNotEquals(PlayersForTest.MARTY.getMapName(), PlayersForTest.ANDY.getMapName());

//		build expected player profile
        PlayersForTest marty = PlayersForTest.MARTY;
        String expected = "Name: " + marty.getPlayerName() + "," +
                "Crew: " + marty.getCrew() + "," +
                "Major: " + marty.getMajor() + "," +
                "Doubloons: " + marty.getDoubloons() + "," +
                "Current Experience: " + marty.getExperiencePoints() + ",";

        expected = fingerCommand.formatString(expected);
        assertEquals(expected, actual);
    }


    /**
     * Test that you can Finger someone on the same server
     */
    @Test
    public void testThatAnOnlinePlayerCanFingerAnotherOnSameMap()
    {
        CommandTerminalTextWhoIs fingerCommand = new CommandTerminalTextWhoIs();
        assertTrue(PlayersForTest.MERLIN.getOnline());
        assertTrue(PlayersForTest.GA.getOnline());
        String actual = fingerCommand.execute(PlayersForTest.ANDY.getPlayerID(), new String[]{"GA"});
        assertEquals(PlayersForTest.MERLIN.getMapName(), PlayersForTest.GA.getMapName());

//		build expected player profile
        PlayersForTest ga = PlayersForTest.GA;
        String expected = "Name: " + ga.getPlayerName() + "," +
                "Crew: " + ga.getCrew() + "," +
                "Major: " + ga.getMajor() + "," +
                "Doubloons: " + ga.getDoubloons() + "," +
                "Current Experience: " + ga.getExperiencePoints() + ",";

        expected = fingerCommand.formatString(expected);
        assertEquals(expected, actual);
    }

    /**
     * Test that we when look for a player that does not exists it tells them
     */
    @Test
    public void testGetPlayerNotFound()
    {
        CommandTerminalTextWhoIs fingerCommand = new CommandTerminalTextWhoIs();
        String playerName = "IAmNotARealPlayer";
        String actual = fingerCommand.execute(PlayersForTest.JOHN.getPlayerID(), new String[]{playerName});
        String expected = "Player " + playerName + " not found.";
        assertEquals(expected, actual);
    }

}

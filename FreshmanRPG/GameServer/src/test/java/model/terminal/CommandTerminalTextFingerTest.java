package model.terminal;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;

import org.junit.Test;

import dataDTO.LevelManagerDTO;
import datasource.DatabaseException;
import datatypes.PlayersForTest;

/**
 * Test class for CommandTerminalTextFinger
 * @author as3871
 *
 */
public class CommandTerminalTextFingerTest
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
	 * @throws DatabaseException
	 */
	@Test
	public void testThatAnOnlinePlayerCanFingerAnotherNotOnSameMap() throws DatabaseException
	{
		CommandTerminalTextFinger fingerCommand = new CommandTerminalTextFinger();
		String actual = fingerCommand.execute(PlayersForTest.NICK.getPlayerID(), new String[]{"Merlin"});
		assertTrue(PlayersForTest.MERLIN.getOnline());
		assertNotEquals(PlayersForTest.MERLIN.getMapName(), PlayersForTest.NICK.getMapName());

		//build expected player profile
		PlayersForTest merlin = PlayersForTest.MERLIN;
		String dateString = "";
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		dateString = sdf.format(LevelManagerDTO.getSingleton().getLevelForPoints(merlin.getExperiencePoints()).getDeadlineDate());

		String expected = "Name: " + merlin.getPlayerName() + "," +
				"Crew: " + merlin.getCrew() + "," +
				"Major: " + merlin.getMajor() + "," +
				"Knowledge Points: " + merlin.getKnowledgeScore() + "," +
				"Current Experience: " + merlin.getExperiencePoints() + ",";
		expected = fingerCommand.formatString(expected);
		assertEquals(expected, actual);
	}

	/**
	 * Tests that we can finger a player that is offline
	 * @throws DatabaseException
	 */
	@Test
	public void testThatAnOnlinePlayerCanFingerAnOfflinePlayer() throws DatabaseException
	{
		CommandTerminalTextFinger fingerCommand = new CommandTerminalTextFinger();
		assertFalse(PlayersForTest.MARTY.getOnline());
		String actual = fingerCommand.execute(PlayersForTest.ANDY.getPlayerID(), new String[]{"Marty"});
		assertNotEquals(PlayersForTest.MARTY.getMapName(), PlayersForTest.ANDY.getMapName());

//		build expected player profile
		PlayersForTest marty = PlayersForTest.MARTY;
		String dateString = "";
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		dateString = sdf.format(LevelManagerDTO.getSingleton().getLevelForPoints(marty.getExperiencePoints()).getDeadlineDate());

		String expected = "Name: " + marty.getPlayerName() + "," +
				"Crew: " + marty.getCrew() + "," +
				"Major: " + marty.getMajor() + "," +
				"Knowledge Points: " + marty.getKnowledgeScore() + "," +
				"Current Experience: " + marty.getExperiencePoints() + ",";

		expected = fingerCommand.formatString(expected);
		assertEquals(expected, actual);
	}


	/**
	 * Test that you can Finger someone on the same server
	 * @throws DatabaseException
	 */
	@Test
	public void testThatAnOnlinePlayerCanFingerAnotherOnSameMap() throws DatabaseException
	{
		CommandTerminalTextFinger fingerCommand = new CommandTerminalTextFinger();
		assertTrue(PlayersForTest.MERLIN.getOnline());
		assertTrue(PlayersForTest.GA.getOnline());
		String actual = fingerCommand.execute(PlayersForTest.ANDY.getPlayerID(), new String[]{"GA"});
		assertEquals(PlayersForTest.MERLIN.getMapName(), PlayersForTest.GA.getMapName());

//		build expected player profile
		PlayersForTest ga = PlayersForTest.GA;
		String dateString = "";
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		dateString = sdf.format(LevelManagerDTO.getSingleton().getLevelForPoints(ga.getExperiencePoints()).getDeadlineDate());

		String expected = "Name: " + ga.getPlayerName() + "," +
				"Crew: " + ga.getCrew() + "," +
				"Major: " + ga.getMajor() + "," +
				"Knowledge Points: " + ga.getKnowledgeScore() + "," +
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
		CommandTerminalTextFinger fingerCommand = new CommandTerminalTextFinger();
		String playerName = "IAmNotARealPlayer";
		String actual = fingerCommand.execute(PlayersForTest.JOHN.getPlayerID(), new String[]{playerName});
		String expected = "Player " + playerName + " not found.";
		assertEquals(expected, actual);
	}

}

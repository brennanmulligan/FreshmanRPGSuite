package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the CommandAdventureNotificationComplete class
 * @author Ryan
 *
 */
public class CommandAdventureNotificationCompleteTest 
{

	/**
	 * Tests that we can create Command and set/get its fields
	 */
	@Test
	public void testCreateCommand() 
	{
		int playerID = 1;
		int questID = 1;
		int adventureID = 1;
		
		CommandAdventureNotificationComplete cmd = new CommandAdventureNotificationComplete(playerID, questID, adventureID);
		assertEquals(playerID, cmd.getPlayerID());
		assertEquals(questID, cmd.getQuestID());
		assertEquals(adventureID, cmd.getAdventureID());
	}

}

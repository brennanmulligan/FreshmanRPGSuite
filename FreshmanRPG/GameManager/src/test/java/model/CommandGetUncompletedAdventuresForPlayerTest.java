package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Test;

import datasource.DatabaseException;
import model.reports.PlayersUncompletedAdventuresReport;
import datatypes.PlayersForTest;

/**
 * Tests behaviors for command to get uncompleted adventures for a player
 *
 */
public class CommandGetUncompletedAdventuresForPlayerTest
{

	private int playerID = PlayersForTest.MERLIN.getPlayerID();

	/**
	 * Tests generating a report
	 * @throws DatabaseException - shouldn't
	 */
	@Test
	public void testGeneratesReport() throws DatabaseException
	{

		MockQualifiedObserver receiver = new MockQualifiedObserver(PlayersUncompletedAdventuresReport.class);

		CommandGetUncompletedAdventuresForPlayer command = new CommandGetUncompletedAdventuresForPlayer(playerID);
		command.execute();

		assertEquals(((PlayersUncompletedAdventuresReport) receiver.getReport()).getAllUncompletedAdventures(), GameManagerQuestManager.getInstance().getIncompleteAdventures(playerID));
	}

	/**
	 * Tests to see if the command returns false when a database error occurs
	 */
	@Test
	public void testErrorIsThrown()
	{
		try
		{
			GameManagerQuestManager.setSingleton(new FailingQuestManager());
		}
		catch (DatabaseException e)
		{
			fail("Test failed because the singleton instance could not be replaced.");
		}

		CommandGetUncompletedAdventuresForPlayer command = new CommandGetUncompletedAdventuresForPlayer(playerID);
		assertFalse(command.execute());
		GameManagerQuestManager.resetSingleton();
	}

	/**
	 * Forces an exception when you try to send the uncompleted adventures report
	 *
	 */
	class FailingQuestManager extends GameManagerQuestManager
	{

		/**
		 * @throws DatabaseException shouldn't
		 */
		FailingQuestManager() throws DatabaseException
		{
			super();
		}

		/**
		 * @see model.GameManagerQuestManager#sendUncompletedAdventuresReport(int)
		 */
		@Override
		public void sendUncompletedAdventuresReport(int playerID) throws DatabaseException
		{
			throw new DatabaseException("Test exception");
		}
	}
}

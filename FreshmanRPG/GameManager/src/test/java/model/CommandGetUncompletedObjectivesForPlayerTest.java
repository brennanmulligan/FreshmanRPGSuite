package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Test;

import datasource.DatabaseException;
import model.reports.PlayersUncompletedObjectivesReport;
import datatypes.PlayersForTest;

/**
 * Tests behaviors for command to get uncompleted objectives for a player
 *
 */
public class CommandGetUncompletedObjectivesForPlayerTest
{

	private int playerID = PlayersForTest.MERLIN.getPlayerID();

	/**
	 * Tests generating a report
	 * @throws DatabaseException - shouldn't
	 */
	@Test
	public void testGeneratesReport() throws DatabaseException
	{

		MockQualifiedObserver receiver = new MockQualifiedObserver(PlayersUncompletedObjectivesReport.class);

		CommandGetUncompletedObjectivesForPlayer command = new CommandGetUncompletedObjectivesForPlayer(playerID);
		command.execute();

		assertEquals(((PlayersUncompletedObjectivesReport) receiver.getReport()).getAllUncompletedObjectives(), GameManagerQuestManager.getInstance().getIncompleteObjectives(playerID));
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

		CommandGetUncompletedObjectivesForPlayer command = new CommandGetUncompletedObjectivesForPlayer(playerID);
		assertFalse(command.execute());
		GameManagerQuestManager.resetSingleton();
	}

	/**
	 * Forces an exception when you try to send the uncompleted objectives report
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
		 * @see model.GameManagerQuestManager#sendUncompletedObjectivesReport(int)
		 */
		@Override
		public void sendUncompletedObjectivesReport(int playerID) throws DatabaseException
		{
			throw new DatabaseException("Test exception");
		}
	}
}

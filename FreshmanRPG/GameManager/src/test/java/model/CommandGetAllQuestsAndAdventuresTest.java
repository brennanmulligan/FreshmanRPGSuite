package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Test;

import datasource.DatabaseException;
import model.reports.AllQuestsAndAdventuresReport;

/**
 * @author da8289
 * Test behavior of the GetAllQuestsAndAdventuresCommand
 *
 */
public class CommandGetAllQuestsAndAdventuresTest
{

	/**
	 * The command should tell it's receiver to send a AllQuestsAndAdventuresReport.
	 *
	 * @throws DatabaseException - shouldn't
	 */
	@Test
	public void testGeneratesReport() throws DatabaseException
	{
		MockQualifiedObserver receiver = new MockQualifiedObserver(AllQuestsAndAdventuresReport.class);

		CommandGetAllQuestsAndAdventures command = new CommandGetAllQuestsAndAdventures();
		command.execute();

		assertEquals(((AllQuestsAndAdventuresReport) receiver.getReport()).getQuestInfo(), GameManagerQuestManager.getInstance().getQuests());
	}

	/**
	 * Check to see if the command returns false when a database error occurs.
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

		CommandGetAllQuestsAndAdventures command = new CommandGetAllQuestsAndAdventures();
		assertFalse(command.execute());
		GameManagerQuestManager.resetSingleton();
	}

}

/**
 * Forces an exception when we try to send a quest report
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
	 * @see model.GameManagerQuestManager#sendQuestReport()
	 */
	@Override
	public void sendQuestReport() throws DatabaseException
	{
		throw new DatabaseException("Test exception");
	}

}

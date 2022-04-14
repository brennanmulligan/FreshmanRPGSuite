package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Test;

import datasource.DatabaseException;
import model.reports.AllQuestsAndObjectivesReport;

/**
 * @author da8289
 * Test behavior of the GetAllQuestsAndObjectivesCommand
 *
 */
public class CommandGetAllQuestsAndObjectivesTest
{

	/**
	 * The command should tell it's receiver to send a AllQuestsAndObjectivesReport.
	 *
	 * @throws DatabaseException - shouldn't
	 */
	@Test
	public void testGeneratesReport() throws DatabaseException
	{
		MockQualifiedObserver receiver = new MockQualifiedObserver(AllQuestsAndObjectivesReport.class);

		CommandGetAllQuestsAndObjectives command = new CommandGetAllQuestsAndObjectives();
		command.execute();

		assertEquals(((AllQuestsAndObjectivesReport) receiver.getReport()).getQuestInfo(), GameManagerQuestManager.getInstance().getQuests());
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

		CommandGetAllQuestsAndObjectives command = new CommandGetAllQuestsAndObjectives();
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

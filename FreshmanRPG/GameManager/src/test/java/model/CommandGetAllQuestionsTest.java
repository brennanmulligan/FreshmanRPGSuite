package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import model.reports.QuestionListReport;

/**
 * Tests the functionality of the CommandGetAllQuestions.
 *
 */
public class CommandGetAllQuestionsTest
{

	/**
	 *
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	/**
	 * The command should tell it's receiver to send a QuestionListReport.
	 *
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	@Test
	public void testGeneratesReport() throws DatabaseException
	{
		MockQualifiedObserver receiver = new MockQualifiedObserver(QuestionListReport.class);

		CommandGetAllQuestions command = new CommandGetAllQuestions();
		command.execute();

		assertEquals(((QuestionListReport) receiver.getReport()).getQuestions(),
				QuestionManager.getInstance().getQuestions());
	}

	/**
	 * Check to see if the command returns false when a database error occurs.
	 */
	@Test
	public void testErrorIsThrown()
	{
		try
		{
			QuestionManager.setSingleton(new FailingQuestionManager());
		}
		catch (DatabaseException e)
		{
			fail("Test failed because the singleton instance could not be replaced.");
		}

		CommandGetAllQuestions command = new CommandGetAllQuestions();
		assertFalse(command.execute());
		QuestionManager.resetSingleton();
	}
}

/**
 * Forces an exception when you try to refresh the question lsit
 *
 */
class FailingQuestionManager extends QuestionManager
{

	/**
	 * @throws DatabaseException
	 *             shouldn't
	 */
	FailingQuestionManager() throws DatabaseException
	{
		super();
	}

	/**
	 * @see model.QuestionManager#refreshQuestionList()
	 */
	@Override
	public void refreshQuestionList() throws DatabaseException
	{
		throw new DatabaseException("Test exception");
	}

}

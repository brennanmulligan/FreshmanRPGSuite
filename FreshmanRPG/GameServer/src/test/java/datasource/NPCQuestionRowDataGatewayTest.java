package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Test;

import datatypes.NPCQuestionsForTest;

/**
 * Tests required of all player gateways
 *
 * @author Merlin
 *
 */
public abstract class NPCQuestionRowDataGatewayTest extends DatabaseTest
{

	protected NPCQuestionRowDataGateway gateway;

	/**
	 * Make sure any static information is cleaned up between tests
	 *
	 * @throws SQLException
	 *             shouldn't
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@After
	public void tearDown() throws DatabaseException, SQLException
	{
		super.tearDown();
		if (gateway != null)
		{
			gateway.resetData();
		}
	}

	/**
	 * Make sure we can retrieve a specific question
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void finder() throws DatabaseException
	{
		NPCQuestionsForTest question = NPCQuestionsForTest.ONE;
		gateway = findGateway(NPCQuestionsForTest.ONE.getQuestionID());
		assertEquals(question.getQuestionID(), gateway.getQuestionID());
		assertEquals(question.getQ(), gateway.getQuestionStatement());
		assertEquals(question.getA(), gateway.getAnswer());

		assertEquals(question.getStartDate(), gateway.getStartDate());
		assertEquals(question.getEndDate(), gateway.getEndDate());
	}

	/**
	 * Tests to see if we can delete a question from the database
	 *
	 * @throws DatabaseException
	 *             Will be thrown when looking for a deleted question
	 */
	@Test
	public void deleteQuestionNoException() throws DatabaseException
	{
		gateway = findGateway(NPCQuestionsForTest.ONE.getQuestionID());

		try
		{
			gateway.delete();
		}
		catch (DatabaseException e)
		{
			fail("The delete operation should not have thrown an exception.");
		}
	}

	/**
	 * Tests to see if we can delete a question from the database
	 *
	 * @throws DatabaseException
	 *             will be thrown when looking for a deleted question
	 */
	@Test(expected = DatabaseException.class)
	public void deleteQuestion() throws DatabaseException
	{
		try
		{
			gateway = findGateway(NPCQuestionsForTest.ONE.getQuestionID());
		}
		catch (DatabaseException e)
		{
			fail("An exception was thrown before the delete operation could be tested.");
		}

		gateway.delete();
		findGateway(NPCQuestionsForTest.ONE.getQuestionID());
	}

	/**
	 * Tests to see if we can update the NPCQuestion database table
	 *
	 * @throws DatabaseException
	 *             Shouldn't
	 */
	@Test
	public void updateQuestionRowDataGateway() throws DatabaseException
	{
		NPCQuestionsForTest question = NPCQuestionsForTest.ONE;
		gateway = findGateway(NPCQuestionsForTest.ONE.getQuestionID());
		assertEquals(question.getQ(), gateway.getQuestionStatement());
		gateway.setQuestion("This is a test?");
		gateway.setAnswer("Another Test");
		gateway.setStartDate(LocalDate.of(2014, 2, 11));
		gateway.setEndDate(LocalDate.of(2015, 3, 21));
		gateway.updateGateway();
		gateway = findGateway(NPCQuestionsForTest.ONE.getQuestionID());
		assertEquals("This is a test?", gateway.getQuestionStatement());
		assertEquals("Another Test", gateway.getAnswer());
		assertEquals(LocalDate.of(2014, 2, 11), gateway.getStartDate());
		assertEquals(LocalDate.of(2015, 3, 21), gateway.getEndDate());

	}

	/**
	 * make sure we get the right exception if we try to find someone who doesn't
	 * exist
	 *
	 * @throws DatabaseException
	 *             should
	 */
	@Test(expected = DatabaseException.class)
	public void findNotExisting() throws DatabaseException
	{
		gateway = findGateway(11111);
	}

	/**
	 * @return a random gateway
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public abstract NPCQuestionRowDataGateway findRandomGateway() throws DatabaseException;

	/**
	 * get a gateway for a given question
	 *
	 * @param questionID
	 *            the unique ID of the question we are interested in
	 * @return the gateway
	 * @throws DatabaseException
	 *             if we couldn't find the ID in the data source
	 */
	abstract NPCQuestionRowDataGateway findGateway(int questionID) throws DatabaseException;

}

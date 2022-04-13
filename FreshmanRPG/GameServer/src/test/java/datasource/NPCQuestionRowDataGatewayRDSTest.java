package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.Test;

import dataDTO.QuestionDTO;
import datatypes.NPCQuestionsForTest;

/**
 * Tests for the RDS version of the gateway
 *
 * @author Merlin
 *
 */
public class NPCQuestionRowDataGatewayRDSTest extends NPCQuestionRowDataGatewayTest
{

	/**
	 *
	 * @see datasource.NPCQuestionRowDataGatewayTest#findGateway(int)
	 */
	@Override
	NPCQuestionRowDataGateway findGateway(int questionID) throws DatabaseException
	{
		return new NPCQuestionRowDataGatewayRDS(questionID);
	}

	/**
	 * @see datasource.NPCQuestionRowDataGatewayTest#findRandomGateway()
	 */
	@Override
	public NPCQuestionRowDataGateway findRandomGateway() throws DatabaseException
	{
		return NPCQuestionRowDataGatewayRDS.findRandomGateway();
	}

	/**
	 * make sure we can retrieve the data transfer object for a given question
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testGetQuestionDTO() throws DatabaseException
	{
		NPCQuestionsForTest question = NPCQuestionsForTest.FOUR;
		NPCQuestionRowDataGatewayRDS gateway
				= new NPCQuestionRowDataGatewayRDS(question.getQ(), question.getA(),
				question.getStartDate(), question.getEndDate());

		QuestionDTO questionInfo = gateway.getQuestionInfo();

		assertEquals(question.getA(), questionInfo.getAnswer());
		assertEquals(question.getQ(), questionInfo.getQuestion());
		assertEquals(question.getStartDate(), questionInfo.getStartDate());
		assertEquals(question.getEndDate(), questionInfo.getEndDate());
	}

	/**
	 * Make sure that the creation method assigns an id.  Also check other parameters.
	 * @throws DatabaseException Will throw an exception if we cannot add the
	 * information to the NPCQuestion table
	 */
	@Test
	public void creation() throws DatabaseException
	{
		NPCQuestionRowDataGatewayRDS gateway = new NPCQuestionRowDataGatewayRDS(
				"hello?",
				"world.",
				LocalDate.of(1, 2, 3),
				LocalDate.of(4, 5, 6)
		);

		assertNotNull(gateway.getQuestionID());
		assertTrue(gateway.getQuestionID() > 0);
		assertEquals("hello?", gateway.getQuestionStatement());
		assertEquals("world.", gateway.getAnswer());
		assertEquals(LocalDate.of(1, 2, 3), gateway.getStartDate());
		assertEquals(LocalDate.of(4, 5, 6), gateway.getEndDate());
	}

	/**
	 * Tests to see if we can create a question and find the information
	 * about the question
	 * @throws DatabaseException Will be thrown if we cannot find the question
	 * based off the ID
	 */
	@Test
	public void findCreatedQuestion() throws DatabaseException
	{
		NPCQuestionRowDataGatewayRDS gateway = new NPCQuestionRowDataGatewayRDS(
				"hello?",
				"world.",
				LocalDate.of(2012, 2, 14),
				LocalDate.of(2018, 12, 15)
		);

		NPCQuestionRowDataGatewayRDS gateway2 = new NPCQuestionRowDataGatewayRDS(gateway.getQuestionID());
		assertNotNull(gateway2.getQuestionID());
		assertEquals(gateway.getQuestionStatement(), gateway2.getQuestionStatement());
		assertEquals(gateway.getAnswer(), gateway2.getAnswer());

		assertEquals(LocalDate.of(2012, 2, 14), gateway2.getStartDate());
		assertEquals(LocalDate.of(2018, 12, 15), gateway2.getEndDate());
	}

	/**
	 * Tests to see if we can delete a question from the database after creating one
	 * @throws DatabaseException Will be thrown when looking for a deleted question
	 */
	@Test(expected = DatabaseException.class)
	public void deleteCreatedQuestion() throws DatabaseException
	{
		NPCQuestionRowDataGatewayRDS gateway = null;

		try
		{
			gateway = new NPCQuestionRowDataGatewayRDS(
					"hello?",
					"world.",
					LocalDate.of(2012, 2, 14),
					LocalDate.of(2018, 12, 15)
			);
		}
		catch (DatabaseException e)
		{

			fail("Creating a question failed when it shouldn't");
		}

		gateway.delete();
		findGateway(gateway.getQuestionID());
	}


}

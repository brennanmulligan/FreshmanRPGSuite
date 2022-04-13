package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataDTO.QuestionDTO;
import datatypes.NPCQuestionsForTest;

/**
 * @author Robert Windisch
 *
 *         Tests for the NPCQuestion table data gateway
 */
public abstract class NPCQuestionTableDataGatewayTest extends DatabaseTest
{
	private NPCQuestionTableDataGateway gateway;

	/**
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		super.setUp();
	}

	/**
	 * Make sure any static information is cleaned up between tests
	 *
	 * @throws SQLException
	 *             shouldn't
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@After
	public void cleanup() throws DatabaseException, SQLException
	{
		super.tearDown();
		if (gateway != null)
		{
			gateway.resetData();
		}
	}

	/**
	 * @return the correct gateway
	 */
	public abstract NPCQuestionTableDataGateway getGatewaySingleton();

	/**
	 * Test that a singleton is returned
	 */
	@Test
	public void isASingleton()
	{
		NPCQuestionTableDataGateway x = getGatewaySingleton();
		NPCQuestionTableDataGateway y = getGatewaySingleton();
		assertSame(x, y);
		assertNotNull(x);
	}

	/**
	 * We should be able to fetch all questions from the data source.
	 *
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	@Test
	public void getAllQuestions() throws DatabaseException
	{
		gateway = getGatewaySingleton();

		gateway.resetData();
		ArrayList<QuestionDTO> results = gateway.getAllQuestions();

		assertEquals(NPCQuestionsForTest.values().length, results.size());
	}

	/**
	 * Checks to make sure we can delete all of the questions in the table
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void deleteAllQuestions() throws DatabaseException
	{
		gateway = getGatewaySingleton();
		gateway.resetData();
		gateway.deleteAllQuestions();
		ArrayList<QuestionDTO> x = gateway.getAllQuestions();
		assertEquals(0, x.size());

		gateway.resetData();
	}

	/**
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void deletesOneQuestion() throws DatabaseException
	{
		gateway = getGatewaySingleton();
		gateway.resetData();
		for (QuestionDTO question : gateway.getAllQuestions())
		{
			assertNotNull(question);
		}
		int beforeCount = gateway.getAllQuestions().size();
		NPCQuestionsForTest gone = NPCQuestionsForTest.FOUR;
		gateway.deleteQuestion(gone.getQuestionID());
		ArrayList<QuestionDTO> after = gateway.getAllQuestions();
		assertEquals(beforeCount - 1, after.size());
		for (QuestionDTO question : after)
		{
			assertFalse(question.getQuestion().equals(gone.getQ()) && question.getAnswer().equals(gone.getA()));
		}

	}

	/**
	 * Make sure we can add a single question to the data source
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws ParseException
	 *             shouldn't
	 */
	@Test
	public void addOneQuestion() throws DatabaseException, ParseException
	{
		gateway = getGatewaySingleton();
		gateway.resetData();
		int beforeCount = gateway.getAllQuestions().size();
		QuestionDTO dto = gateway.addQuestion("This is a hard question", "With a hard answer",
				LocalDate.of(2018, 12, 25), LocalDate.of(2019, 2, 5));

		// verify the returned DTO
		assertEquals("This is a hard question", dto.getQuestion());
		assertEquals("With a hard answer", dto.getAnswer());
		assertEquals(LocalDate.of(2018, 12, 25), dto.getStartDate());
		assertEquals(LocalDate.of(2019, 2, 5), dto.getEndDate());

		// verify it got into the datasource
		ArrayList<QuestionDTO> after = gateway.getAllQuestions();
		assertEquals(beforeCount + 1, after.size());
		boolean found = false;
		for (QuestionDTO question : after)
		{
			found = found
					|| question.getQuestion().equals(dto.getQuestion()) && question.getAnswer().equals(dto.getAnswer());
		}
		assertTrue(found);
	}

	/**
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void canGetOneQuestion() throws DatabaseException
	{
		gateway = getGatewaySingleton();
		gateway.resetData();
		NPCQuestionsForTest target = NPCQuestionsForTest.FOUR;
		QuestionDTO returned = gateway.getQuestion(target.getQuestionID());
		assertEquals(target.getQuestionID(), returned.getId());
		assertEquals(target.getQ(), returned.getQuestion());
		assertEquals(target.getA(), returned.getAnswer());
		assertEquals(target.getStartDate(), returned.getStartDate());
		assertEquals(target.getEndDate(), returned.getEndDate());
	}
}

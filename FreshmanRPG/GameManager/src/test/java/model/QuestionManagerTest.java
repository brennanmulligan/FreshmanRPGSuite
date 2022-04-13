package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dataDTO.QuestionDTO;
import datasource.DatabaseException;
import model.reports.QuestionListReport;
import datatypes.NPCQuestionsForTest;

/**
 * Tests the functionality of QuestionManager.
 *
 * @author Alec Waddelow and Nick Martinez
 *
 */
public class QuestionManagerTest
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
	 * @throws DatabaseException
	 *             Test failed if this is thrown.
	 */
	@Test
	public void testQuestionManagerInitialization() throws DatabaseException
	{
		QuestionManager questionManager = QuestionManager.getInstance();
		assertNotNull(questionManager.getQuestions());
	}

	/**
	 * We should be able to add a question.
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testAddQuestion() throws DatabaseException
	{
		QuestionManager questionManager = QuestionManager.getInstance();
		int numQuestions = questionManager.getNumberOfQuestions();

		NPCQuestionsForTest question1 = NPCQuestionsForTest.FOUR;

		questionManager.addQuestion(question1.getQ(), question1.getA(), question1.getStartDate(),
				question1.getEndDate());

		assertEquals((numQuestions + 1), questionManager.getNumberOfQuestions());
	}

	/**
	 * Should be able to remove a question
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testRemoveQuestion() throws DatabaseException
	{
		QuestionManager questionManager = QuestionManager.getInstance();
		int numQuestions = questionManager.getNumberOfQuestions();

		NPCQuestionsForTest question1 = NPCQuestionsForTest.FOUR;

		QuestionDTO dto = questionManager.addQuestion(question1.getQ(), question1.getA(), question1.getStartDate(),
				question1.getEndDate());

		assertEquals((numQuestions + 1), questionManager.getNumberOfQuestions());

		questionManager.removeQuestion(dto.getId());

		assertEquals((numQuestions), questionManager.getNumberOfQuestions());
	}

	/**
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testRemoveQuestionSendsReport() throws DatabaseException
	{
		MockQualifiedObserver obs = new MockQualifiedObserver(QuestionListReport.class);

		NPCQuestionsForTest question1 = NPCQuestionsForTest.FOUR;
		QuestionDTO testDTO = new QuestionDTO(question1.getQuestionID(), question1.getQ(), question1.getA(),
				question1.getStartDate(), question1.getEndDate());

		QuestionManager manager = QuestionManager.getInstance();
		testDTO = manager.addQuestion(question1.getQ(), question1.getA(), question1.getStartDate(),
				question1.getEndDate());

		CommandDeleteQuestion cmd = new CommandDeleteQuestion(testDTO.getId());
		cmd.execute();

		QuestionListReport listReport = new QuestionListReport(manager.getQuestions());
		assertEquals(listReport.getQuestions(), ((QuestionListReport) obs.getReport()).getQuestions());
	}

	/**
	 * Tests the getQuestions method
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testGetQuestions() throws DatabaseException
	{
		QuestionManager questionManager = QuestionManager.getInstance();
		ArrayList<QuestionDTO> returnList = questionManager.getQuestions();

		assertEquals(NPCQuestionsForTest.values().length, returnList.size());
	}

	/**
	 * @throws DatabaseException
	 *             Test failed if this is thrown.
	 * @throws QuestionNotPresentException
	 *             Test passed if this is thrown.
	 */
	@Test(expected = QuestionNotPresentException.class)
	public void testUpdateQuestionNotPresent() throws DatabaseException, QuestionNotPresentException
	{
		QuestionManager questionManager = QuestionManager.getInstance();
		questionManager.updateQuestion(-1, "question?", "answer.", LocalDate.of(2012, 3, 3), LocalDate.of(2014, 5, 6));
	}

	/**
	 * @throws DatabaseException
	 *             Test failed if this is thrown.
	 */
	@Test
	public void testUpdateQuestionPresent() throws DatabaseException
	{
		QuestionManager questionManager = QuestionManager.getInstance();
		QuestionDTO info = questionManager.getQuestions().get(0);
		try
		{
			questionManager.updateQuestion(info.getId(), info.getQuestion(), info.getAnswer(), info.getStartDate(),
					info.getEndDate());
		}
		catch (QuestionNotPresentException e)
		{
			fail("Question not present should not be thrown");
		}
	}

	/**
	 * @throws DatabaseException
	 *             Test failed if this is thrown.
	 * @throws QuestionNotPresentException
	 *             Test failed if this is thrown.
	 */
	@Test
	public void testUpdateQuestionChangesValues() throws DatabaseException, QuestionNotPresentException
	{
		QuestionManager questionManager = QuestionManager.getInstance();
		QuestionDTO originalQuestion = questionManager.getQuestions().get(0);
		String originalAnswer = originalQuestion.getAnswer();

		QuestionDTO modifiedQuestion = new QuestionDTO(originalQuestion.getId(), originalQuestion.getQuestion(),
				originalAnswer + "123", originalQuestion.getStartDate(), originalQuestion.getEndDate());

		questionManager.updateQuestion(modifiedQuestion.getId(), modifiedQuestion.getQuestion(),
				modifiedQuestion.getAnswer(), modifiedQuestion.getStartDate(), modifiedQuestion.getEndDate());
		QuestionDTO updatedQuestion = questionManager.getQuestions().get(0);
		assertEquals(originalAnswer + "123", updatedQuestion.getAnswer());
	}

	/**
	 * @throws DatabaseException
	 *             Test failed if this is thrown.
	 * @throws QuestionNotPresentException
	 *             Test failed if this is thrown.
	 */
	@Test
	public void testThrowQuestionListReport() throws DatabaseException, QuestionNotPresentException
	{
		QuestionManager questionManager = QuestionManager.getInstance();

		MockQualifiedObserver mockReciever = new MockQualifiedObserver(QuestionListReport.class);
		QuestionDTO currentQuestion = questionManager.getQuestions().get(0);
		questionManager.updateQuestion(currentQuestion.getId(), currentQuestion.getQuestion(),
				currentQuestion.getAnswer(), currentQuestion.getStartDate(), currentQuestion.getEndDate());
		assertTrue(mockReciever.didReceiveReport());
	}

}

package model.reports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dataDTO.QuestionDTO;
import model.OptionsManager;
import datatypes.NPCQuestionsForTest;

/**
 * Test the src.model.reports.QuestionListReport object.
 */
public class QuestionListReportTest
{

	/**
	 * Ensure test mode.
	 */
	@Before
	public void setup()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 * Test behavior when the report contains no questions.
	 */
	@Test
	public void testEmpty()
	{
		ArrayList<QuestionDTO> questions = new ArrayList<>();
		QuestionListReport report = new QuestionListReport(questions);
		assertEquals(0, report.getQuestions().size());
	}

	/**
	 * Test behavior when the report contains one question(s).
	 */
	@Test
	public void testOneQuestionInfo()
	{
		ArrayList<QuestionDTO> questions = new ArrayList<>();
		NPCQuestionsForTest data = NPCQuestionsForTest.ONE;
		QuestionDTO q1 = new QuestionDTO(data.getQuestionID(), data.getQ(), data.getA(), data.getStartDate(), data.getEndDate());
		QuestionListReport qlist = new QuestionListReport(questions);
		questions.add(q1);
		ArrayList<QuestionDTO> temp = qlist.getQuestions();
		assertEquals(1, temp.size());
		assertTrue(temp.contains(q1));
	}

	/**
	 * Test behavior when the report contains some question(s).
	 */
	@Test
	public void testSomeQuestionInfo()
	{
		ArrayList<QuestionDTO> questions = new ArrayList<>();

		NPCQuestionsForTest questionData1 = NPCQuestionsForTest.ONE;
		NPCQuestionsForTest questionData2 = NPCQuestionsForTest.TWO;
		NPCQuestionsForTest questionData3 = NPCQuestionsForTest.MULTIPLE_CHOICE;

		QuestionListReport qlist = new QuestionListReport(questions);

		QuestionDTO q1 = new QuestionDTO(questionData1.getQuestionID(), questionData1.getQ(), questionData1.getA(), questionData1.getStartDate(), questionData1.getEndDate());
		QuestionDTO q2 = new QuestionDTO(questionData2.getQuestionID(), questionData2.getQ(), questionData2.getA(), questionData2.getStartDate(), questionData2.getEndDate());
		QuestionDTO q3 = new QuestionDTO(questionData3.getQuestionID(), questionData3.getQ(), questionData3.getA(), questionData3.getStartDate(), questionData3.getEndDate());

		questions.add(q1);
		questions.add(q2);
		questions.add(q3);

		ArrayList<QuestionDTO> temp = qlist.getQuestions();

		assertEquals(3, temp.size());
		assertTrue(temp.contains(q1));
		assertTrue(temp.contains(q2));
		assertTrue(temp.contains(q3));
	}
}

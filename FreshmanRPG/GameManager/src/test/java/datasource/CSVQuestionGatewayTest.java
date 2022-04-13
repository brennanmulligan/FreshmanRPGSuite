package datasource;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.junit.Test;

import dataDTO.QuestionDTO;

/**
 * @author Ben Uleau, Mohammed Almaslamani
 *
 */
public class CSVQuestionGatewayTest
{
	private LocalDate semesterStartDate = LocalDate.of(2000, 1, 1);
	private LocalDate secondWeekOfSemester = LocalDate.of(2000, 1, 8);
	private LocalDate fourthWeekOfSemester = LocalDate.of(2000, 1, 22);

	private CSVQuestionGateway createFromString(String input) throws InvalidColumnException
	{
		return new CSVQuestionGateway(input, semesterStartDate);
	}

	/**
	 * Ensure that we can parse out the header
	 * @throws InvalidColumnException - invalid header
	 */
	@Test
	public void testColumnsIndices() throws InvalidColumnException
	{
		final String quizID = "quizID";
		final String question = "question";
		final String answer = "answer";
		final String start_week = "start_week";
		final String end_week = "end_week";
		final CSVQuestionGateway gateway = createFromString(quizID + ", " + question + ", " + answer + ", " + start_week + ", " + end_week);

		assertEquals(quizID, gateway.getColumn(0));
		assertEquals(question, gateway.getColumn(1));
		assertEquals(answer, gateway.getColumn(2));
		assertEquals(start_week, gateway.getColumn(3));
		assertEquals(end_week, gateway.getColumn(4));
	}

	/**
	 * Should throw an exception with invalid coulumn names in header
	 * @throws InvalidColumnException - invalid columns
	 */
	@Test(expected = InvalidColumnException.class)
	public void testThrowsExceptionWithInvalidHeader() throws InvalidColumnException
	{
		createFromString("chocolate, strawberry, Jackie Chan, Boomerang, The Lord of the Rings");
	}

	/**
	 * Should be able to create an instance with a path to a csv and that it
	 * correctly reads from that file
	 * @throws InvalidColumnException - invalid header
	 * @throws IOException - bad path
	 */
	@Test
	public void testCreateFromCSV() throws InvalidColumnException, IOException
	{
		final Path path = Paths.get("tests/testdata/testQuizbotData.csv");
		final CSVQuestionGateway gateway = new CSVQuestionGateway(path, semesterStartDate);


		assertEquals(10, gateway.getNumberOfQuestions());
		/*-------------------------------------------------------------------------------*
		 * LOOK INTO REFACTORING THIS. Should have getAllQuestionData AND getQuestionData*
		 *-------------------------------------------------------------------------------*/
		final QuestionDTO question = gateway.getQuestionData().get(0);
		assertEquals(1, question.getId());
		assertEquals("Say my name.", question.getQuestion());
		assertEquals("Heisenberg", question.getAnswer());
		assertEquals(secondWeekOfSemester, question.getStartDate());
		assertEquals(fourthWeekOfSemester, question.getEndDate());

	}

	/**
	 * Single question
	 * @throws InvalidColumnException - invalid header
	 */
	@Test
	public void testQuestionWithOneRow() throws InvalidColumnException
	{

		final int quizID = 1;
		final String q = "question";
		final String a = "answer";
		final int startWeek = 1;
		final int endWeek = 10;

		final CSVQuestionGateway gateway = createFromString(
				"quizID, question, answer, start_week, end_week"
						+ System.getProperty("line.separator")
						+ quizID + ", " + q + ", " + a + ", " + startWeek + ", " + endWeek);

		final QuestionDTO question = gateway.getQuestionData().get(0);

		assertEquals(quizID, question.getId());
		assertEquals(q, question.getQuestion());
		assertEquals(a, question.getAnswer());
	}

	/**
	 * Multiple questions
	 * @throws InvalidColumnException - invalid header
	 */
	@Test
	public void testQuestionWithThreeRows() throws InvalidColumnException
	{
		final int numOfQuestions = 2;
		final int[] quizID = {10, 20, 30};
		final String[] q = {"question", "question1", "question2"};
		final String[] a = {"answer", "answer1", "answer2"};
		final int[] startWeek = {10, 30, 50};
		final int[] endWeek = {30, 5, 10};


		final CSVQuestionGateway gateway = createFromString(
				"quizID, question, answer, start_week, end_week"
						+ System.getProperty("line.separator") +
						quizID[0] + ", " + q[0] + ", " + a[0] + ", " + startWeek[0] + ", " + endWeek[0]
						+ System.getProperty("line.separator") +
						quizID[1] + ", " + q[1] + ", " + a[1] + ", " + startWeek[1] + ", " + endWeek[1]
						+ System.getProperty("line.separator") +
						quizID[2] + ", " + q[2] + ", " + a[2] + ", " + startWeek[2] + ", " + endWeek[2]);


		for (int i = 0; i < numOfQuestions; i++)
		{
			assertEquals(quizID[i], gateway.getQuestionData().get(i).getId());
			assertEquals(q[i], gateway.getQuestionData().get(i).getQuestion());
			assertEquals(a[i], gateway.getQuestionData().get(i).getAnswer());
		}
	}

	/**
	 * The parser should trim any whitespace in the data where applicable
	 * @throws InvalidColumnException - invalid header
	 */
	@Test
	public void testTrimData() throws InvalidColumnException
	{
		final int quizID = 1;
		final String q = "question";
		final String a = "answer";
		final int startWeek = 1;
		final int endWeek = 10;
		final String input = "quizID, question	, answer     ,   start_week, end_week" +
				System.getProperty("line.separator") +
				quizID + ", " + q + ", " + a + ", " + startWeek + ", " + endWeek;

		final CSVQuestionGateway gateway = createFromString(input);
		final QuestionDTO question = gateway.getQuestionData().get(0);

		assertEquals(quizID, question.getId());
		assertEquals(q, question.getQuestion());
		assertEquals(a, question.getAnswer());
	}

	/**
	 * Test no questions
	 * @throws InvalidColumnException - invalid header
	 */
	@Test
	public void testZeroQuestions() throws InvalidColumnException
	{
		final String input = "quizID, question, answer, start_week, end_week";
		final CSVQuestionGateway gateway = createFromString(input);

		assertEquals(0, gateway.getNumberOfQuestions());
	}

	/**
	 * If an empty field is encountered, the parser should skip that entire row
	 * @throws InvalidColumnException - invalid header
	 */
	@Test
	public void testEmptyField() throws InvalidColumnException
	{
		final String input = "quizID, question, answer, start_week, end_week" +
				System.getProperty("line.separator") +
				"1,, ans, 1, 10" +
				System.getProperty("line.separator") +
				"2, ques, answ, 2, 20";
		final CSVQuestionGateway gateway = createFromString(input);
		assertEquals(1, gateway.getNumberOfQuestions());
	}

	/**
	 * If a field with only whitespace is encountered, the parser should skip
	 * that entire row
	 * @throws InvalidColumnException - invalid header
	 */
	@Test
	public void testWhitespaceOnlyField() throws InvalidColumnException
	{
		final String input = "quizID, question, answer, start_week, end_week" +
				System.getProperty("line.separator") +
				"1,						, ans, 1, 10" +
				System.getProperty("line.separator") +
				"2, ques, answ, 2, 20";
		final CSVQuestionGateway gateway = createFromString(input);

		assertEquals(1, gateway.getNumberOfQuestions());
	}
}

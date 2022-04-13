package model.reports;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import dataDTO.QuestionDTO;
import model.OptionsManager;

/**
 * Test the src.data.QuestionInfo object.
 */
public class QuestionInfoTest
{

	/**
	 * Switch to test mode.
	 */
	@Before
	public void setup()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 * Check that the constructor sets the correct values.
	 */
	@Test
	public void testConstructor()
	{
		QuestionDTO questionInfo = new QuestionDTO(12,
				"What is the airspeed velocity of an unladen swallow?",
				"42",
				LocalDate.of(2014, 12, 25),
				LocalDate.of(2015, 1, 11)
		);

		assertEquals(12, questionInfo.getQuestionID());
		assertEquals("What is the airspeed velocity of an unladen swallow?", questionInfo.getQuestion());
		assertEquals("42", questionInfo.getAnswer());
		assertEquals(LocalDate.of(2014, 12, 25), questionInfo.getStartDate());
		assertEquals(LocalDate.of(2015, 1, 11), questionInfo.getEndDate());
	}

	/**
	 * Test to test the toString method.
	 */
	@Test
	public void testToString()
	{
		QuestionDTO questionInfo = new QuestionDTO(14,
				"What is the airspeed velocity of an unladen swallow?",
				"42",
				LocalDate.of(2014, 12, 25),
				LocalDate.of(2015, 1, 11)
		);

		assertEquals(
				"QuestionInfo(question: " + questionInfo.getQuestion() +
						", answer: " + questionInfo.getAnswer() +
						", startDate: " + questionInfo.getStartDate().toString() +
						", endDate: " + questionInfo.getEndDate().toString(),
				questionInfo.toString()
		);
	}

}

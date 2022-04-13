package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import dataDTO.QuestionDTO;
import datasource.DatabaseException;
import datasource.NPCQuestionTableDataGateway;
import datasource.NPCQuestionTableDataGatewayRDS;
import model.reports.QuestionListReport;
import datatypes.NPCQuestionsForTest;

/**
 * @author Darin Alleman
 *
 */
public class CommandAddQuestionTest
{
	/**
	 * Make sure we are in test mode
	 */
	@BeforeClass
	public static void setup()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	/**
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testAddQuestion() throws DatabaseException
	{
		NPCQuestionsForTest question1 = NPCQuestionsForTest.ONE;
		CommandAddQuestion cmd = new CommandAddQuestion(question1.getQ(), question1.getA(), question1.getStartDate(), question1.getEndDate());

		cmd.execute();


		//check to see that the testDTO exists in the questions table
		NPCQuestionTableDataGateway gateway = NPCQuestionTableDataGatewayRDS.getSingleton();
		ArrayList<QuestionDTO> questions = gateway.getAllQuestions();
		for (QuestionDTO q : questions)
		{
			if (q.getQuestion().equals(question1.getQ()) &&
					q.getAnswer().equals(question1.getA()) &&
					q.getStartDate().equals(question1.getStartDate()) &&
					q.getEndDate().equals(question1.getEndDate()))
			{
				assertTrue(true);
				return;
			}
		}
		fail();
	}

	/**
	 * Test that a QuestionAddedReport is created and sent when the command is executed
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testReportIsSent() throws DatabaseException
	{
		MockQualifiedObserver obs = new MockQualifiedObserver(QuestionListReport.class);

		NPCQuestionsForTest question1 = NPCQuestionsForTest.FOUR;
		CommandAddQuestion cmd = new CommandAddQuestion(question1.getQ(), question1.getA(), question1.getStartDate(), question1.getEndDate());
		cmd.execute();

		QuestionManager manager = QuestionManager.getInstance();
		ArrayList<QuestionDTO> list = manager.getQuestions();
		QuestionListReport listReport = new QuestionListReport(list);
		assertEquals(listReport.getQuestions(), ((QuestionListReport) obs.getReport()).getQuestions());
	}

}

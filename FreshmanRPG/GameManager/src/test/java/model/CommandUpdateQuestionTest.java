package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dataDTO.QuestionDTO;
import datasource.DatabaseException;
import datasource.NPCQuestionTableDataGatewayMock;
import model.reports.QuestionListReport;
import datatypes.NPCQuestionsForTest;

/**
 * Test the behavior of model.CommandUpdateQuestion
 */
public class CommandUpdateQuestionTest
{

	/**
	 *
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		NPCQuestionTableDataGatewayMock.getSingleton().resetData();
	}

	/**
	 * [testUpdatedManager description]
	 *
	 * @throws DatabaseException
	 *             Shouln't
	 *
	 */
	@Test
	public void testUpdatedManager() throws DatabaseException
	{
		QuestionDTO originalQuestion = NPCQuestionTableDataGatewayMock.getSingleton().getAllQuestions().get(3);
		QuestionDTO modifiedQuestion = new QuestionDTO(originalQuestion.getId(), originalQuestion.getQuestion(),
				"this is an answer", originalQuestion.getStartDate(), originalQuestion.getEndDate());

		CommandUpdateQuestion updateCommand = new CommandUpdateQuestion(modifiedQuestion.getId(),
				modifiedQuestion.getQuestion(), modifiedQuestion.getAnswer(), modifiedQuestion.getStartDate(),
				modifiedQuestion.getEndDate());
		updateCommand.execute();

		QuestionDTO updatedQuestion = NPCQuestionTableDataGatewayMock.getSingleton().getAllQuestions().get(3);
		assertEquals(modifiedQuestion, updatedQuestion);
	}

	/**
	 * Test that a QuestionListReport is created and sent when the command is
	 * executed
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testReportIsSent() throws DatabaseException
	{
		MockQualifiedObserver obs = new MockQualifiedObserver(QuestionListReport.class);

		NPCQuestionsForTest q = NPCQuestionsForTest.FOUR;
		CommandUpdateQuestion cmd = new CommandUpdateQuestion(q.getQuestionID(), q.getQ(), q.getA(), q.getStartDate(),
				q.getEndDate());
		cmd.execute();

		QuestionManager manager = QuestionManager.getInstance();
		ArrayList<QuestionDTO> list = manager.getQuestions();
		QuestionListReport listReport = new QuestionListReport(list);
		assertEquals(listReport.getQuestions(), ((QuestionListReport) obs.getReport()).getQuestions());
	}

}

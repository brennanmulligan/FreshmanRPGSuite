package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dataDTO.QuestionDTO;
import datasource.DatabaseException;
import datasource.NPCQuestionTableDataGateway;
import datasource.NPCQuestionTableDataGatewayMock;
import datasource.NPCQuestionTableDataGatewayRDS;
import model.reports.QuestionListReport;
import datatypes.NPCQuestionsForTest;

/**
 * @author Reginald Nettey and David Burkett
 *
 */
public class CommandDeleteQuestionTest
{
	/**
	 * Resets the data in the mocks and sets test mode.
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		NPCQuestionTableDataGatewayMock.getSingleton().resetData();

	}

	/**
	 * Make sure the command deletes from the database
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void deleteQuestionUpdatesDatabase() throws DatabaseException
	{
		NPCQuestionTableDataGateway gateway = getQuestionTableGateway();
		int numQuestions = gateway.getAllQuestions().size();
		NPCQuestionsForTest question1 = NPCQuestionsForTest.FOUR;

		CommandDeleteQuestion cmd = new CommandDeleteQuestion(question1.getQuestionID());

		assertTrue(cmd.execute());
		assertEquals((numQuestions - 1), gateway.getAllQuestions().size());
		ArrayList<QuestionDTO> all = gateway.getAllQuestions();
		for (QuestionDTO question : all)
		{
			assertFalse(
					question.getQuestion().equals(question1.getQ()) && question.getAnswer().equals(question1.getA()));
		}
	}

	private NPCQuestionTableDataGateway getQuestionTableGateway()
	{
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			return NPCQuestionTableDataGatewayMock.getSingleton();
		}
		return NPCQuestionTableDataGatewayRDS.getSingleton();
	}

	/**
	 * Test that a QuestionAddedReport is created and sent when the command is
	 * executed
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testReportIsSent() throws DatabaseException
	{
		MockQualifiedObserver obs = new MockQualifiedObserver(QuestionListReport.class);

		CommandDeleteQuestion cmd = new CommandDeleteQuestion(NPCQuestionsForTest.FOUR.getQuestionID());
		cmd.execute();

		QuestionManager manager = QuestionManager.getInstance();
		ArrayList<QuestionDTO> list = manager.getQuestions();
		QuestionListReport listReport = new QuestionListReport(list);
		assertEquals(listReport.getQuestions(), ((QuestionListReport) obs.getReport()).getQuestions());
	}
}

package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import DatabaseBuilders.BuildTestQuestions;
import dataDTO.QuestionDTO;
import datasource.ObjectiveStateTableDataGatewayMock;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;
import datasource.PlayerLoginRowDataGatewayMock;
import datasource.PlayerRowDataGatewayMock;
import datasource.QuestStateTableDataGatewayMock;
import model.reports.QuestionListReport;

/**
 * Tests for command to delete all quizbot questions in table before importing.
 *
 * @author Jordan Long
 *
 */
public class CommandDeleteAllQuestionsTest
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
		new PlayerRowDataGatewayMock().resetData();
		new PlayerLoginRowDataGatewayMock().resetData();
		QuestStateTableDataGatewayMock.getSingleton().resetData();
		ObjectiveStateTableDataGatewayMock.getSingleton().resetData();
		QuestManager.resetSingleton();
		new PlayerConnectionRowDataGatewayMock(1).resetData();
		GameManagerPlayerManager.resetSingleton();
	}

	/**
	 * Make sure the command deletes
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws SQLException
	 *             shouldn't
	 */
	@Test
	public void deleteQuestion() throws DatabaseException, SQLException
	{
		QuestionManager questionManager = QuestionManager.getInstance();

		CommandDeleteAllQuestions cmd = new CommandDeleteAllQuestions();
		assertTrue(cmd.execute());
		assertEquals(0, questionManager.getNumberOfQuestions());
		BuildTestQuestions.createQuestionTable();
	}

	/**
	 * Test that a QuestionDeletedReport is created and sent when the command is
	 * executed
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testReportIsSent() throws DatabaseException
	{
		MockQualifiedObserver obs = new MockQualifiedObserver(QuestionListReport.class);

		CommandDeleteAllQuestions cmd = new CommandDeleteAllQuestions();
		cmd.execute();

		QuestionManager manager = QuestionManager.getInstance();
		ArrayList<QuestionDTO> list = manager.getQuestions();
		QuestionListReport listReport = new QuestionListReport(list);
		assertEquals(listReport.getQuestions().size(), ((QuestionListReport) obs.getReport()).getQuestions().size());
	}
}

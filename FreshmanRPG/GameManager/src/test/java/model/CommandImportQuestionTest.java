package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dataDTO.QuestionDTO;
import datasource.DatabaseException;
import datasource.NPCQuestionTableDataGateway;
import datasource.NPCQuestionTableDataGatewayMock;
import model.reports.QuestionListReport;

/**
 * test that @see model.CommandImportQuestion behaves as expected
 *
 * @author Mohammed Almaslamani, Christopher Boyer
 *
 */
public class CommandImportQuestionTest
{
	private String filePath = "testdata/testQuizbotDataSmall.csv";

	/**
	 * set up the test setting.
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	/**
	 * test that questions can be imported.
	 *
	 * @throws DatabaseException
	 *             - shouldn't throw that.
	 */
	@Test
	public void testImportQuestionsNoErrors() throws DatabaseException
	{
		File file = new File(filePath);
		CommandImportQuestion command = new CommandImportQuestion(file, LocalDate.of(2000, 1, 1));

		boolean result = command.execute();
		if (!result)
		{
			fail();
		}

		NPCQuestionTableDataGateway gateway = NPCQuestionTableDataGatewayMock.getSingleton();
		ArrayList<QuestionDTO> questions = gateway.getAllQuestions();
		boolean questionFound = false;
		for (QuestionDTO q : questions)
		{
			if (q.getQuestion().equals("Say my name."))
			{
				assertEquals("Heisenberg", q.getAnswer());
				questionFound = true;
			}
		}

		assertTrue(questionFound);
	}

	/**
	 * Tests that the appropriate report is sent after importing.
	 *
	 * @throws DatabaseException-
	 *             shouldn't throw that for now.
	 */
	@Test
	public void testReport() throws DatabaseException
	{
		MockQualifiedObserver mqo = new MockQualifiedObserver(QuestionListReport.class);
		CommandImportQuestion command = new CommandImportQuestion(new File(filePath), LocalDate.of(2000, 1, 1));

		command.execute();
		assertNotNull(mqo.getReport());
		assertTrue(mqo.getReport() instanceof QuestionListReport);
	}
}

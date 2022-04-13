package ui.fx.framework_test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import dataDTO.QuestionDTO;
import datasource.DatabaseException;
import datasource.NPCQuestionTableDataGatewayRDS;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import manager.GameManagerFX;
import model.Command;
import model.CommandDeleteQuestion;
import model.ModelFacade;
import model.OptionsManager;
import ui.fx.contentviews.QuizbotContentView;

/**
 * This tests the import question functionality
 *
 * @author Abe Loscher and Ben Uleau
 *
 */
public class ImportQuestionTest extends ApplicationTest
{

	/**
	 * Start the game manager.
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]
				{"--localhost", "--db=05"}).start(stage);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 * Sets up the test class
	 *
	 * @throws Exception
	 *             shouldn't
	 */
	@Before
	public void setUp() throws Exception
	{

	}

	/**
	 * Tests the importing of a question from a csv file
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testImportQuestions() throws DatabaseException
	{
		ArrayList<QuestionDTO> questions = NPCQuestionTableDataGatewayRDS.getSingleton().getAllQuestions();
		int numQuestionsBefore = questions.size();
		clickOn("#QuizbotMenuButton");
		clickOn("#ImportButton");
		QuizbotContentView.getInstance().getContainer()
				.setSelectedFile(new File("tests/testdata/testQuizbotDataSmall.csv"));
		push(KeyCode.ESCAPE);
		push(KeyCode.ENTER);
		clickOn("#StartDatePicker").write("1/1/2018");
		clickOn("#ModalSaveButton");
		sleep(2000);
		questions = NPCQuestionTableDataGatewayRDS.getSingleton().getAllQuestions();
		int numQuestionsAfter = questions.size();
		final int addedQuestions = 1;
		assertEquals(numQuestionsBefore + addedQuestions, numQuestionsAfter);
		for (QuestionDTO question : questions)
		{
			if (question.getStartDate().equals(java.sql.Date.valueOf(LocalDate.of(2018, 1, 1)))
					&& question.getEndDate().equals(java.sql.Date.valueOf(LocalDate.of(2018, 3, 12))))
			{
				Command command = new CommandDeleteQuestion(question.getId());
				ModelFacade.getSingleton().queueCommand(command);
			}

		}

		sleep(500);
	}

}

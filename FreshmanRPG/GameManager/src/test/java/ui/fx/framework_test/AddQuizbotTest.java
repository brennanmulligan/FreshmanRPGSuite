package ui.fx.framework_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import dataDTO.QuestionDTO;
import datasource.DatabaseException;
import datasource.NPCQuestionTableDataGatewayRDS;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import manager.GameManagerFX;
import model.CommandDeleteQuestion;
import model.ModelFacade;
import model.OptionsManager;
import ui.fx.WorkSpace;
import ui.fx.dialogues.AddQuizbotModal;
import ui.fx.framework.AlertBar;

/**
 * @author Benjamin Uleau, Chris Boyer
 * Test adding quizzes
 */
public class AddQuizbotTest extends ApplicationTest
{
	/**
	 * Start the game manager
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]{"--localhost", "--db=04"}).start(stage);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 * Perform basic testing on add quiz components
	 */
	@Test
	public void testModalComponents()
	{
		clickOn("#QuizbotMenuButton");
		assertEquals("QuizbotContentView", WorkSpace.getInstance().getCenter().getClass().getSimpleName());
		clickOn("#AddButton");
		assertEquals("ADD QUIZ", AlertBar.getInstance().getMessage());

		clickOn("#QuestionField").write("Test question");
		clickOn("#AnswerField").write("Test answer");

		assertEquals("Test question", AddQuizbotModal.getInstance().getQuestion());
		assertEquals("Test answer", AddQuizbotModal.getInstance().getAnswer());

		clickOn("#StartDate").write("1/1/2000");
		clickOn("#EndDate").write("1/1/2000");
		push(KeyCode.ENTER);

		assertEquals(LocalDate.of(2000, 1, 1), AddQuizbotModal.getInstance().getStartDate());
		assertEquals(LocalDate.of(2000, 1, 1), AddQuizbotModal.getInstance().getEndDate());

		clickOn("#ModalCancelButton");
	}

	/**
	 * Test that a command gets queued when saving a command.
	 * @throws DatabaseException shouldn't
	 * @throws InterruptedException shouldn't
	 * @throws SQLException shouldn't
	 */
	@Test
	public void testQueueCommand() throws DatabaseException, InterruptedException, SQLException
	{
		String question = "testQueueCommand_Question";
		String answer = "testQueueCommand_Answer";
		clickOn("#QuizbotMenuButton");
		clickOn("#AddButton");
		clickOn("#QuestionField").write(question);
		clickOn("#AnswerField").write(answer);
		clickOn("#StartDate").write("1/1/2000");
		clickOn("#EndDate").write("1/2/2000");
		clickOn("#ModalSaveButton");

		Thread.sleep(2000);

		ArrayList<QuestionDTO> questions = NPCQuestionTableDataGatewayRDS.getSingleton().getAllQuestions();
		boolean found = false;
		int id = -1;
		for (QuestionDTO q : questions)
		{
			if (q.getQuestion().equals(question) && q.getAnswer().equals(answer))
			{
				id = q.getId();
				found = true;
			}
		}

		if (!found)
		{
			fail();
		}

		CommandDeleteQuestion cmd = new CommandDeleteQuestion(id);
		ModelFacade.getSingleton().queueCommand(cmd);

		Thread.sleep(2000);
	}
}

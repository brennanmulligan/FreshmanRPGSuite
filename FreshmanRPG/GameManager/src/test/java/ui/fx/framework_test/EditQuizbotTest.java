package ui.fx.framework_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import dataDTO.QuestionDTO;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import manager.GameManagerFX;
import model.CommandDeleteQuestion;
import model.ModelFacade;
import model.OptionsManager;
import ui.fx.WorkSpace;
import ui.fx.contentviews.QuizbotContentView;
import ui.fx.dialogues.EditQuizbotModal;
import ui.fx.framework.AlertBar;

/**
 * @author Benjamin Uleau, Jordan Long Test cases for editing quizbot questions.
 *
 */
public class EditQuizbotTest extends ApplicationTest
{
	/**
	 * Start the game manager
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]
				{"--localhost", "--db=05"}).start(stage);
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
		QuizbotContentView.getInstance().getQuestionsTable().getSelectionModel().select(0);

		clickOn("#EditButton");
		assertEquals("EDIT QUIZ", AlertBar.getInstance().getMessage());

		clickOn("#QuestionsTable");

		clickOn("#StartDate").write("1/1/2000");
		clickOn("#EndDate").write("1/1/2000");
		push(KeyCode.ENTER);

		assertEquals(LocalDate.of(2000, 1, 1), EditQuizbotModal.getInstance().getStartDate());
		assertEquals(LocalDate.of(2000, 1, 1), EditQuizbotModal.getInstance().getEndDate());

		clickOn("#ModalCancelButton");
	}

	/**
	 * @throws InterruptedException
	 *             shouldn't
	 */
	@Test
	public void testCorrectlyEditsQuestion() throws InterruptedException
	{
		clickOn("#QuizbotMenuButton");
		clickOn("#AddButton");

		String question = "testQueueEditCommand_Question";
		String answer = "testQueueEditCommand_Answer";
		clickOn("#QuestionField").write(question);
		clickOn("#AnswerField").write(answer);
		clickOn("#StartDate").write("1/1/2000");
		clickOn("#EndDate").write("1/2/2000");

		clickOn("#ModalSaveButton");

		Thread.sleep(2000);

		QuestionDTO rightDTO = null;
		for (QuestionDTO dto : QuizbotContentView.getInstance().getQuestionsTable().getItems())
		{
			if (dto.getQuestion().equals(question))
			{
				rightDTO = dto;
			}
		}

		QuizbotContentView.getInstance().getQuestionsTable().getSelectionModel().select(rightDTO);
		clickOn("#EditButton");
		String newQuestion = "new";
		EditQuizbotModal.getInstance().setQuestion("");
		clickOn("#QuestionField").write(newQuestion);
		clickOn("#StartDate").write("1/1/2000");
		clickOn("#EndDate").write("1/2/2000");
		clickOn("#ModalSaveButton");
		Thread.sleep(2000);
		boolean flag = false;

		for (QuestionDTO dto : QuizbotContentView.getInstance().getQuestionsTable().getItems())
		{
			if (dto.getId() == rightDTO.getId())
			{
				assertTrue(dto.getQuestion().equals(newQuestion));
				flag = true;
				rightDTO = dto;
			}
		}
		assertTrue(flag);

		CommandDeleteQuestion cdq = new CommandDeleteQuestion(rightDTO.getId());
		ModelFacade.getSingleton().queueCommand(cdq);
	}
}

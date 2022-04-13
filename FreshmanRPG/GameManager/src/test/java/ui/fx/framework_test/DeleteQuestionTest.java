package ui.fx.framework_test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import dataDTO.QuestionDTO;
import javafx.stage.Stage;
import manager.GameManagerFX;
import model.CommandAddQuestion;
import model.ModelFacade;
import model.OptionsManager;
import ui.fx.contentviews.QuizbotContentView;
import ui.fx.framework.AlertBar;

/**
 * Testing the deletion of questions functionality.
 *
 * @author Mohammed Almaslamani
 *
 */
public class DeleteQuestionTest extends ApplicationTest
{

	/**
	 * starting the game manager to begin testing
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]
				{"--localhost", "--db=05"}).start(stage);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 *
	 */
	@Test
	public void testDeleteQuestionButtonConfirmation()
	{
		clickOn("#QuizbotMenuButton");

		QuizbotContentView.getInstance().getQuestionsTable().getSelectionModel().select(0);
		clickOn("#DeleteButton");
		clickOn("#NoButton");
		assertEquals("QUESTION DELETION CANCELLED", AlertBar.getInstance().getMessage());
	}

	/**
	 * testing deleting question from the db
	 */
	@Test
	public void testDeleteSelectedButton()
	{

		clickOn("#QuizbotMenuButton");
		QuestionDTO question = new QuestionDTO(0, "Who am i?", "No One", LocalDate.of(2018, 1, 1),
				LocalDate.of(2018, 2, 3));
		CommandAddQuestion command = new CommandAddQuestion(question.getQuestion(), question.getAnswer(),
				question.getStartDate(), question.getEndDate());

		ModelFacade.getSingleton().queueCommand(command);

		this.sleep(1000);

		int sizeOfTableBeforeDelete = QuizbotContentView.getInstance().getQuestionsTable().getItems().size();

		QuizbotContentView.getInstance().getQuestionsTable().getSelectionModel()
				.select(QuizbotContentView.getInstance().getQuestionsTable().getItems().size() - 1);
		clickOn("#DeleteButton");
		clickOn("#YesButton");

		this.sleep(2000);

		assertEquals(sizeOfTableBeforeDelete - 1,
				QuizbotContentView.getInstance().getQuestionsTable().getItems().size());
	}

}

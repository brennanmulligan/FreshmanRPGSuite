package ui.fx.dialogues;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.stage.Stage;
import manager.GameManagerFX;

/**
 * Test for AddQuestModal.
 * @author Christopher Boyer and Abe Loscher
 */
public class AddQuestModalTest extends ApplicationTest
{

	/**
	 * Starts the JavaFX application
	 * @param stage stage of the application
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]{"--localhost", "--db=04"}).start(stage);

	}

	/**
	 * Test that the title is correct.
	 */
	@Test
	public void testTitleCorrect()
	{
		clickOn("#AdventureMenuButton");
		clickOn("#AddButton");
		clickOn("#QuestButton");
		assertEquals("Add Quest", AddQuestModal.getInstance().getModalTitle().getText());
		clickOn("#ModalCancelButton");
	}

}

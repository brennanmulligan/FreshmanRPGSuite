package ui.fx.dialogues;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import dataENUM.ObjectiveCompletionType;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import manager.GameManagerFX;
import ui.fx.contentviews.ObjectiveContentView;

/**
 * Tests for AddObjectiveModal.
 * @author Christopher Boyer and Abe Loscher
 */
public class AddObjectiveModalTest extends ApplicationTest
{

	/**
	 * Starts the JavaFX application
	 * @param stage stage of the JavaFX application
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]{"--localhost", "--db=04"}).start(stage);
	}

	/**
	 * Test the title is correct.
	 */
	@Test
	public void testTitleIsCorrect()
	{
		clickOn("#ObjectiveMenuButton");
		clickOn("#AddButton");
		clickOn("#ObjectiveButton");
		assertEquals("Add Objective", AddObjectiveModal.getInstance().getModalTitle().getText());
		clickOn("#ModalCancelButton");
	}

	/**
	 * Tests that we can add an objective with an interact type
	 */
	@Test
	public void testAddInteractObjectiveToQuest()
	{
		clickOn("#ObjectiveMenuButton");
		clickOn("#AddButton");
		clickOn("#ObjectiveButton");
		clickOn("#QuestName");
		push(KeyCode.DOWN);
		push(KeyCode.ENTER);
		clickOn("#ObjectiveCompletionType");
		push(KeyCode.DOWN);
		push(KeyCode.ENTER);
		while (!AddObjectiveModal.getInstance().getObjectiveCompletionType().getValue().equals(ObjectiveCompletionType.INTERACT))
		{
			clickOn("#ObjectiveCompletionType");
			push(KeyCode.DOWN);
			push(KeyCode.ENTER);
		}

		clickOn("#InteractableItem");
		push(KeyCode.DOWN);
		push(KeyCode.ENTER);
		clickOn("#ModalSaveButton");
		sleep(1000);
		assertEquals(ObjectiveCompletionType.INTERACT, ObjectiveContentView.getInstance().getObjectiveTable().getItems().get(ObjectiveContentView.getInstance().getObjectiveTable().getItems().size() - 1).getCompletionType());
	}
}
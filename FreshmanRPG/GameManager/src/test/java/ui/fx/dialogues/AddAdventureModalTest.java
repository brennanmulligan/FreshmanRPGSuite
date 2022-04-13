package ui.fx.dialogues;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import dataENUM.AdventureCompletionType;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import manager.GameManagerFX;
import ui.fx.contentviews.AdventureContentView;

/**
 * Tests for AddAdventureModal.
 * @author Christopher Boyer and Abe Loscher
 */
public class AddAdventureModalTest extends ApplicationTest
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
		clickOn("#AdventureMenuButton");
		clickOn("#AddButton");
		clickOn("#AdventureButton");
		assertEquals("Add Adventure", AddAdventureModal.getInstance().getModalTitle().getText());
		clickOn("#ModalCancelButton");
	}

	/**
	 * Tests that we can add an adventure with an interact type
	 */
	@Test
	public void testAddInteractAdventureToQuest()
	{
		clickOn("#AdventureMenuButton");
		clickOn("#AddButton");
		clickOn("#AdventureButton");
		clickOn("#QuestName");
		push(KeyCode.DOWN);
		push(KeyCode.ENTER);
		clickOn("#AdventureCompletionType");
		push(KeyCode.DOWN);
		push(KeyCode.ENTER);
		while (!AddAdventureModal.getInstance().getAdventureCompletionType().getValue().equals(AdventureCompletionType.INTERACT))
		{
			clickOn("#AdventureCompletionType");
			push(KeyCode.DOWN);
			push(KeyCode.ENTER);
		}

		clickOn("#InteractableItem");
		push(KeyCode.DOWN);
		push(KeyCode.ENTER);
		clickOn("#ModalSaveButton");
		sleep(1000);
		assertEquals(AdventureCompletionType.INTERACT, AdventureContentView.getInstance().getAdventureTable().getItems().get(AdventureContentView.getInstance().getAdventureTable().getItems().size() - 1).getCompletionType());
	}
}
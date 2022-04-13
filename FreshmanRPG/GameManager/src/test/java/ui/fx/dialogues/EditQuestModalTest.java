package ui.fx.dialogues;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.stage.Stage;
import manager.GameManagerFX;
import ui.fx.contentviews.AdventureContentView;

/**
 * Tests for EditQuestModal
 * @author Chris Boyer and Abe Loscher
 */
public class EditQuestModalTest extends ApplicationTest
{

	/**
	 * Starts the JavaFX application
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]{"--localhost", "--db=04"}).start(stage);
	}

	/**
	 * Tests to see that the title is correct
	 */
	@Test
	public void testTitleIsCorrect()
	{
		clickOn("#AdventureMenuButton");
		AdventureContentView.getInstance().getQuestTable().getSelectionModel().select(0);
		clickOn("#EditButton");
		assertEquals("Edit Quest", EditQuestModal.getInstance().getModalTitle().getText());
		clickOn("#ModalCancelButton");
	}
}

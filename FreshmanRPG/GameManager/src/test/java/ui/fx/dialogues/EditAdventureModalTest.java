package ui.fx.dialogues;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.stage.Stage;
import manager.GameManagerFX;
import ui.fx.contentviews.AdventureContentView;

/**
 * Tests for the EditAdventureModal
 * @author Chris Boyer and Abe Loscher
 *
 */
public class EditAdventureModalTest extends ApplicationTest
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
	 * Sets up resources associated with the modal
	 */
	@Before
	public void setUp()
	{
		//TODO set up necessary resources
	}

	/**
	 * Tears down modal 
	 */
	@After
	public void tearDown()
	{
		//TODO Tear down instances
		//EditAdventureModal.
	}

	/**
	 * Tests to see that the modal title is correct
	 */
	@Test
	public void testTitleIsCorrect()
	{
		clickOn("#AdventureMenuButton");
		AdventureContentView.getInstance().getQuestTable().getSelectionModel().select(0);
		AdventureContentView.getInstance().getAdventureTable().getSelectionModel().select(0);
		clickOn("#EditButton");
		assertEquals("Edit Adventure", EditAdventureModal.getInstance().getModalTitle().getText());
		clickOn("#ModalCancelButton");
	}

}

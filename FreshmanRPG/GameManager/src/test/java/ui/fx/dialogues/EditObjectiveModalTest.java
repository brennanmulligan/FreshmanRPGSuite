package ui.fx.dialogues;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.stage.Stage;
import manager.GameManagerFX;
import ui.fx.contentviews.ObjectiveContentView;

/**
 * Tests for the EditObjectiveModal
 * @author Chris Boyer and Abe Loscher
 *
 */
public class EditObjectiveModalTest extends ApplicationTest
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
		//EditObjectiveModal.
	}

	/**
	 * Tests to see that the modal title is correct
	 */
	@Test
	public void testTitleIsCorrect()
	{
		clickOn("#ObjectiveMenuButton");
		ObjectiveContentView.getInstance().getQuestTable().getSelectionModel().select(0);
		ObjectiveContentView.getInstance().getObjectiveTable().getSelectionModel().select(0);
		clickOn("#EditButton");
		assertEquals("Edit Objective", EditObjectiveModal.getInstance().getModalTitle().getText());
		clickOn("#ModalCancelButton");
	}

}

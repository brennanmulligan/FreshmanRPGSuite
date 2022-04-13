package ui.fx.framework_test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.stage.Stage;
import manager.GameManagerFX;
import model.OptionsManager;
import ui.fx.WorkSpace;
import ui.fx.contentviews.InteractableObjectContentView;
import ui.fx.dialogues.EditInteractableObjectModal;

/**
 *
 * @author Benjamin Uleau, Chris Boyer
 * Test cases for editing interactable objects
 */
public class EditInteractableObjectTest extends ApplicationTest
{
	/**
	 * Start the game manager
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]{"--localhost", "--db=05"}).start(stage);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 * Perform basic testing on edit quiz components
	 */
	@Test
	public void testModalComponents()
	{
		clickOn("#InteractableObjectMenuButton");
		assertEquals("InteractableObjectContentView", WorkSpace.getInstance().getCenter().getClass().getSimpleName());
		InteractableObjectContentView.getInstance().getInteractableObjectTable().getSelectionModel().select(0);

		clickOn("#EditButton");

		//Test components are clickable
		clickOn("#Name");
		clickOn("#PositionX");
		clickOn("#PositionY");
		clickOn("#Map");
		clickOn("#InteractableItemActionType");
		clickOn("#BuffPoints");
		clickOn("#MessageSent");
	}

	/**
	 * Test editing the name
	 */
	@Test
	public void testCanWrite()
	{
		clickOn("#InteractableObjectMenuButton");
		InteractableObjectContentView.getInstance().getInteractableObjectTable().getSelectionModel().select(0);
		clickOn("#EditButton");
		EditInteractableObjectModal.getInstance().setName("");
		String name = "Test name";
		clickOn("#Name").write(name);
		assertEquals(name, EditInteractableObjectModal.getInstance().getName());
		clickOn("#ModalCancelButton");
	}

}

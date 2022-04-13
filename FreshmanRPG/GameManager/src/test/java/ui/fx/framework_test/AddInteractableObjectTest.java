package ui.fx.framework_test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import manager.GameManagerFX;
import model.OptionsManager;
import ui.fx.contentviews.InteractableObjectContentView;

/**
 * Tests that object can be added.
 * @author Christopher Boyer and Abe Loscher
 */
public class AddInteractableObjectTest extends ApplicationTest
{

	/**
	 * Setup.
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]{"--localhost", "--db=04"}).start(stage);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 * Test that an object is added to the menu.
	 */
	@Test
	public void testAddInteractableObject()
	{

		String objectName = "ObjectName";
		clickOn("#InteractableObjectMenuButton");

		int numberOfItemsBeforeTest = InteractableObjectContentView.getInstance().getInteractableObjectTable().getItems().size();
		clickOn("#AddButton");
		clickOn("#Name").write(objectName);
		clickOn("#Map");
		push(KeyCode.DOWN);
		push(KeyCode.ENTER);
		clickOn("#InteractableItemActionType");
		push(KeyCode.DOWN);
		push(KeyCode.ENTER);
		clickOn("#ModalSaveButton");

		sleep(1000);
		assertEquals(numberOfItemsBeforeTest + 1, InteractableObjectContentView.getInstance().getInteractableObjectTable().getItems().size());
	}
}

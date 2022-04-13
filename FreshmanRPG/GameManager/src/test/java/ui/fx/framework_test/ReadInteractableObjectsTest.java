package ui.fx.framework_test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.stage.Stage;
import manager.GameManagerFX;
import model.OptionsManager;
import ui.fx.contentviews.InteractableObjectContentView;

/**
 * Test that interactable objects can be read.
 * @author Ben Uleau and Christopher Boyer
 *
 */
public class ReadInteractableObjectsTest extends ApplicationTest
{

	/**
	 * Start the game manager.
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]{"--localhost", "--db=04"}).start(stage);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 * Tests that objects get loaded into the table.
	 */
	@Test
	public void testInteractableObjectsGetLoadedIntoTheTableView()
	{
		clickOn("#InteractableObjectMenuButton");
		assertTrue(0 < InteractableObjectContentView.getInstance().getInteractableObjectTable().getItems().size());
	}

}

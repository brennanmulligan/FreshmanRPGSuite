package ui.fx.framework_test;

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.stage.Stage;
import manager.GameManagerFX;
import model.OptionsManager;

/**
 * Test deleting an interactable object from the database.
 * @author Jordan Long
 *
 */
public class DeleteInteractableObjectTest extends ApplicationTest
{

	/**
	 * Starts the game manager
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]{"--localhost", "--db=04"}).start(stage);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 * Setup for test class
	 * @throws Exception - exception 
	 */
	@Before
	public void setUp() throws Exception
	{

	}

	/**
	 * Testing the confirmation message shows up correctly
	 */
	@Test
	public void testDeleteButtonShowsConfirmation()
	{
	}


}

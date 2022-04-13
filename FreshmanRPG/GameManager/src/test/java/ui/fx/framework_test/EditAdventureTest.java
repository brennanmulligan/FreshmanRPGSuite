package ui.fx.framework_test;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import manager.GameManagerFX;
import model.OptionsManager;
import ui.fx.contentviews.AdventureContentView;

/**
 * Tests that the GUI can be used to edit the parameters of an adventure
 *
 */
public class EditAdventureTest extends ApplicationTest
{

	/**
	 * @see org.testfx.framework.junit.ApplicationTest#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]{"--localhost", "--db=04"}).start(stage);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void testOpenModalWithAdventureInformation() throws InterruptedException
	{
		clickOn("#AdventureMenuButton");
		AdventureContentView.getInstance().getQuestTable().getSelectionModel().select(0);
		AdventureContentView.getInstance().getAdventureTable().getSelectionModel().select(0);
		clickOn("#EditButton");
		press(KeyCode.ESCAPE);
	}

	/**
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void testEditAdventureInformation() throws InterruptedException
	{
		clickOn("#AdventureMenuButton");
		AdventureContentView.getInstance().getQuestTable().getSelectionModel().select(0);
		AdventureContentView.getInstance().getAdventureTable().getSelectionModel().select(0);
		clickOn("#EditButton");
		clickOn("#XPGained");
		this.press(MouseButton.PRIMARY);
		press(KeyCode.UP);
		clickOn("#ModalSaveButton");
		press(KeyCode.ESCAPE);
	}

}

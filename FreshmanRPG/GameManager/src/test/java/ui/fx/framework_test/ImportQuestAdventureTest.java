package ui.fx.framework_test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import manager.GameManagerFX;
import model.OptionsManager;
import ui.fx.contentviews.AdventureContentView;

/**
 * Tests for the import quests and adventures function.
 * @author Jordan Long
 *
 */
public class ImportQuestAdventureTest extends ApplicationTest
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
	 * Test that hitting the import button opens it and then you can escape out of it.
	 */
	@Test
	public void testImportButtonOpensFileChooser()
	{
		clickOn("#AdventureMenuButton");
		clickOn("#ImportButton");
		assertTrue(AdventureContentView.getInstance().getContainer().isActive());
		push(KeyCode.ESCAPE);
		assertFalse(AdventureContentView.getInstance().getContainer().isActive());
	}


	/**
	 * Test whether the import file chooser can select the correct file.
	 */
	@Test
	public void testImportButtonImportsSelectedFile()
	{

		clickOn("#AdventureMenuButton");
		clickOn("#ImportButton");
		AdventureContentView.getInstance().getContainer().setSelectedFile(new File("/tests/testdata/testQuestAdventureData.csv"));
		push(KeyCode.ESCAPE);

	}
}

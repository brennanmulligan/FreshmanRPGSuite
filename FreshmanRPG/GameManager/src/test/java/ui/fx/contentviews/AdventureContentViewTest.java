package ui.fx.contentviews;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import manager.GameManagerFX;

/**
 * Tests for the AdventureContentView class
 * @author Jordan Long and Abe Loscher
 *
 */
public class AdventureContentViewTest extends ApplicationTest
{
	/**
	 * Start the scene
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX().start(stage);
	}

	/**
	 * Tests that upon clicking the Import button, we get a file chooser popup
	 */
	@Test
	public void testImportFileChooser()
	{
		clickOn("#AdventureMenuButton");
		clickOn("#ImportButton");
		assertEquals(true, AdventureContentView.getInstance().getContainer().isActive());
		push(KeyCode.ESCAPE);
		assertEquals(false, AdventureContentView.getInstance().getContainer().isActive());
	}
}

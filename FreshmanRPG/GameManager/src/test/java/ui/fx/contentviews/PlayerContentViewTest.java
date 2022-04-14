package ui.fx.contentviews;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.testfx.api.FxRobotException;
import org.testfx.framework.junit.ApplicationTest;

import javafx.stage.Stage;
import manager.GameManagerFX;

/**
 * Test that it works.
 * @author Christopher Boyer and Abe Loscher
 */
public class PlayerContentViewTest extends ApplicationTest
{
	/**
	 * Setup.
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]{"--localhost", "--db=04"}).start(stage);
	}

	/**
	 * Does what the test name says.
	 */
	@Test
	public void testThatWhenYouClickOnThePlayerWindowThereIsASpecialButtonForCheckingObjectiveStatus()
	{
		clickOn("#PlayerMenuButton");
		try
		{
			clickOn("#PlayerObjectiveStateButton");
		}
		catch (Exception exception)
		{
			fail("Objective status button should exist: " + exception.getMessage());
		}
	}

	/**
	 * Test that being on another windows doesn't show the button.
	 */
	@Test(expected = FxRobotException.class)
	public void testThatObjectiveStatusButtonDoesNotExistInOtherContentViews()
	{
		clickOn("#QuizbotMenuButton");
		clickOn("#PlayerObjectiveStateButton");
	}

}

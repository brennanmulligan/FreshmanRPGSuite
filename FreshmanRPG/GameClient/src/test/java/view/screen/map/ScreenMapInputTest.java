package view.screen.map;

import static org.junit.Assert.assertEquals;

import datatypes.PlayersForTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import datatypes.Position;
import model.ClientModelFacade;
import model.ClientModelTestUtilities;
import model.ClientPlayerManager;
import model.MapManager;

/**
 * @author nhydock
 */
public class ScreenMapInputTest
{

	/**
	 * Reset the player manager before starting testing
	 */
	@Before
	public void setup()
	{
		ClientPlayerManager.resetSingleton();

		boolean[][] passability = { { true, true, true }, { true, false, true }, { true, true, true } };

		MapManager.resetSingleton();
		MapManager.getSingleton().setPassability(passability);
	}

	/**
	 * Test the input listener sending movement commands. This is currently ignored because, since it is running
	 * headless, there is no sprite, so the screen map input listener can't move the player.  Not sure we can ever fix
	 * that . . .
	 *
	 * @throws InterruptedException
	 *             when thread used to allow model facade command processing
	 *             interrupts
	 */
	@Test
	@Ignore
	public void testMovementCommandIssuing() throws InterruptedException
	{
		InputProcessor input = new ScreenMapInput();

		// Make the model facade be headless, but not mocked so we can make sure
		// that the movement commands generated when we hit a key cause the
		// appropriate change in position
		ClientModelFacade.resetSingleton();
		ClientModelFacade.getSingleton(true, false);
		// setup initial player for testing
		ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.JOHN);

		MapManager.resetSingleton();
		MapManager.getSingleton().setHeadless(true);
		MapManager.getSingleton().changeToNewFile("theGreen.tmx");

		// move player south
		input.keyDown(Keys.DOWN);
		pauseForCommandExecution();
		assertEquals(new Position(1, 0), ClientModelTestUtilities.getThisPlayersPosition());

		// move player north
		input.keyDown(Keys.UP);
		pauseForCommandExecution();
		assertEquals(new Position(0, 0), ClientModelTestUtilities.getThisPlayersPosition());

		// move player east
		input.keyDown(Keys.RIGHT);
		pauseForCommandExecution();
		assertEquals(new Position(0, 1), ClientModelTestUtilities.getThisPlayersPosition());

		// move player west
		input.keyDown(Keys.LEFT);
		pauseForCommandExecution();
		assertEquals(new Position(0, 0), ClientModelTestUtilities.getThisPlayersPosition());
	}

	private void pauseForCommandExecution() throws InterruptedException
	{
		while (ClientModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
	}
}

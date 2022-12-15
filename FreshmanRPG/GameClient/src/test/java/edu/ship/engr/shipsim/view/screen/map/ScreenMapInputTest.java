package edu.ship.engr.shipsim.view.screen.map;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.ClientModelTestUtilities;
import edu.ship.engr.shipsim.model.ClientPlayerManager;
import edu.ship.engr.shipsim.model.MapManager;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nhydock
 */
@GameTest("GameClient")
public class ScreenMapInputTest
{

    /**
     * Reset the player manager before starting testing
     */
    @BeforeEach
    public void setup()
    {
        ClientPlayerManager.resetSingleton();

        boolean[][] passability = {{true, true, true}, {true, false, true}, {true, true, true}};

        MapManager.resetSingleton();
        MapManager.getSingleton().setPassability(passability);
    }

    /**
     * Test the input listener sending movement commands. This is currently ignored because, since it is running
     * headless, there is no sprite, so the screen map input listener can't move the player.  Not sure we can ever fix
     * that . . .
     *
     * @throws InterruptedException when thread used to allow model facade command processing
     *                              interrupts
     */
    @Test
    @Disabled
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
        MapManager.getSingleton().changeToNewFile("quad.tmx", "ScreenMapInputTest");

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

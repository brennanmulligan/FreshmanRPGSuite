package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientPlayerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test that the CommandClearModelState clears the players and map properly
 *
 * @author Steve
 */
@GameTest("GameClient")
@ResetClientPlayerManager
public class CommandClearModelStateTest
{
    /**
     * Reset the MapManager after we're done.
     */
    @BeforeEach
    public void setup()
    {
        MapManager.resetSingleton();
    }

    /**
     * Ensure that the players model is reset
     */
    @Test
    public void testDoesResetPlayers()
    {
        ClientPlayerManager original = ClientPlayerManager.getSingleton();

        CommandClearModelState cms = new CommandClearModelState();
        cms.execute();

        assertNotEquals(original, ClientPlayerManager.getSingleton());
    }

    /**
     * Ensure that the current map is reset
     */
    @Test
    public void testDoesResetMap()
    {
        MapManager original = MapManager.getSingleton();

        CommandClearModelState cms = new CommandClearModelState();
        cms.execute();

        assertNotEquals(original, MapManager.getSingleton());
    }
}

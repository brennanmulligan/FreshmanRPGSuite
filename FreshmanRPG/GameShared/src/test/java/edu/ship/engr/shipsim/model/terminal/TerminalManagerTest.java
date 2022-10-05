package edu.ship.engr.shipsim.model.terminal;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Chris Roadcap
 * @author Denny Fleagle
 */
@GameTest("GameShared")
public class TerminalManagerTest
{
    TerminalManager manager;

    /**
     * Get the instance of terminal manager
     */
    @BeforeEach
    public void getSingleton()
    {
        manager = TerminalManager.getSingleton();
    }

    /**
     * Reset the instance of terminal manager
     */
    @AfterEach
    public void resetSingleton()
    {
        manager.resetSingleton();
    }

    /**
     * Tests the manager is a singleton
     */
    @Test
    public void testSingletonInitialization()
    {
        assertNotNull(manager);
    }

    /**
     * Tests resetting the singleton
     */
    @Test
    public void testResetSingleton()
    {
        assertTrue(manager.resetSingleton());
    }

    /**
     * tests that we can build a hashmap and it contains a command that extends the TerminalCommand abstract class
     *
     * @throws InstantiationException if the command isn't able to be instantiated
     * @throws IllegalAccessException if the command isn't able to be instantiated
     */
    @Test
    public void testBuildHashmap() throws InstantiationException, IllegalAccessException, NoSuchMethodException,
            InvocationTargetException
    {
        manager.buildHashMap();
        assertEquals(StubTerminalCommand1.class, manager.getTerminalCommand("stub1"));
    }

    /**
     * tests that we return null when getting a terminal command if it isn't in the hashmap
     *
     * @throws InstantiationException if the command isn't able to be instantiated
     * @throws IllegalAccessException if the command isn't able to be instantiated
     */
    @Test
    public void testTerminalCommandNotInHashmap() throws InstantiationException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException
    {
        manager.buildHashMap();
        assertNull(manager.getTerminalCommand("Not a real command"));
    }

}

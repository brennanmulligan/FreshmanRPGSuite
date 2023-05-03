package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.InteractableItemsForTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetInteractObjectManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for InteractObjectManager
 *
 * @author Andy Kim, Truc Chau, Jacob Knight, and Emmanuel Douge
 */
@GameTest("GameServer")
@ResetInteractObjectManager
@ResetReportObserverConnector
public class InteractObjectManagerTest
{
    /**
     * Tests that getSingleton() returns the same InteractObjectManager object
     */
    @Test
    public void testSingleton()
    {
        InteractObjectManager interactObject = InteractObjectManager.getSingleton();
        InteractObjectManager interactObjectSecond = InteractObjectManager.getSingleton();

        assertSame(interactObject, interactObjectSecond);
        InteractObjectManager.resetSingleton();
        assertNotSame(interactObject, InteractObjectManager.getSingleton());

    }

    /**
     * Tests execute returns false if no player on map
     */
    @Test
    public void testExecuteWithoutPlayer()
    {
        InteractObjectManager man = InteractObjectManager.getSingleton();
        assertFalse(man.execute(PlayersForTest.ANDY.getPlayerID(), InteractableItemsForTest.BOOK.getItemID()));

    }

    /**
     * Tests that if the player position is NOT near the object locations
     * Should return false
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void testPlayerIsNOTInObjectRange() throws DatabaseException
    {
        //Marty's default position is 10,19
        Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MARTY.getPlayerID());
        Position playerPosition = new Position(100, 100); //set up new position for the player
        player.setPosition(playerPosition);

        int result = InteractObjectManager.getSingleton().objectInRange(PlayersForTest.MARTY.getPlayerID());
        assertEquals(-1, result);
    }

    /**
     * Tests that if the player position is near the object locations
     * Should return true
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void testPlayerIsInObjectRange() throws DatabaseException
    {
        //Marty's default position is 10,19
        //Default position of interactable item on the same map with Marty in the mock version is at (1,1)
        Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MARTY.getPlayerID());
        Position playerPosition = new Position(1, 1); //set new position for the player at (1,1)
        player.setPosition(playerPosition);

        int result = InteractObjectManager.getSingleton().objectInRange(PlayersForTest.MARTY.getPlayerID());
        assertTrue(result >= 0);

    }

    @ParameterizedTest
    @MethodSource
    public void testPlayerPosition(Position position, boolean expected) throws DatabaseException
    {
        Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        player.setPosition(position);

        int result = InteractObjectManager.getSingleton().objectInRange(PlayersForTest.MERLIN.getPlayerID());
        assertEquals(expected, result >= 0);
    }

    private static Stream<Arguments> testPlayerPosition()
    {
        return Stream.of(
                Arguments.of(new Position(0, 1), true),
                Arguments.of(new Position(0, 2), true),
                Arguments.of(new Position(0, 3), false),
                Arguments.of(new Position(1, 0), true),
                Arguments.of(new Position(2, 0), true),
                Arguments.of(new Position(3, 0), false)
        );
    }

    /**
     * Tests that an item can pass an Buff Pool into a Player.
     * Using test item 4 for this purpose which has a buff pool size of 50.
     */
    @Test
    public void testPlayerBuffPool()
    {
        Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MARTY.getPlayerID());
        Position playerPosition = new Position(3, 0);
        player.setPosition(playerPosition);

        InteractObjectManager.getSingleton().execute(player.getPlayerID(), 4);

        assertEquals(player.getBuffPool(), 50);
    }
}

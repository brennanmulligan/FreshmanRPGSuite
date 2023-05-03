package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test that a player Id can be found by name
 */
@GameTest("GameShared")
public class FindPlayerByIDByPlayerNameTest
{
    /**
     * Test that the correct Id is returned when searching by player name
     *
     * @throws DatabaseException Shouldn't
     */
    @Test
    public void testGetPlayerIDFromPlayerName() throws DatabaseException
    {
        assertEquals(1, FindPlayerIDFromPlayerName.getPlayerID("John"));
        assertEquals(21, FindPlayerIDFromPlayerName.getPlayerID("Jawn"));
    }

    /**
     * Test that an exception is thrown when no player found
     */
    @Test
    public void testExceptionThrownWhenPlayerNotFound()
    {
        assertThrows(DatabaseException.class, () ->
        {
            assertEquals(21, FindPlayerIDFromPlayerName.getPlayerID("BillyBob"));
        });
    }

}

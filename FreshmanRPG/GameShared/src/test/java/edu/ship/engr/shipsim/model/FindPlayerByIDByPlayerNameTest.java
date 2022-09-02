package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test that a player Id can be found by name
 */
public class FindPlayerByIDByPlayerNameTest extends ServerSideTest
{

    /**
     * Test that the correct Id is returned when searching by player name
     *
     * @throws DatabaseException Shouldn't
     */
    @Test
    public void testgetPlayerIDFromPlayerName() throws DatabaseException
    {
        assertEquals(1, FindPlayerIDFromPlayerName.getPlayerID("John"));
        assertEquals(21, FindPlayerIDFromPlayerName.getPlayerID("Jawn"));
    }

    /**
     * Test that an exception is thrown when no player found
     *
     * @throws DatabaseException Shouldn't
     */
    @Test(expected = DatabaseException.class)
    public void testExceptionThrownWhenPlayerNotFound() throws DatabaseException
    {
        assertEquals(21, FindPlayerIDFromPlayerName.getPlayerID("BillyBob"));
    }

}

package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the functionality required during a login
 *
 * @author Merlin
 */
@GameTest("GameShared")
public class PlayerLoginTest
{
    /**
     * If the name and password are in the db, we should be able to determine the
     * player's unique ID
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void simpleRead() throws DatabaseException
    {
        PlayerLogin pl = new PlayerLogin(PlayersForTest.JOHN.getPlayerName(), PlayersForTest.JOHN.getPlayerPassword());
        assertEquals(1, pl.getPlayerID());
    }

    /**
     * If the player's name isn't in the db, we should throw an exception
     */
    @Test
    public void notThere()
    {
        assertThrows(DatabaseException.class, () ->
        {
            new PlayerLogin(PlayersForTest.JOHN.getPlayerName() + "z", PlayersForTest.JOHN.getPlayerPassword());
        });
    }

    /**
     * If the password given doesn't match the db, we should throw an exception
     */
    @Test
    public void wrongPassword()
    {
        assertThrows(DatabaseException.class, () ->
        {
            new PlayerLogin(PlayersForTest.JOHN.getPlayerName(), PlayersForTest.JOHN.getPlayerPassword() + 'Z');
        });
    }

    /**
     * Test that the password checking actually works.
     *
     * @throws DatabaseException - shouldn't
     */
    @Test
    public void correctPassword() throws DatabaseException
    {
        PlayerLogin l = new PlayerLogin(PlayersForTest.JOHN.getPlayerName(), PlayersForTest.JOHN.getPlayerPassword());
        assertEquals(l.getPlayerName(), PlayersForTest.JOHN.getPlayerName());
        assertEquals(l.getPlayerID(), PlayersForTest.JOHN.getPlayerID());
    }

    /**
     * Test that a user already marked as online cannot
     * login a second time.
     */
    @Test
    @Disabled("We have temporarily disabled blocking multiple logins")
    public void duplicateLogin()
    {
        assertThrows(DatabaseException.class, () ->
        {
            // FIXME: Properly handle multiple logins
            //Merlin is marked as online in the enum already
            //Attempting to login again as MERLIN should throw our database exception
            PlayerLogin player1 = new PlayerLogin(PlayersForTest.MERLIN.getPlayerName(), PlayersForTest.MERLIN.getPlayerPassword());
            assertEquals(player1.getPlayerName(), PlayersForTest.MERLIN.getPlayerName());
            assertEquals(player1.getPlayerID(), PlayersForTest.MERLIN.getPlayerID());
        });
    }
}

package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functionality required during a login
 *
 * @author Merlin
 */
public class PlayerLoginTest
{

    /**
     * @see ServerSideTest#setUp()
     */
    @Before
    public void setUp()
    {
        OptionsManager.getSingleton().setTestMode(true);
    }

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
     *
     * @throws DatabaseException should
     */
    @Test(expected = DatabaseException.class)
    public void notThere() throws DatabaseException
    {
        new PlayerLogin(PlayersForTest.JOHN.getPlayerName() + "z", PlayersForTest.JOHN.getPlayerPassword());
    }

    /**
     * If the password given doesn't match the db, we should throw an exception
     *
     * @throws DatabaseException should
     */
    @Test(expected = DatabaseException.class)
    public void wrongPassword() throws DatabaseException
    {
        new PlayerLogin(PlayersForTest.JOHN.getPlayerName(), PlayersForTest.JOHN.getPlayerPassword() + 'Z');
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
     *
     * @throws DatabaseException should throw since player is marked as online in enum
     */
    @Test(expected = DatabaseException.class)
    @Ignore("We have temporarily disabled blocking multiple logins")
    public void duplicateLogin() throws DatabaseException
    {
        // FIXME: Properly handle multiple logins
        //Merlin is marked as online in the enum already
        //Attempting to login again as MERLIN should throw our database exception
        PlayerLogin player1 = new PlayerLogin(PlayersForTest.MERLIN.getPlayerName(), PlayersForTest.MERLIN.getPlayerPassword());
        assertEquals(player1.getPlayerName(), PlayersForTest.MERLIN.getPlayerName());
        assertEquals(player1.getPlayerID(), PlayersForTest.MERLIN.getPlayerID());

    }

}

package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author Merlin Tests for all of the Player Login row data gateways
 */
public class PlayerLoginRowDataGatewayTest extends ServerSideTest
{

    /**
     * Create the row data gateway for a test based on existing data in the data
     * source
     *
     * @param playerName the name of the player we are looking for
     * @return the gateway for that player's row in the data source
     * @throws DatabaseException if the gateway can't find the player
     */
    PlayerLoginRowDataGateway findRowDataGateway(String playerName) throws DatabaseException
    {
        return new PlayerLoginRowDataGateway(playerName);
    }

    /**
     * Create a gateway that manages a new row being added to the data source
     *
     * @param playerName the name of the player
     * @param password   the player's password
     * @return the gateway we will test
     * @throws DatabaseException if the gateway can't create the row
     */
    PlayerLoginRowDataGateway createRowDataGateway(int playerID, String playerName,
                                                   String password) throws DatabaseException
    {
        return new PlayerLoginRowDataGateway(playerID, playerName, password);
    }

    PlayerLoginRowDataGateway findRowDataGateway(int playerID) throws DatabaseException
    {
        return new PlayerLoginRowDataGateway(playerID);
    }

    /**
     * tests that a person is deleted by their id,
     * if person is searched again, expect to catch exception
     *
     * @throws DatabaseException SHOULD THROW BECAUSE ROW IS DELETED
     */
    @Test(expected = DatabaseException.class)
    public void testDeleteRow() throws DatabaseException
    {
        PlayerLoginRowDataGateway loginRowDataGateway = new PlayerLoginRowDataGateway(20);
        loginRowDataGateway.deleteRow();
        new PlayerLoginRowDataGateway(20);
    }

    /**
     * Check that passwords are hashed appropriately.
     *
     * @throws DatabaseException Probably not
     */
    @Test()
    public void testSetPassword() throws DatabaseException
    {
        PlayerLoginRowDataGateway loginRowDataGateway = new PlayerLoginRowDataGateway(20);
        loginRowDataGateway.setPassword("test");
        assertTrue(loginRowDataGateway.checkPassword("test"));
    }

    /**
     * Verify that login fails if the salt silently changes
     *
     * @throws DatabaseException Probably not
     */
    @Test()
    public void testBadSalt() throws DatabaseException
    {
        byte[] wrongSalt = new byte[32];
        Random rand = new SecureRandom();
        rand.nextBytes(wrongSalt);

        PlayerLoginRowDataGateway loginRowDataGateway = new PlayerLoginRowDataGateway(20);
        loginRowDataGateway.setPassword("test");
        loginRowDataGateway.setSalt(wrongSalt); // Change the salt after the password is set
        assertFalse(loginRowDataGateway.checkPassword("test"));
    }

    protected PlayerLoginRowDataGateway gateway;


    /**
     * Make sure we can add a new user to the system
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void creation() throws DatabaseException
    {
        PlayersForTest player = PlayersForTest.JAWN;
        gateway = findRowDataGateway(player.getPlayerID());
        gateway.deleteRow();
        gateway = createRowDataGateway(
                player.getPlayerID(),
                player.getPlayerName(),
                player.getPlayerPassword());

        PlayerLoginRowDataGateway after = findRowDataGateway(player.getPlayerID());
        assertTrue(after.checkPassword(player.getPlayerPassword()));
    }


    /**
     * Try to create a row for a map file that is already in the database
     *
     * @throws DatabaseException shouldn't
     */
    @Test(expected = DatabaseException.class)
    public void createExisting() throws DatabaseException
    {
        gateway = createRowDataGateway(
                PlayersForTest.MERLIN.getPlayerID(),
                PlayersForTest.MERLIN.getPlayerName(),
                PlayersForTest.MERLIN.getPlayerPassword());
    }

    /**
     * Make sure we can retrieve an existing player
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void findExisting() throws DatabaseException
    {
        gateway = findRowDataGateway(PlayersForTest.MERLIN.getPlayerName());
        assertEquals(2, gateway.getPlayerID());
        assertEquals(PlayersForTest.MERLIN.getPlayerName(), gateway.getPlayerName());
        assertTrue(gateway.checkPassword(PlayersForTest.MERLIN.getPlayerPassword()));
    }

    /**
     * Make sure we can retrieve an existing player
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void findExistingFromPlayerID() throws DatabaseException
    {
        gateway = findRowDataGateway(PlayersForTest.MERLIN.getPlayerID());
        assertEquals(2, gateway.getPlayerID());
        assertEquals(PlayersForTest.MERLIN.getPlayerName(), gateway.getPlayerName());
        assertTrue(gateway.checkPassword(PlayersForTest.MERLIN.getPlayerPassword()));
    }

    /**
     * make sure we get the right exception if we try to find someone who
     * doesn't exist
     *
     * @throws DatabaseException should
     */
    @Test(expected = DatabaseException.class)
    public void findNotExisting() throws DatabaseException
    {
        gateway = findRowDataGateway("no one");
    }

    /**
     * @throws DatabaseException shouldn't
     */
    @Test
    public void changePassword() throws DatabaseException
    {
        gateway = findRowDataGateway(PlayersForTest.MERLIN.getPlayerName());
        gateway.setPassword("not secret");
        gateway.persist();
        PlayerLoginRowDataGateway after =
                findRowDataGateway(PlayersForTest.MERLIN.getPlayerName());
        assertTrue(after.checkPassword("not secret"));
    }
}
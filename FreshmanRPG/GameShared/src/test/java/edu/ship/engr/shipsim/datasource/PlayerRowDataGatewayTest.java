package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests required of all player gateways
 *
 * @author Merlin
 */
@GameTest("GameShared")
public class PlayerRowDataGatewayTest
{
    protected PlayerRowDataGateway gateway;

    /**
     * Find the gateway for a given player
     *
     * @param playerID the ID of the player we are testing
     * @return the gateway
     * @throws DatabaseException if the playerID can't be found in the data
     *                           source
     */
    PlayerRowDataGateway findGateway(int playerID) throws DatabaseException
    {
        return new PlayerRowDataGateway(playerID);
    }

    /**
     * @throws DatabaseException shouldn't
     */
    @Test
    public void finder() throws DatabaseException
    {
        PlayersForTest john = PlayersForTest.JOHN;
        gateway = findGateway(john.getPlayerID());
        assertEquals(john.getPlayerID(), gateway.getPlayerID());

        assertEquals(john.getAppearanceType(), gateway.getAppearanceType());
        assertEquals(john.getExperiencePoints(), gateway.getExperiencePoints());
        assertEquals(john.getCrew(), gateway.getCrew());
        assertEquals(john.getMajor(), gateway.getMajor());
        assertEquals(john.getSection(), gateway.getSection());
        assertEquals(john.getBuffPool(), gateway.getBuffPool());
        assertEquals(john.getOnline(), gateway.getOnline());
    }

    /**
     * Make sure we can add a new user to the system
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void creation() throws DatabaseException
    {
        gateway = createGateway("Warrior", 2, 13, Crew.OFF_BY_ONE,
                Major.SOFTWARE_ENGINEERING, 1, 42, false);

        PlayerRowDataGateway after = findGateway(gateway.getPlayerID());
        assertEquals("Warrior", after.getAppearanceType());
        assertEquals(2, after.getQuizScore());
        assertEquals(13, after.getExperiencePoints());
        assertEquals(Crew.OFF_BY_ONE, after.getCrew());
        assertEquals(Major.SOFTWARE_ENGINEERING, after.getMajor());
        assertEquals(1, after.getSection());
        assertEquals(42, after.getBuffPool());
        assertFalse(after.getOnline());
    }

    /**
     * Get a gateway that creates a row in the data source with the given
     * information
     *
     * @param appearanceType   the appearance type of the player
     * @param quizScore        This player's quiz score
     * @param experiencePoints this player's experience points
     * @param crew             the crew to which this player belongs
     * @param major            the major of this player
     * @param section          the section the player is enrolled in
     * @param buffPool         The size of the buff pool this player has
     * @return the gateway
     * @throws DatabaseException if we fail to create the row
     */
    PlayerRowDataGateway createGateway(String appearanceType, int quizScore,
                                       int experiencePoints, Crew crew, Major major, int section, int buffPool, boolean online) throws DatabaseException
    {
        return new PlayerRowDataGateway(appearanceType, quizScore, experiencePoints, crew, major, section, buffPool, online);
    }

    /**
     * make sure we get the right exception if we try to find someone who
     * doesn't exist
     */
    @Test
    public void findNotExisting()
    {
        assertThrows(DatabaseException.class, () ->
        {
            gateway = findGateway(11111);
        });
    }

    /**
     * Should remove a row from the data source.
     */
    @Test
    public void removeTest()
    {
        assertThrows(DatabaseException.class, () ->
        {
            PlayersForTest player = PlayersForTest.MATT;
            PlayerRowDataGateway gateway = findGateway(player.getPlayerID());

            gateway.delete();

            // should throw an exception because player now removed
            findGateway(player.getPlayerID());
        });
    }


    /**
     * @throws DatabaseException shouldn't
     */
    @Test
    public void changeQuizScore() throws DatabaseException
    {
        gateway = findGateway(PlayersForTest.MERLIN.getPlayerID());
        gateway.setQuizScore(666);
        gateway.persist();
        PlayerRowDataGateway after = findGateway(PlayersForTest.MERLIN.getPlayerID());
        assertEquals(666, after.getQuizScore());
    }

    /**
     * @throws DatabaseException shouldn't
     */
    @Test
    public void changeExperiencePoints() throws DatabaseException
    {
        gateway = findGateway(PlayersForTest.MERLIN.getPlayerID());
        gateway.setExperiencePoints(424);
        gateway.persist();

        PlayerRowDataGateway after = findGateway(PlayersForTest.MERLIN.getPlayerID());
        assertEquals(424, after.getExperiencePoints());

        gateway.resetData();
    }

    /**
     * @throws DatabaseException shouldn't
     */
    @Test
    public void changeAppearanceType() throws DatabaseException
    {
        gateway = findGateway(PlayersForTest.MERLIN.getPlayerID());
        gateway.setAppearanceType("Ugly!");
        gateway.persist();
        PlayerRowDataGateway after = findGateway(PlayersForTest.MERLIN.getPlayerID());
        assertEquals("Ugly!", after.getAppearanceType());

        gateway.resetData();
    }

    /**
     * @throws DatabaseException shouldn't
     */
    @Test
    public void changeBuff() throws DatabaseException
    {
        this.gateway = this.findGateway(PlayersForTest.MERLIN.getPlayerID());

        assertEquals(PlayersForTest.MERLIN.getBuffPool(), this.gateway.getBuffPool());

        this.gateway.setBuffPool(PlayersForTest.MERLIN.getBuffPool() + 10);
        this.gateway.persist();

        PlayerRowDataGateway after = this.findGateway(PlayersForTest.MERLIN.getPlayerID());
        assertEquals(PlayersForTest.MERLIN.getBuffPool() + 10, after.getBuffPool());
    }

    /**
     * Tests that online status can be toggled on and off
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void flipFlopOnline() throws DatabaseException
    {
        this.gateway = this.findGateway(PlayersForTest.MERLIN.getPlayerID());

        //should start online
        assertEquals(PlayersForTest.MERLIN.getOnline(), this.gateway.getOnline());

        //set to offline
        this.gateway.setOnline(false);
        this.gateway.persist();

        //make sure she's offline
        PlayerRowDataGateway offline = this.findGateway(PlayersForTest.MERLIN.getPlayerID());
        assertFalse(offline.getOnline());

        //set back to online
        this.gateway.setOnline(true);
        this.gateway.persist();

        PlayerRowDataGateway online = this.findGateway(PlayersForTest.MERLIN.getPlayerID());
        assertTrue(online.getOnline());
    }

    /**
     * Tests the delete method in PlayerRowDataGateway
     *
     * @throws DatabaseException should
     */
    @Test
    public void testDeletePlayerByIdDeletesCorrectPlayer() throws DatabaseException
    {
        PlayerRowDataGateway firstMerlin = findGateway(PlayersForTest.MERLIN.getPlayerID());
        firstMerlin.delete();

        boolean found = false;
        try
        {
            findGateway(PlayersForTest.MERLIN.getPlayerID());
        }
        catch (DatabaseException e)
        {
            found = true;
        }
        assertTrue(found);
    }

}

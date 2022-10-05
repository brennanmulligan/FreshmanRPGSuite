package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.datatypes.NPCsForTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests required of all player gateways
 *
 * @author Merlin
 */
@GameTest("GameServer")
public class NPCRowDataGatewayTest
{

    protected NPCRowDataGateway gateway;

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
     * @throws DatabaseException shouldn't
     */
    @Test
    public void finder() throws DatabaseException
    {
        NPCsForTest merlin = NPCsForTest.NPC1;
        gateway = findGateway(merlin.getPlayerID());
        assertEquals(merlin.getBehaviorClass(), gateway.getBehaviorClass());
        assertEquals(merlin.getPlayerID(), gateway.getPlayerID());
    }

    /**
     * Make sure that we can find all of the NPCs for a given map
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void findsAllForMap() throws DatabaseException
    {
        ArrayList<NPCRowDataGateway> gateways = getAllForMap("silly.tmx");
        assertEquals(2, gateways.size());
        assertTrue((NPCsForTest.NPC1.getPlayerID() == gateways.get(0).getPlayerID() ||
                NPCsForTest.NPC1.getPlayerID() == gateways.get(1).getPlayerID()));
        assertTrue((NPCsForTest.NPC3.getPlayerID() == gateways.get(0).getPlayerID() ||
                NPCsForTest.NPC3.getPlayerID() == gateways.get(1).getPlayerID()));
    }

    /**
     * Get gateways for all of the NPCs on a given map
     *
     * @param mapName the map we are interested
     * @return the gateways we found
     * @throws DatabaseException if the data source layer has trouble
     */
    public ArrayList<NPCRowDataGateway> getAllForMap(String mapName)
            throws DatabaseException
    {
        return NPCRowDataGateway.getNPCsForMap(mapName);
    }

    /**
     * Test that a NPC gateway is created
     *
     * @throws DatabaseException Shouldn't
     */
    @Test
    public void testCreateConstructor() throws DatabaseException
    {
        PlayersForTest testPlayer = PlayersForTest.FRANK;

        new NPCRowDataGateway(testPlayer.getPlayerID(), "edu.ship.engr.shipsim.model.RedHatBehavior", "");

        NPCRowDataGateway finderTester =
                new NPCRowDataGateway(testPlayer.getPlayerID());

        assertEquals(testPlayer.getPlayerID(), finderTester.getPlayerID());
        assertEquals("edu.ship.engr.shipsim.model.RedHatBehavior", finderTester.getBehaviorClass());
    }

    /**
     * Test that an exception is thrown
     */
    @Test
    public void testDuplicateNotAdded()
    {
        assertThrows(DatabaseException.class, () ->
        {
            NPCsForTest testPlayer = NPCsForTest.NPC1;

            new NPCRowDataGateway(testPlayer.getPlayerID(), testPlayer.getBehaviorClass(), "");
        });
    }

    /**
     * Find a gateway for a given player ID. Allows these tests to be subclassed
     * for each type of gateway ensuring they all work the same.
     *
     * @param playerID the player's unique ID
     * @return the gateway
     * @throws DatabaseException if the playerID can't be found in tthe
     *                           datasource
     */
    NPCRowDataGateway findGateway(int playerID) throws DatabaseException
    {
        return new NPCRowDataGateway(playerID);
    }

}

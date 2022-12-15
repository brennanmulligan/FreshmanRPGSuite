package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.NPCsForTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test NPC Mappers
 *
 * @author Merlin
 */
@GameTest("GameServer")
public class NPCMapperTest extends PlayerMapperTest
{
    /**
     * @see edu.ship.engr.shipsim.model.PlayerMapperTest#getMapper()
     */
    @Override
    protected PlayerMapper getMapper() throws DatabaseException
    {
        return new NPCMapper(getPlayerWeAreTesting().getPlayerID());
    }

    /**
     * @see edu.ship.engr.shipsim.model.PlayerMapperTest#getPlayerWeAreTesting()
     */
    @Override
    protected PlayersForTest getPlayerWeAreTesting()
    {
        return PlayersForTest.QUIZBOT;
    }

    /**
     * When we get the mapper for an existing player, make sure that the NPC
     * specific information is created
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void fillsExisting() throws DatabaseException
    {
        NPCMapper pm = new NPCMapper(NPCsForTest.NPC2.getPlayerID());
        NPC p = (NPC) pm.getPlayer();
        NPCsForTest testPlayer = NPCsForTest.NPC2;
        assertEquals(testPlayer.getBehaviorClass(), p.getBehaviorClass());
    }

    /**
     * Make sure we can find all of the NPCs for a given map
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void findsForMap() throws DatabaseException
    {
        ArrayList<NPCMapper> npcs = NPCMapper.findNPCsOnMap("silly.tmx");
        assertEquals(2, npcs.size());
        assertEquals(PlayersForTest.MOCK_NPC.getPlayerID(), npcs.get(0).getPlayer().getPlayerID());
        assertEquals(PlayersForTest.MOCK_NPC3.getPlayerID(), npcs.get(1).getPlayer().getPlayerID());
    }
}

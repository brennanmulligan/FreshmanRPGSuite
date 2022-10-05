package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 */
@GameTest("GameServer")
public class VisitedMapsGatewayTest
{

    /**
     * test that we can add a map to a player
     *
     * @throws DatabaseException if error occurs during database access
     */
    @Test
    public void testAddMapToPlayer() throws DatabaseException
    {
        @SuppressWarnings("unchecked") ArrayList<String> testList =
                (ArrayList<String>) PlayersForTest.MERLIN.getMapsVisited().clone();
        testList.add("Library");


        VisitedMapsGateway gateway =
                new VisitedMapsGateway(PlayersForTest.MERLIN.getPlayerID(), "Library");

        assertEquals(testList, gateway.getMaps());

    }

    /**
     * test that we can get the maps a player has visited
     *
     * @throws DatabaseException if error occurs during database access
     */
    @Test
    public void testGetVisitedMaps() throws DatabaseException
    {
        ArrayList<String> testList = PlayersForTest.MERLIN.getMapsVisited();

        VisitedMapsGateway gateway =
                new VisitedMapsGateway(PlayersForTest.MERLIN.getPlayerID());
        assertEquals(testList, gateway.getMaps());
    }

}

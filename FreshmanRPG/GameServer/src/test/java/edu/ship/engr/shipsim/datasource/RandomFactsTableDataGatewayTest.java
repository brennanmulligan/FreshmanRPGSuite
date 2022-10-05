package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.RandomFactDTO;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.RandomFactsForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author merlin
 */
@GameTest("GameServer")
public class RandomFactsTableDataGatewayTest
{

    private RandomFactsTableDataGateway gateway;

    @BeforeEach
    public void localSetUp()
    {
        gateway = getGateway();
    }

    /**
     * Make sure we can get all of the facts for a particular NPC
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void canRetrieveAllForOneNPC() throws DatabaseException
    {
        ArrayList<RandomFactDTO> result =
                gateway.getAllFactsForNPC(PlayersForTest.BIG_RED.getPlayerID());
        for (RandomFactsForTest fact : RandomFactsForTest.values())
        {
            if (fact.getNpcID() == PlayersForTest.BIG_RED.getPlayerID())
            {
                assertTrue(result.contains(fact.getDTO()));
            }
            else
            {
                assertFalse(result.contains(fact.getDTO()));
            }
        }
    }

    /**
     * @return the gateway the tests should be run on
     */
    public RandomFactsTableDataGateway getGateway()
    {
        return RandomFactsTableDataGateway.getSingleton();
    }

    /**
     *
     */
    @Test
    public void isASingleton()
    {
        RandomFactsTableDataGateway x = getGateway();
        assertNotNull(x);
        assertSame(x, getGateway());
    }

}

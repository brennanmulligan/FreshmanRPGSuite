package datasource;

import dataDTO.RandomFactDTO;
import datatypes.PlayersForTest;
import datatypes.RandomFactsForTest;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author merlin
 */
public class RandomFactsTableDataGatewayTest extends ServerSideTest
{

    private RandomFactsTableDataGateway gateway;

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

    @Before
    public void localSetUp()
    {
        gateway = getGateway();
    }

}

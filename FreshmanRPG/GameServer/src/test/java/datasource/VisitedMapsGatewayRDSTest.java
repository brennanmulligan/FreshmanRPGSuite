package datasource;

import datatypes.PlayersForTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * test the visited maps gateway RDS
 */
public class VisitedMapsGatewayRDSTest extends DatabaseTest
{
    /**
     * @see datasource.DatabaseTest#setUp()
     */
    @Before
    public void setUp() throws DatabaseException
    {
        super.setUp();
    }

    /**
     * @see datasource.DatabaseTest#tearDown()
     */
    @After
    public void tearDown() throws DatabaseException, SQLException
    {
        super.tearDown();
    }

    /**
     * test that we can add a map to a player
     *
     * @throws DatabaseException if error occurs during database access
     */
    @Test
    public void testAddMapToPlayer() throws DatabaseException
    {
        @SuppressWarnings("unchecked") ArrayList<String> testList =
                (ArrayList<String>)PlayersForTest.MERLIN.getMapsVisited().clone();
        testList.add("Library");


        VisitedMapsGatewayRDS gateway =
                new VisitedMapsGatewayRDS(PlayersForTest.MERLIN.getPlayerID(), "Library");

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

        VisitedMapsGatewayRDS gateway =
                new VisitedMapsGatewayRDS(PlayersForTest.MERLIN.getPlayerID());
        assertEquals(testList, gateway.getMaps());
    }
}

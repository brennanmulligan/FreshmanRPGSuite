package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.ServersForTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Merlin
 */
public class MapToServerMappingTest extends ServerSideTest
{

    private MapToServerMapping map;

    /**
     * Initialize the necessary singletons
     */
    @Before
    public void setup()
    {
        OptionsManager.resetSingleton();
        OptionsManager.getSingleton().setTestMode(true);
    }

    /**
     * Can retrieve one
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void retrieval() throws DatabaseException
    {
        ServersForTest expected = ServersForTest.FIRST_SERVER;
        map = new MapToServerMapping(expected.getMapName());
        assertEquals(expected.getMapName(), map.getMapName());
        assertEquals(expected.getHostName(), map.getHostName());
        assertEquals(expected.getPortNumber(), map.getPortNumber());
    }

    /**
     * Make sure we can change the hostname and port number and update the
     * database appropriately
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void canPersistChanges() throws DatabaseException
    {
        map = new MapToServerMapping(ServersForTest.FIRST_SERVER.getMapName());
        map.setHostName("homehost");
        map.setPortNumber(42);
        map.persist();

        MapToServerMapping mapAfter = new MapToServerMapping(ServersForTest.FIRST_SERVER.getMapName());
        assertEquals(map.getHostName(), mapAfter.getHostName());
        assertEquals(map.getPortNumber(), mapAfter.getPortNumber());
        assertEquals(map.getMapName(), mapAfter.getMapName());
    }

}

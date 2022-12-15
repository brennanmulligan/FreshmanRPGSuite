package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.ServersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Merlin
 */
@GameTest("GameShared")
public class MapToServerMappingTest
{
    private MapToServerMapping map;

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

package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ServerRowDataGateway;
import edu.ship.engr.shipsim.datatypes.ServersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    //Mock created to see if isQuiet is true
    //We will copy and paste this to see if isQuiet is false
    @Test
    public void canSeeQuietTrue() throws DatabaseException
    {
        ServerRowDataGateway gateway = mock(ServerRowDataGateway.class);
        when(gateway.isQuiet()).thenReturn(true);

        MapToServerMapping map = new MapToServerMapping(gateway);
        assertTrue(map.isQuiet());
    }

    @Test
    public void canSeeQuietFalse() throws DatabaseException
    {
        ServerRowDataGateway gateway = mock(ServerRowDataGateway.class);
        when(gateway.isQuiet()).thenReturn(false);

        MapToServerMapping map = new MapToServerMapping(gateway);
        assertFalse(map.isQuiet());
    }

}

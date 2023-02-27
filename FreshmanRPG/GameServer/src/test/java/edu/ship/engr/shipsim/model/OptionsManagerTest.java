package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ServerRowDataGateway;
import edu.ship.engr.shipsim.datatypes.ServersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the OptionsManager
 *
 * @author Steve
 */
@GameTest("GameServer")
public class OptionsManagerTest
{

    /**
     * Reset that singleton
     *
     * @throws DatabaseException shouldn't
     */
    @BeforeEach
    public void localSetup() throws DatabaseException
    {
        MapToServerMapping mapping = new MapToServerMapping(ServersForTest.FIRST_SERVER.getMapName());
        mapping.setHostName("holder");
        mapping.setMapFileTitle(ServersForTest.FIRST_SERVER.getMapName());
        mapping.setPortNumber(0);
        mapping.persist();
    }

    /**
     * Make sure OptionsManager is a resetable singleton
     */
    @Test
    public void isSingleton()
    {
        OptionsManager pm1 = OptionsManager.getSingleton();
        OptionsManager pm2 = OptionsManager.getSingleton();
        assertSame(pm1, pm2);
        OptionsManager.resetSingleton();
        assertNotSame(pm1, OptionsManager.getSingleton());
    }

    /**
     * When we set the map name, the map to server mapping is updated in the
     * database
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void savesServerMapping() throws DatabaseException
    {
        OptionsManager manager = OptionsManager.getSingleton();
        manager.setTestMode(true);
        manager.updateMapInformation(ServersForTest.FIRST_SERVER.getMapName(), "ourhost.com", 1337);

        MapToServerMapping actual = new MapToServerMapping(ServersForTest.FIRST_SERVER.getMapName());
        assertEquals(actual.getHostName(), "ourhost.com");
        assertEquals(actual.getMapName(), ServersForTest.FIRST_SERVER.getMapName());
        assertEquals(actual.getPortNumber(), 1337);
    }

    /**
     * Basic getter test
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void serverMappingGetters() throws DatabaseException
    {
        OptionsManager manager = OptionsManager.getSingleton();
        manager.updateMapInformation(ServersForTest.FIRST_SERVER.getMapName(), "ourhost.com", 1337);

        assertEquals(ServersForTest.FIRST_SERVER.getMapName(), manager.getMapFileTitle());
        assertEquals("ourhost.com", manager.getHostName());
        assertEquals(1337, manager.getPortNumber());
    }


    @Test
    public void testGetterOfIsQuietServer()
    {
        MapToServerMapping mapToServerMapping = mock(MapToServerMapping.class);
        when(mapToServerMapping.isQuiet()).thenReturn(true);

        OptionsManager receives =  OptionsManager.getSingleton();
        receives.setMapToServerMapping(mapToServerMapping);

        assertTrue(receives.isQuiet());
    }

    @Test
    public void testGetterOfIsNotQuietServer()
    {
        MapToServerMapping mapToServerMapping = mock(MapToServerMapping.class);
        when(mapToServerMapping.isQuiet()).thenReturn(false);

        OptionsManager receives =  OptionsManager.getSingleton();
        receives.setMapToServerMapping(mapToServerMapping);

        assertFalse(receives.isQuiet());
    }
}

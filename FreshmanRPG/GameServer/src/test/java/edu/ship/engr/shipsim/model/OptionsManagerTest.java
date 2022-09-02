package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.ServersForTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the OptionsManager
 *
 * @author Steve
 */
public class OptionsManagerTest extends ServerSideTest
{

    /**
     * Reset that singleton
     *
     * @throws DatabaseException shouldn't
     */
    @Before
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
}

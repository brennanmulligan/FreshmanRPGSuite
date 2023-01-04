package edu.ship.engr.shipsim.model;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.ObjectMap;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.NewMapReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Merlin
 */
@GameTest("GameClient")
@ResetClientModelFacade
@ResetReportObserverConnector
public class MapManagerTest
{
    /**
     * make sure it is a good singleton
     */
    @Test
    public void isResetableSingleton()
    {
        MapManager facade1 = MapManager.getSingleton();
        MapManager facade2 = MapManager.getSingleton();
        assertSame(facade1, facade2);
        MapManager.resetSingleton();
        assertNotSame(facade1, MapManager.getSingleton());
    }

    /**
     * Should update observers of NewMapReport when it gets a new map
     */
    @Test
    public void updatesOnNewMap()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a NewMapReport is sent
        connector.registerObserver(observer, NewMapReport.class);

        MapManager map = MapManager.getSingleton();
        map.changeToNewFile("simple.tmx", "updates on new map in MapManagerTest");

        // set up the report for verification
        NewMapReport expectedReport = new NewMapReport(map.getTiledMap());

        // since changeToNewFile sends a NewMapReport, verify that the observer was notified of it
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }

    /**
     * Since we can't really open the tmx file (libgdx can't do that headless),
     * we have to mock it
     */
    @Test
    public void canMockTiledMapAndGetALayer()
    {
        TiledMap tiledMap = mock(TiledMap.class);
        MapLayers mapLayers = mock(MapLayers.class);
        MapLayer mapLayer = mock(MapLayer.class);

        when(tiledMap.getLayers()).thenReturn(mapLayers);

        when(mapLayers.get("Foreground")).thenReturn(mapLayer);

        MapManager.getSingleton().setMap(tiledMap);
        MapLayer layer = MapManager.getSingleton().getMapLayer("Foreground");
        assertEquals(mapLayer, layer);
    }

    /**
     * Make sure that the passabilityMap, once created, can be properly
     * processed
     */
    @Test
    public void canCheckCollision()
    {
        MapManager map = MapManager.getSingleton();

        boolean[][] passMap =
                {
                        {false, false, true, false},
                        {false, true, true, false},
                        {false, true, false, false},
                        {true, true, false, false}};

        map.setPassability(passMap);

        assertFalse(map.getIsTilePassable(new Position(0, 0)));
        assertFalse(map.getIsTilePassable(new Position(0, 1)));
        assertTrue(map.getIsTilePassable(new Position(0, 2)));
        assertFalse(map.getIsTilePassable(new Position(0, 3)));
        assertFalse(map.getIsTilePassable(new Position(1, 0)));
        assertTrue(map.getIsTilePassable(new Position(2, 1)));
        assertFalse(map.getIsTilePassable(new Position(3, 2)));
    }

    /**
     * Make sure the map manager recognizes when a position is
     * a teleportation hotspot.
     */
    @Test
    public void canCheckTeleport()
    {
        MapManager map = MapManager.getSingleton();

        ObjectMap<Position, TeleportHotSpot> teleportMap = new ObjectMap<>();
        TeleportHotSpot hotspot = new TeleportHotSpot("Map Name", new Position(1, 2));

        teleportMap.put(new Position(5, 5), hotspot);

        map.setTeleportHotspots(teleportMap);

        assertTrue(map.getIsTileTeleport(new Position(5, 5)));
        assertFalse(map.getIsTileTeleport(new Position(0, 0)));
    }


}

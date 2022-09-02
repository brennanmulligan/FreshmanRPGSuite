package edu.ship.engr.shipsim.model;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.ObjectMap;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.NewMapReport;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Merlin
 */
public class MapManagerTest
{
    /**
     * reset the singleton
     */
    @Before
    public void setup()
    {
        QualifiedObservableConnector.resetSingleton();
        ClientModelFacade.resetSingleton();
        ClientModelFacade.getSingleton(true, false);
    }

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
        QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
        QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
        cm.registerObserver(obs, NewMapReport.class);
        obs.receiveReport(EasyMock.isA(NewMapReport.class));
        EasyMock.replay(obs);

        MapManager map = MapManager.getSingleton();

        map.changeToNewFile("simple.tmx", "updates on new map in MapManagerTest");
        EasyMock.verify(obs);
    }

    /**
     * Since we can't really open the tmx file (libgdx can't do that headless),
     * we have to mock it
     */
    @Test
    public void canMockTiledMapAndGetALayer()
    {
        TiledMap tiledMap = EasyMock.createMock(TiledMap.class);
        MapLayers mapLayers = EasyMock.createMock(MapLayers.class);
        EasyMock.expect(tiledMap.getLayers()).andReturn(mapLayers);
        MapLayer mapLayer = EasyMock.createMock(MapLayer.class);
        EasyMock.expect(mapLayers.get("Foreground")).andReturn(mapLayer);
        EasyMock.replay(tiledMap);
        EasyMock.replay(mapLayers);
        EasyMock.replay(mapLayer);

        MapManager.getSingleton().setMap(tiledMap);
        MapLayer layer = MapManager.getSingleton().getMapLayer("Foreground");
        assertEquals(mapLayer, layer);

        EasyMock.verify(tiledMap);
        EasyMock.verify(mapLayers);
        EasyMock.verify(mapLayer);
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

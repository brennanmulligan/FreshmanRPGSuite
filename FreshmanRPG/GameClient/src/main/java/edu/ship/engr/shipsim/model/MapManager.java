package edu.ship.engr.shipsim.model;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import edu.ship.engr.shipsim.datasource.ContentLoader;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.NewMapReport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Merlin
 */
public class MapManager
{

    private static final String COLLISION_LAYER = "Collision";
    private static final String REGION_LAYER = "Regions";
    private static MapManager singleton;
    private TiledMap tiledMap;
    private boolean[][] passabilityMap;
    private boolean headless;
    private boolean noCollisionLayer;
    private ObjectMap<Position, TeleportHotSpot> teleportMap;
    private Array<MapObject> regions;
    private boolean recentlyChanged;


    /**
     * Make the default constructor private
     */
    private MapManager()
    {
        if (OptionsManager.getSingleton().isTestMode())
        {
            headless = true;
        }
    }

    /**
     * @return the only one of these there is
     */
    public synchronized static MapManager getSingleton()
    {
        if (singleton == null)
        {
            singleton = new MapManager();
        }
        return singleton;
    }

    /**
     * Used for testing purposes
     */
    public static void resetSingleton()
    {
        OptionsManager.getSingleton().assertTestMode();
        singleton = null;
    }

    /**
     * @param fileTitle    the title of the file we should switch to
     * @param fileContents
     */
    public void changeToNewFile(String fileTitle, String fileContents)
    {
        File mapFile = ContentLoader.getMapFile("current.tmx");

        try (FileWriter f = new FileWriter(mapFile))
        {
            f.write(fileContents);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        if (!headless)
        {
            setMap(new TmxMapLoader().load(mapFile.getPath()));
        }
        else
        {
            this.noCollisionLayer = true;
        }
        if (!OptionsManager.getSingleton().isTestMode())
        {
            recentlyChanged = true;
            // This will set recentlyChanged to false 6 seconds from now
            setTimerToMeasureRecently();
        }
        else
        {
            recentlyChanged = false;
        }
        ReportObserverConnector.getSingleton()
                .sendReport(new NewMapReport(tiledMap));
    }

    /**
     * @param p the location in that map that is being checked to see if it is with
     *          a particular region
     * @return a name of a region if the position is in one, or null if not
     */
    public String getIsInRegion(Position p)
    {
        Vector2 pos = this.positionToPixel(p);
        String name = null;
        if (regions != null)
        {
            for (int i = 0; i < regions.size && name == null; i++)
            {
                MapObject object = regions.get(i);
                //go through types
                if (object instanceof RectangleMapObject)
                {
                    Rectangle shape = ((RectangleMapObject) object).getRectangle();
                    if (shape.contains(pos))
                    {
                        name = object.getName();
                    }
                }
                else if (object instanceof CircleMapObject)
                {
                    Circle shape = ((CircleMapObject) object).getCircle();
                    if (shape.contains(pos))
                    {
                        name = object.getName();
                    }
                }
                else if (object instanceof EllipseMapObject)
                {
                    Ellipse shape = ((EllipseMapObject) object).getEllipse();
                    if (shape.contains(pos))
                    {
                        name = object.getName();
                    }
                }
                else if (object instanceof PolygonMapObject)
                {
                    Polygon shape = ((PolygonMapObject) object).getPolygon();
                    if (shape.contains(pos.x, pos.y))
                    {
                        name = object.getName();
                    }
                }
            }
        }
        return name;
    }

    /**
     * @param p The position to check
     * @return TRUE if the given position is passable terrain, FALSE if not
     */
    public boolean getIsTilePassable(Position p)
    {
        // allow walking anywhere when there is no collision layer
        if (noCollisionLayer)
        {
            return true;
        }
        // prevent walking out of bounds
        if (p.getColumn() >= this.passabilityMap[0].length || p.getColumn() < 0
                || p.getRow() >= this.passabilityMap.length || p.getRow() < 0)
        {
            return false;
        }
        // check against the passability map for capable movement
        return this.passabilityMap[p.getRow()][p.getColumn()];
    }

    /**
     * @param p the position of the tile that is being checked to see if it's a
     *          teleportation hotspot
     * @return true if the tile is a teleportation hotspot
     * else return false
     */
    public boolean getIsTileTeleport(Position p)
    {
        if (this.teleportMap == null)
        {
            return false;
        }
        return teleportMap.containsKey(p);
    }

    /**
     * gets the map layer from the tiled map
     *
     * @param layerName the name of the layer to get
     * @return MapLayer the map layer
     */
    public MapLayer getMapLayer(String layerName)
    {
        MapLayers layers = tiledMap.getLayers();
        return layers.get(layerName);
    }

    public boolean getRecentlyChanged()
    {
        return recentlyChanged;
    }

    /**
     * @param thePosition the position the player is currently in
     * @return telportMap's value where the key is thePosition
     */
    public TeleportHotSpot getTeleportHotSpot(Position thePosition)
    {
        return teleportMap.get(thePosition);
    }

    /**
     * @param p the position we're converting
     * @return a position into a pixel scaled vector
     */
    public Vector2 positionToPixel(Position p)
    {
        Vector2 vec;
        int width = 1;
        int height = 1;
        if (tiledMap != null)
        {
            MapProperties prop = tiledMap.getProperties();
            width = prop.get("tilewidth", Integer.class);
            height = prop.get("tileheight", Integer.class);
        }
        vec = new Vector2(p.getColumn() * width, p.getRow() * height);

        return vec;
    }

    /**
     * Whether Gdx is running
     *
     * @param headless boolean running or not
     */
    public void setHeadless(boolean headless)
    {
        this.headless = headless;
    }

    /**
     * @return the tiledMap object
     */
    public TiledMap getTiledMap()
    {
        return tiledMap;
    }

    /**
     * setting the Tiled map that is managed by this
     *
     * @param tiledMap TiledMap map
     */
    public void setMap(TiledMap tiledMap)
    {
        this.tiledMap = tiledMap;

        // set the map's passability
        TiledMapTileLayer collisionLayer = null;
        MapLayer regionLayer = null;
        MapProperties properties = null;

        if (!headless)
        {
            collisionLayer = (TiledMapTileLayer) tiledMap.getLayers()
                    .get(COLLISION_LAYER);
            properties = this.tiledMap.getProperties();
            regionLayer = tiledMap.getLayers().get(REGION_LAYER);
        }

        if (collisionLayer != null)
        {
            int width = tiledMap.getProperties().get("width", Integer.class);
            int height = tiledMap.getProperties().get("height", Integer.class);
            this.passabilityMap = new boolean[height][width];

            for (int row = 0; row < height; row++)
            {
                for (int col = 0; col < width; col++)
                {
                    Cell cell = collisionLayer.getCell(col, row);
                    passabilityMap[height - row - 1][col] = (cell == null);
                }
            }

        }
        else
        {
            noCollisionLayer = true;
        }

        //parse out regions, which are mainly used for quests
        if (regionLayer != null)
        {
            this.regions = new Array<>();
            for (MapObject object : regionLayer.getObjects())
            {
                //only add named regions, else we can't identify where we are
                if (object.getName() != null)
                {
                    regions.add(object);
                }
            }
        }

        //handle parsing out teleportation hotspots
        if (properties != null)
        {
            if (this.teleportMap == null)
            {
                this.teleportMap = new ObjectMap<>();
            }
            this.teleportMap.clear();

            Iterator<String> propKeys = properties.getKeys();
            while (propKeys.hasNext())
            {
                String key = propKeys.next();
                //parse position of the hotspot when a property isn't a hotspot definition
                if (key.matches("[0-9]+ [0-9]+"))
                {
                    String[] values = key.split(" ", 2);
                    int col, row;
                    col = Integer.parseInt(values[0]);
                    row = Integer.parseInt(values[1]);
                    Position from = new Position(row, col);

                    values = properties.get(key).toString().split(" ");
                    String mapName = values[0];
                    col = Integer.parseInt(values[1]);
                    row = Integer.parseInt(values[2]);
                    Position to = new Position(row, col);

                    TeleportHotSpot hotspot = new TeleportHotSpot(mapName, to);
                    this.teleportMap.put(from, hotspot);
                }
            }
        }
    }

    /**
     * Sets the passabilityMap for testing purposes
     *
     * @param pass The new passabilityMap
     */
    public void setPassability(boolean[][] pass)
    {
        this.noCollisionLayer = false;
        this.passabilityMap = pass;
    }

    /**
     * Sets the teleport hotspot locations for testing purposes
     *
     * @param teleportMap The hashmap of teleport hotspots
     */
    public void setTeleportHotspots(ObjectMap<Position, TeleportHotSpot> teleportMap)
    {
        this.teleportMap = teleportMap;
    }

    /**
     * The server needs our old connection to time out in order to clean everything up.
     * Therefore, when we arrive at a new map, we need to not teleport for 6 seconds.
     * That is why we keep track of "recentlyChanged".
     */
    private void setTimerToMeasureRecently()
    {
        TimerTask task = new TimerTask()
        {
            public void run()
            {
                recentlyChanged = false;
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 6000L;
        timer.schedule(task, delay);
    }
}

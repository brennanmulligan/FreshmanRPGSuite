package model;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import datasource.DatabaseException;
import datasource.ServerRowDataGateway;
import datasource.ServerRowDataGatewayMock;
import datasource.ServerRowDataGatewayRDS;
import datatypes.Position;

import java.util.Iterator;

/**
 * Holds the map information that will be passed to the server for teleport
 */
public class ServerMapManager
{
	private static ServerMapManager singleton;
	private TiledMap tiledMap;
	private boolean[][] passabilityMap;
	private boolean headless;
	private boolean noCollisionLayer;
	private ObjectMap<Position, TeleportHotSpot> teleportMap;
	private Array<MapObject> regions;

	private static final String COLLISION_LAYER = "Collision";
	private static final String REGION_LAYER = "Regions";

	/**
	 * Make the default constructor private
	 */
	private ServerMapManager()
	{
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			headless = true;
		}
	}

	/**
	 * Used for testing purposes
	 */
	public static void resetSingleton()
	{
		singleton = null;
	}

	/**
	 * @return the only one of these there is
	 */
	public synchronized static ServerMapManager getSingleton()
	{
		if (singleton == null)
		{
			singleton = new ServerMapManager();
		}
		return singleton;
	}

	/**
	 * @param fileTitle
	 *            the title of the file we should switch to
	 */
	public void changeToNewFile(String fileTitle)
	{
		if (!headless)
		{

			setMap(new TmxMapLoader().load(fileTitle));
		}
		else
		{
			this.noCollisionLayer = true;
		}
	}

	/**
	 * setting the Tiled map that is managed by this
	 *
	 * @param tiledMap
	 *            TiledMap map
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
					TiledMapTileLayer.Cell cell = collisionLayer.getCell(col, row);
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
	 * Returns the position based on the mapTitle
	 * @param mapTitle the title of the map we are interested in
	 * @return the default starting position
	 */
	public Position getDefaultPositionForMap(String mapTitle)
	{
		try
		{
			ServerRowDataGateway gateway =
					ServerRowDataGatewayRDS.findPosAndMapNameFromMapTitle(mapTitle);
			return new Position(gateway.getTeleportPositionX(),
					gateway.getTeleportPositionY());
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * @param destination the file title of the map we are interested in
	 * @return the name of the map we are interested in
	 */
	public String getMapNameFromMapTitle(String destination)
	{

		String mapName = null;
		try
		{
			ServerRowDataGateway gateway = ServerRowDataGatewayRDS.findPosAndMapNameFromMapTitle(destination);
			mapName = gateway.getMapName();
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}

		return mapName;
	}

	/**
	 * @param mapName the map name
	 * @return the mapName from the MapTitle
	 */
	public String getMapTitleFromMapName(String mapName)
	{
		String mapTitle = null;
		try
		{
			ServerRowDataGateway gateway;
			if (OptionsManager.getSingleton().isUsingMockDataSource())
			{
				gateway = new ServerRowDataGatewayMock(mapName);
			}
			else
			{
				gateway = ServerRowDataGatewayRDS.findServerInfoFromMapName(mapName);
			}
			mapTitle = gateway.getMapTitle();
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
		return mapTitle;
	}

	public boolean[][] getPassabilityMap()
	{
		return passabilityMap;
	}
}

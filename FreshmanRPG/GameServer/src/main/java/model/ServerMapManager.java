package model;

import datasource.DatabaseException;
import datasource.ServerRowDataGateway;
import datasource.ServerRowDataGatewayMock;
import datasource.ServerRowDataGatewayRDS;
import datatypes.Position;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Holds the map information that will be passed to the server for teleport
 */
public class ServerMapManager
{
	private static ServerMapManager singleton;
	private boolean[][] collisionMap;


	private static final String COLLISION_LAYER = "Collision";
	private static final String REGION_LAYER = "Regions";

	/**
	 * Make the default constructor private
	 */
	private ServerMapManager()
	{
		String mapFile = OptionsManager.getSingleton().getMapName();
		loadMapData(mapFile);
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
	 * Appends absolute path to map file, may change if project file system is restructured
	 * @param mapFile name of map file
	 * @return absolute path
	 */
	public String findMapFileAbsolutePath(String mapFile)
	{
		String mapFilePath = "";
		URI path;
		try
		{
			path = SmartPath.class.getProtectionDomain().getCodeSource()
					.getLocation().toURI();
		}
		catch (URISyntaxException e1)
		{
			e1.printStackTrace();
			return "";
		}

		try
		{
			URL decodedPath = path.toURL();

			mapFilePath = (new URL(decodedPath, "../../maps/" + mapFile))
					.toURI().getSchemeSpecificPart();
		}
		catch (MalformedURLException | URISyntaxException e)
		{
			e.printStackTrace();
		}

		return mapFilePath;
	}

	/**
	 * Loads map collision data
	 * @param fileTitle title of map file
	 */
	public void loadMapData(String fileTitle)
	{
		String mapFilePath = findMapFileAbsolutePath(fileTitle);
		loadMapDimensions(mapFilePath);
		parseCollisionMapFromTMX(mapFilePath);
	}

	/**
	 * Load map dimensions from file
	 * @param fileTitle title of map file
	 */
	private void loadMapDimensions(String fileTitle)
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();


		DocumentBuilder docBuilder;
		try
		{
			docBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(fileTitle));
			doc.getDocumentElement().normalize();
			NodeList list = doc.getElementsByTagName("map");
			Element element = (Element) list.item(0);
			String widthAsString = element.getAttribute("width");
			String heightAsString = element.getAttribute("height");
			widthAsString = widthAsString.trim();
			heightAsString = heightAsString.trim();
			int mapWidth = Integer.parseInt(widthAsString);
			int mapHeight = Integer.parseInt(heightAsString);
			collisionMap = new boolean[mapHeight][mapWidth];


		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Parses a TMX file for it's collision layer and returns the collisionMap
	 * @param mapFilePath path to map file
	 */
	private void parseCollisionMapFromTMX(String mapFilePath)
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();


		DocumentBuilder docBuilder;
		try
		{
			docBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(mapFilePath));
			doc.getDocumentElement().normalize();
			NodeList list = doc.getElementsByTagName("layer");

			int temp = 0;
			Node node = list.item(temp);
			Element element = (Element) node;
			while (!element.getAttribute("name").equals(COLLISION_LAYER))
			{
				temp++;
				node = list.item(temp);
				element = (Element) node;
			}

			if (temp < list.getLength())
			{
				loadCollisionMapFromElement(element);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Loads collision map from an element assumed to hold collision data
	 * @param element assumed to hold collision data
	 */
	private void loadCollisionMapFromElement(Element element)
	{
		String message = element.getTextContent();
		message = message.trim();
		String[] rowsOfMap = message.split("\n");
		for(int row = 0; row < rowsOfMap.length; row++)
		{
			String[] rowOfMap = rowsOfMap[row].split(",");
			for(int col = 0; col < rowOfMap.length; col++)
			{
				if(!rowOfMap[col].equals("0"))
				{
					collisionMap[row][col] = true;
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

	/**
	 * Gets collision map
	 * @return collisionMap
	 */
	public boolean[][] getCollisionMap()
	{
		return collisionMap;
	}
}

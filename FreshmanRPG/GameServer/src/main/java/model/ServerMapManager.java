package model;

import datasource.DatabaseException;
import datasource.ServerRowDataGateway;
import datasource.ServerRowDataGatewayMock;
import datasource.ServerRowDataGatewayRDS;
import datatypes.Position;

/**
 * Holds the map information that will be passed to the server for teleport
 */
public class ServerMapManager
{
	private static ServerMapManager singleton;

	/**
	 * Make the default constructor private
	 */
	private ServerMapManager()
	{

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


}

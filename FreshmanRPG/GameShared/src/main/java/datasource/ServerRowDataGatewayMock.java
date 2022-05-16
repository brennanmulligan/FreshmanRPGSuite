package datasource;

import java.util.HashMap;

import dataDTO.PlayerMapLocationDTO;
import model.OptionsManager;
import datatypes.ServersForTest;

/**
 * A mock data source that returns test data. It is initialized with the data in
 * ServersInDB
 *
 * @see ServersForTest
 * @author Carol
 *
 */
public class ServerRowDataGatewayMock implements ServerRowDataGateway
{

	private static class Server
	{
		private String hostName;

		private int portNumber;

		private int mapID;

		private String mapTitle;

		private int teleportPositionX;

		private int teleportPositionY;

		/**
		 * Copy Constructor
		 *
		 * @param server
		 *            the one we want to copy
		 */
		public Server(Server server)
		{
			this.hostName = server.hostName;
			this.portNumber = server.portNumber;
			this.mapTitle = server.mapTitle;
			this.mapID = server.mapID;
			this.teleportPositionX = server.teleportPositionX;
			this.teleportPositionY = server.teleportPositionY;
		}

		public Server(String hostName, int portNumber, String mapTitle, int teleportPositionX, int teleportPositionY)
		{
			this.hostName = hostName;
			this.portNumber = portNumber;
			this.mapTitle = mapTitle;
			this.teleportPositionY = teleportPositionY;
			this.teleportPositionX = teleportPositionX;
		}

		public String getHostName()
		{
			return hostName;
		}

		public int getPortNumber()
		{
			return portNumber;
		}

		public String getMapTitle()
		{
			return mapTitle;
		}

		public int getTeleportPositionX()
		{
			return teleportPositionX;
		}

		public int getTeleportPositionY()
		{
			return teleportPositionY;
		}

	}

	private String mapName;
	private Server server;
	private String originalMapName;

	private static HashMap<String, Server> servers;

	/**
	 *
	 */
	public ServerRowDataGatewayMock()
	{
		if (servers == null)
		{
			resetData();
		}
	}

	/**
	 * Find Constructor: initializes itself with data in the data source
	 *
	 * @param mapName
	 *            the map we are interested in
	 * @throws DatabaseException
	 *             if we can't find data for the given map name
	 */
	public ServerRowDataGatewayMock(String mapName) throws DatabaseException
	{
		this();
		originalMapName = mapName;

		this.mapName = mapName;
		if (!servers.containsKey(mapName))
		{
			throw new DatabaseException("Couldn't find a server for map named " + mapName);
		}
		this.server = new Server(servers.get(mapName));

	}

	/**
	 * Create constructor - data will be added as a new row in the data source
	 *
	 * @param mapName
	 *            the name of the map
	 * @param hostName
	 *            the host name serving that map
	 * @param portNumber
	 *            the port number associated with that map
	 * @param mapTitle
	 * 			  the user friendly name of the map
	 * @param teleportPositionY
	 * 			  the y position for teleportation
	 * @param teleportPositionX
	 * 			  the x position for teleportation
	 * @throws DatabaseException
	 *             if an entry for this map already exists
	 */
	public ServerRowDataGatewayMock(String mapName, String hostName, int portNumber, String mapTitle, int teleportPositionX, int teleportPositionY) throws DatabaseException
	{
		this();
		if (servers.containsKey(mapName))
		{
			throw new DatabaseException("Couldn't create a server for map named " + mapName);
		}
		servers.put(mapName, new Server(hostName, portNumber, mapTitle, teleportPositionX, teleportPositionY));
	}

	/**
	 * @see datasource.ServerRowDataGateway#getHostName()
	 */
	@Override
	public String getHostName()
	{
		return server.getHostName();
	}

	/**
	 * @see datasource.ServerRowDataGateway#getMapName()
	 */
	@Override
	public String getMapName()
	{
		return this.mapName;
	}

	/**
	 * @see datasource.ServerRowDataGateway#getPortNumber()
	 */
	@Override
	public int getPortNumber()
	{
		return server.getPortNumber();
	}

	/**
	 * @see datasource.ServerRowDataGateway#persist()
	 */
	@Override
	public void persist()
	{
		if (!OptionsManager.getSingleton().getHostName().equals("localhost"))
		{
			servers.remove(this.originalMapName);
			servers.put(mapName, server);
		}
	}

	/**
	 * @see datasource.ServerRowDataGateway#resetData()
	 */
	public void resetData()
	{
		servers = new HashMap<>();
		for (ServersForTest s : ServersForTest.values())
		{
			servers.put(s.getMapName(), new Server(s.getHostName(), s.getPortNumber(), s.getMapTitle(), s.getTeleportPositionX(), s.getTeleportPositionY()));
		}
	}

	/**
	 * @see datasource.ServerRowDataGateway#setHostName(java.lang.String)
	 */
	@Override
	public void setHostName(String hostName)
	{
		server.hostName = hostName;
	}

	/**
	 * @see datasource.ServerRowDataGateway#setMapName(java.lang.String)
	 */
	@Override
	public void setMapName(String mapName)
	{
		this.mapName = mapName;

	}

	/**
	 * @see datasource.ServerRowDataGateway#setPortNumber(int)
	 */
	@Override
	public void setPortNumber(int portNumber)
	{
		server.portNumber = portNumber;
	}

	/**
	 * Get the map title 
	 */
	@Override
	public String getMapTitle()
	{
		return server.getMapTitle();
	}

	/**
	 * set the map title
	 */
	@Override
	public void setMapTitle(String mapTitle)
	{
		server.mapTitle = mapTitle;
	}

	/**
	 * @see datasource.ServerRowDataGateway#getTeleportPositionX()
	 */
	@Override
	public int getTeleportPositionX()
	{
		if (server == null)
		{
			return -1;
		}
		return server.getTeleportPositionX();
	}

	/**
	 * @see datasource.ServerRowDataGateway#getTeleportPositionY()
	 */
	@Override
	public int getTeleportPositionY()
	{
		if (server == null)
		{
			return -1;
		}
		return server.getTeleportPositionY();
	}

	/**
	 * @see datasource.ServerRowDataGateway#setTeleportPositionX(int)
	 */
	@Override
	public void setTeleportPositionX(int xPosition)
	{
		server.teleportPositionX = xPosition;
	}

	/**
	 * @see datasource.ServerRowDataGateway#setTeleportPositionY(int)
	 */
	@Override
	public void setTeleportPositionY(int yPosition)
	{
		server.teleportPositionY = yPosition;
	}

	@Override
	public PlayerMapLocationDTO getPosition()
	{
		return new PlayerMapLocationDTO(server.teleportPositionX, server.teleportPositionY);
	}

	@Override
	public int getMapID()
	{
		return server.mapID;
	}

	private void setServer(Server s)
	{
		this.server = s;
	}
	public static ServerRowDataGateway findPosAndMapNameFromMapTitle(String mapTitle)
	{
		ServerRowDataGatewayMock gateway = new ServerRowDataGatewayMock();
		for (String mapName: servers.keySet())
		{
			Server s = servers.get(mapName);
			if (s.mapTitle.equals(mapTitle))
			{
				gateway.setServer(s);
				gateway.setMapName(mapName);
			}
		}

		return gateway;
	}
}

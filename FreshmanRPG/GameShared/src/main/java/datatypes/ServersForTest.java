package datatypes;

/**
 * Used to initialize the database to have dummy data for map to server mappings
 *
 * @author Merlin
 *
 */
public enum ServersForTest
{
	/**
	 *
	 */
	FIRST_SERVER("map1.tmx", "host1.com", 1871, "Map1", 10, 10),
	/**
	 *
	 */
	SECOND_SERVER("map2.tmx", "host2.com", 1872, "Map2", 10, 10),
	/**
	 *
	 */
	CURRENT("current.tmx", "localhost", 1872, "Current", 13, 4),
	/**
	 *
	 */
	QUIZNASIUM("quiznasium.tmx", "localhost", 1873, "Quiznasium", 10, 10),
	/**
	 *
	 */
	HOMEWORK("homework.tmx", "localhost", 1874, "Homework", 10, 10),
	/**
	 *
	 */
	SORTINGROOM("sortingRoom.tmx", "localhost", 1875, "SortingRoom", 10, 10),
	/**
	 *
	 */
	WELLINGTONROOM("wellingtonRoom.tmx", "localhost", 1876, "WellingtonRoom", 10, 10),
	/**
	 *
	 */
	MCT1("mct1.tmx", "localhost", 1877, "MCT1", 10, 10),
	/**
	 *
	 */
	THEGREEN("theGreen.tmx", "localhost", 1878, "TheGreen", 10, 10),
	/**
	 *
	 */
	DUCKTOPIA("Ducktopia.tmx", "localhost", 1879, "Ducktopia", 10, 10),
	/**
	 * 
	 */
	CUB("cub.tmx", "localhost", 1880, "Cub", 10, 10);
	
	private String mapName;

	private String hostName;

	private int portNumber;
	
	private String mapTitle;

	private int teleportPositionX;

	private int teleportPositionY;

	ServersForTest(String mapName, String hostName, int portNumber, String mapTitle, int teleportPositionX, int teleportPositionY)
	{
		this.mapName = mapName;
		this.hostName = hostName;
		this.portNumber = portNumber;
		this.mapTitle = mapTitle;
		this.teleportPositionX = teleportPositionX;
		this.teleportPositionY = teleportPositionY;
	}

	/**
	 * Get the host name of the server that should manage that map
	 *
	 * @return the host name portion of the server's url
	 */
	public String getHostName()
	{
		return hostName;
	}

	/**
	 * get the map this mapping relates to
	 *
	 * @return the file name of the tmx file
	 */
	public String getMapName()
	{
		return mapName;
	}

	/**
	 * The port number that the server managing this map will listen to
	 *
	 * @return the port number
	 */
	public int getPortNumber()
	{
		return portNumber;
	}

	/**
	 * @return mapTitle user friendly title of the map
	 */
	public String getMapTitle()
	{
		return mapTitle;
	}

	public int getTeleportPositionX()
	{
		// TODO Auto-generated method stub
		return teleportPositionX;
	}

	public int getTeleportPositionY()
	{
		// TODO Auto-generated method stub
		return teleportPositionY;
	}

}

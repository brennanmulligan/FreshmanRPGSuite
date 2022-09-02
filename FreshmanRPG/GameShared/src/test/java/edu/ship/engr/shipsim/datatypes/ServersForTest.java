package edu.ship.engr.shipsim.datatypes;

/**
 * Used to initialize the database to have dummy data for map to server mappings
 *
 * @author Merlin
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
    REC_CENTER("recCenter.tmx", "localhost", 1873, "Rec Center", 4, 20),
    /**
     *
     */
    LIBRARY("library.tmx", "localhost", 1874, "Library", 48, 22),
    /**
     *
     */
    SORTINGROOM("sortingRoom.tmx", "localhost", 1875, "SortingRoom", 7, 12),
    /**
     *
     */
    WELLINGTONROOM("wellingtonRoom.tmx", "localhost", 1876, "WellingtonRoom", 6, 89),
    /**
     *
     */
    MCT1("mct1.tmx", "localhost", 1877, "MCT1", 33, 55),

    /**
     *
     */
    DUCKTOPIA("Ducktopia.tmx", "localhost", 1879, "Ducktopia", 28, 56),
    /**
     *
     */
    CUB("cub.tmx", "localhost", 1880, "Cub", 59, 94),
    /**
     * Next gen maps from here on out
     */
    MOWREY("mowrey.tmx", "localhost", 1881, "Mowrey", 29, 36),
    /**
     *
     */
    OUTSIDEMOWREY("outsideOfMowrey.tmx", "localhost", 1882, "OutsideMowrey", 2, 94),
    /**
     *
     */
    QUAD("quad.tmx", "localhost", 1883, "Quad", 52, 52),
    /**
     *
     */
    DHC1("dhc1.tmx", "localhost", 1884, "DHC1", 44, 49);


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
        return teleportPositionX;
    }

    public int getTeleportPositionY()
    {
        return teleportPositionY;
    }

}

package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.model.OptionsManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A row table gateway for the Servers table in the database. That table
 * contains the information about each area server.
 *
 * @author Merlin
 */
public class ServerRowDataGateway
{
    private String mapName;

    private String hostName;

    private int portNumber;

    private String mapTitle;

    private Connection connection;

    private String originalMapName;

    private int mapID;

    private int teleportPositionX;

    private int teleportPositionY;

    /**
     * Drop the table if it exists and re-create it empty
     *
     * @throws DatabaseException shouldn't
     */
    public static void createTable() throws DatabaseException
    {
        String sql = "Create TABLE Server ("
                + "serverID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                + "hostName varchar(80),"
                + "portNumber INT,"
                + "mapName varchar(80) UNIQUE, "
                + "mapTitle varchar(80) UNIQUE, "
                + "teleportPositionX INT DEFAULT 0, "
                + "teleportPositionY INT DEFAULT 0)";

        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt = connection.prepareStatement(
                    "DROP TABLE IF EXISTS VisitedMaps, Server");
            stmt.executeUpdate();
            stmt.close();

            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create the Server table", e);
        }
    }

    /**
     * Create a new mapping in the DB
     *
     * @param mapName           the name of the map file
     * @param hostName          the hostname of the server that manages that map
     * @param portNumber        the portnumber of the server that manages that map
     * @param mapTitle          the user friendly name of the map
     * @param teleportPositionX the x position of where players will teleport to
     * @param teleportPositionY the y position of where players will teleport to
     * @throws DatabaseException Either can't talk to DB or a mapping for that
     *                           map file already exists
     */
    public ServerRowDataGateway(String mapName, String hostName, int portNumber,
                                String mapTitle, int teleportPositionX,
                                int teleportPositionY) throws DatabaseException
    {
        insertNewRow(mapName, hostName, portNumber, mapTitle, teleportPositionX,
                teleportPositionY);
        this.mapName = mapName;
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.mapTitle = mapTitle;
        this.teleportPositionX = teleportPositionX;
        this.teleportPositionY = teleportPositionY;
    }

    private void insertNewRow(String mapName, String hostName, int portNumber,
                              String mapTitle, int teleportPositionX,
                              int teleportPositionY) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt = connection.prepareStatement(
                    "Insert INTO Server SET hostName = ?, portNumber = ?, mapName = ?, mapTitle = ?, teleportPositionX = ?, teleportPositionY = ?");
            stmt.setString(1, hostName);
            stmt.setInt(2, portNumber);
            stmt.setString(3, mapName);
            stmt.setString(4, mapTitle);
            stmt.setInt(5, teleportPositionX);
            stmt.setInt(6, teleportPositionY);
            stmt.executeUpdate();

        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Couldn't create a record in Server for map named " + mapName, e);
        }
    }

    /**
     * @param mapName the name of the map file we are interested in
     * @throws DatabaseException if there is no row with the given map file name
     */
    public ServerRowDataGateway(String mapName) throws DatabaseException
    {
        this.mapName = mapName;
        originalMapName = mapName;
        connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt =
                    connection.prepareStatement("SELECT * FROM Server WHERE mapName = ?");
            stmt.setString(1, mapName);
            ResultSet result = stmt.executeQuery();
            result.next();

            this.setHostName(result.getString("hostName"));
            this.setMapTitle(result.getString("mapTitle"));
            this.setPortNumber(result.getInt("portNumber"));
            this.setTeleportPositionX(result.getInt("teleportPositionX"));
            this.setTeleportPositionY(result.getInt("teleportPositionY"));
            mapID = result.getInt("serverID");
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Couldn't find a record in Server for a map named " +
                            mapName, e);
        }
    }

    /**
     * @param mapTitle the title of the map
     * @return the position and the map name
     */
    public static ServerRowDataGateway findPosAndMapNameFromMapTitle(String mapTitle)
            throws DatabaseException
    {


        Connection connection = DatabaseManager.getSingleton().getConnection();
        String SQL =
                "SELECT mapName, teleportPositionX, teleportPositionY FROM Server WHERE mapTitle = ?";
        PreparedStatement stmt;
        ServerRowDataGateway serverGateway = new ServerRowDataGateway();

        try
        {
            stmt = connection.prepareStatement(SQL);
            stmt.setString(1, mapTitle);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                serverGateway = new ServerRowDataGateway();
                serverGateway.setMapName(rs.getString("mapName"));
                serverGateway.setTeleportPositionX(rs.getInt("teleportPositionX"));
                serverGateway.setTeleportPositionY(rs.getInt("teleportPositionY"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return serverGateway;
    }

    /**
     * Get the name of the host managing this gateway's map file
     *
     * @return the host name
     */
    public String getHostName()
    {
        return hostName;
    }

    /**
     * Get the name of this gateway's map file
     *
     * @return the map file's name
     */
    public String getMapName()
    {
        return mapName;
    }

    /**
     * Get the port number of the host managing this gateway's map file
     *
     * @return the port number
     */
    public int getPortNumber()
    {
        return portNumber;
    }

    /**
     * Get the user friendly name of the map
     *
     * @return mapTitle the user friendly name of the map
     */
    public String getMapTitle()
    {
        return mapTitle;
    }


    /**
     * get the map's ID
     *
     * @return mapID the map's ID
     */
    public int getMapID()
    {
        return mapID;
    }

    public int getTeleportPositionX()
    {
        return teleportPositionX;
    }

    public int getTeleportPositionY()
    {
        return teleportPositionY;
    }

    public void setMapName(String mapName)
    {
        this.mapName = mapName;
    }

    public void setPortNumber(int portNumber)
    {
        this.portNumber = portNumber;
    }

    public void setHostName(String hostName)
    {
        this.hostName = hostName;
    }

    public void setTeleportPositionX(int xPosition)
    {
        this.teleportPositionX = xPosition;

    }

    public void setTeleportPositionY(int yPosition)
    {
        this.teleportPositionY = yPosition;
    }

    public void setMapTitle(String mapTitle)
    {
        this.mapTitle = mapTitle;
    }

    public void persist() throws DatabaseException
    {
        this.connection = DatabaseManager.getSingleton().getConnection();
        if (!originalMapName.equals(mapName))
        {
            PreparedStatement stmt;
            try
            {
                stmt = connection.prepareStatement(
                        "DELETE from Server WHERE mapName = ?");
                stmt.setString(1, originalMapName);
                stmt.executeUpdate();
            }
            catch (SQLException e)
            {
                throw new DatabaseException(
                        "Error trying to change mapName from " + originalMapName +
                                " to " + mapName,
                        e);
            }
            insertNewRow(mapName, hostName, portNumber, mapTitle, teleportPositionX,
                    teleportPositionY);
        }
        try
        {
            if (OptionsManager.getSingleton().getHostName().equals("localhost"))
            {
                PreparedStatement stmt = connection.prepareStatement(
                        "UPDATE Server SET portNumber = ? WHERE mapName = ?");
                stmt.setInt(1, portNumber);
                stmt.setString(2, mapName);
                stmt.executeUpdate();
            }
            else
            {
                PreparedStatement stmt = connection.prepareStatement(
                        "UPDATE Server SET portNumber = ?, hostName = ? WHERE mapName = ?");
                stmt.setString(2, hostName);
                stmt.setInt(1, portNumber);
                stmt.setString(3, mapName);
                stmt.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Couldn't find a record in Server for a map named " + mapName, e);
        }
    }

    /**
     * There are two finder constructors that have the same method signature and needed to return information
     * to combat this, we created two different methods that find the information needed based on the given
     * constructor, sets the variables and then it's passed to the right method.
     */
    private ServerRowDataGateway()
    {

    }
}

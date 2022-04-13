package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataDTO.PlayerMapLocationDTO;
import model.OptionsManager;

/**
 * A row table gateway for the Servers table in the database. That table
 * contains the information about each area server.
 *
 * @author Merlin
 *
 */
public class ServerRowDataGatewayRDS implements ServerRowDataGateway
{
	private static PlayerMapLocationDTO position;

	private static String mapName;

	private static String hostName;

	private static int portNumber;

	private static String mapTitle;

	private static Connection connection;

	private static String originalMapName;

	private static int mapID;

	private static int teleportPositionX;

	private static int teleportPositionY;

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
			PreparedStatement stmt =  connection.prepareStatement("DROP TABLE IF EXISTS VisitedMaps, Server");
			stmt.executeUpdate();
			stmt.close();

			stmt =  connection.prepareStatement(sql);
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
	 * @param mapName the name of the map file
	 * @param hostName the hostname of the server that manages that map
	 * @param portNumber the portnumber of the server that manages that map
	 * @param mapTitle the user friendly name of the map
	 * @param teleportPositionX the x position of where players will teleport to
	 * @param teleportPositionY the y position of where players will teleport to
	 * @throws DatabaseException Either can't talk to DB or a mapping for that
	 *             map file already exists
	 */
	public ServerRowDataGatewayRDS(String mapName, String hostName, int portNumber, String mapTitle, int teleportPositionX, int teleportPositionY) throws DatabaseException
	{
		insertNewRow(mapName, hostName, portNumber, mapTitle, teleportPositionX, teleportPositionY);
		this.mapName = mapName;
		this.hostName = hostName;
		this.portNumber = portNumber;
		this.mapTitle = mapTitle;
		this.teleportPositionX = teleportPositionX;
		this.teleportPositionY = teleportPositionY;
	}

	private void insertNewRow(String mapName, String hostName, int portNumber, String mapTitle, int teleportPositionX, int teleportPositionY) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement(
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
			throw new DatabaseException("Couldn't create a record in Server for map named " + mapName, e);
		}
	}

	/**
	 *
	 * @param mapName
	 *
	 * @param mapName the name of the map file we are interested in
	 * @return the server information based on the mapName
	 * @throws DatabaseException if there is no row with the given map file name
	 */
	public static ServerRowDataGateway findServerInfoFromMapName(String mapName) throws DatabaseException
	{
		ServerRowDataGatewayRDS serverGateway = new ServerRowDataGatewayRDS();
		ServerRowDataGatewayRDS.mapName = mapName;
		originalMapName = mapName;
		connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt =  connection.prepareStatement( "SELECT * FROM Server WHERE mapName = ?");
			stmt.setString(1, mapName);
			ResultSet result = stmt.executeQuery();
			result.next();
			serverGateway.setMapName(mapName);
			serverGateway.setHostName(result.getString("hostName"));
			serverGateway.setMapTitle(result.getString("mapTitle"));
			serverGateway.setPortNumber(result.getInt("portNumber"));
			serverGateway.setTeleportPositionX(result.getInt("teleportPositionX"));
			serverGateway.setTeleportPositionY(result.getInt("teleportPositionY"));
			mapID = result.getInt("serverID");

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find a record in Server for a map named " + ServerRowDataGatewayRDS.mapName, e);
		}
		return serverGateway;
	}

	/**
	 * @param mapTitle
	 * @return the position and the map name 
	 * @throws DatabaseException
	 */
	public static ServerRowDataGateway findPosAndMapNameFromMapTitle(String mapTitle) throws DatabaseException
	{


		Connection connection = DatabaseManager.getSingleton().getConnection();
		String SQL = "SELECT mapName, teleportPositionX, teleportPositionY FROM Server WHERE mapTitle = ?";
		PreparedStatement stmt;
		ServerRowDataGatewayRDS serverGateway = new ServerRowDataGatewayRDS();

		try
		{
			stmt =  connection.prepareStatement(SQL);
			stmt.setString(1, mapTitle);
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
			{
				serverGateway = new ServerRowDataGatewayRDS();
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
	@Override
	public String getMapTitle()
	{
		return mapTitle;
	}

	/**
	 * get the teleport position
	 * @return position the location to be teleported to
	 */
	@Override
	public PlayerMapLocationDTO getPosition()
	{
		return position;
	}

	/**
	 * get the map's ID
	 * @return mapID the map's ID
	 */
	public int getMapID()
	{
		return mapID;
	}

	/**
	 * @see datasource.ServerRowDataGateway#getTeleportPositionX()
	 */
	@Override
	public int getTeleportPositionX()
	{
		return teleportPositionX;
	}

	/**
	 * @see datasource.ServerRowDataGateway#getTeleportPositionY()
	 */
	@Override
	public int getTeleportPositionY()
	{
		return teleportPositionY;
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
		this.portNumber = portNumber;
	}

	/**
	 * @see datasource.ServerRowDataGateway#setHostName(java.lang.String)
	 */
	@Override
	public void setHostName(String hostName)
	{
		this.hostName = hostName;
	}

	/**
	 * @see datasource.ServerRowDataGateway#setTeleportPositionX(int)
	 */
	@Override
	public void setTeleportPositionX(int xPosition)
	{
		this.teleportPositionX = xPosition;

	}

	/**
	 * @see datasource.ServerRowDataGateway#setTeleportPositionY(int)
	 */
	@Override
	public void setTeleportPositionY(int yPosition)
	{
		this.teleportPositionY = yPosition;
	}

	/**
	 * @see datasource.ServerRowDataGateway#setMapTitle(java.lang.String)
	 */
	@Override
	public void setMapTitle(String mapTitle)
	{
		this.mapTitle = mapTitle;
	}

	/**
	 * @see datasource.ServerRowDataGateway#persist()
	 */
	@Override
	public void persist() throws DatabaseException
	{
		this.connection = DatabaseManager.getSingleton().getConnection();
		if (!originalMapName.equals(mapName))
		{
			PreparedStatement stmt;
			try
			{
				stmt =  connection.prepareStatement("DELETE from Server WHERE mapName = ?");
				stmt.setString(1, originalMapName);
				stmt.executeUpdate();
			}
			catch (SQLException e)
			{
				throw new DatabaseException("Error trying to change mapName from " + originalMapName + " to " + mapName,
						e);
			}
			insertNewRow(mapName, hostName, portNumber, mapTitle, teleportPositionX, teleportPositionY);
		}
		try
		{
			if (OptionsManager.getSingleton().getHostName().equals("localhost"))
			{
				PreparedStatement stmt =  connection.prepareStatement(
						"UPDATE Server SET portNumber = ? WHERE mapName = ?");
				stmt.setInt(1, portNumber);
				stmt.setString(2, mapName);
				stmt.executeUpdate();
			}
			else
			{
				PreparedStatement stmt =  connection.prepareStatement(
						"UPDATE Server SET portNumber = ?, hostName = ? WHERE mapName = ?");
				stmt.setString(2, hostName);
				stmt.setInt(1, portNumber);
				stmt.setString(3, mapName);
				stmt.executeUpdate();
			}
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find a record in Server for a map named " + mapName, e);
		}
	}

	/**
	 * @see datasource.ServerRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
	}

	/**
	 * There are two finder constructors that have the same method signature and needed to return information
	 * to combat this, we created two different methods that find the information needed based on the given
	 * constructor, sets the variables and then it's passed to the right method.
	 */
	private ServerRowDataGatewayRDS()
	{

	}
}

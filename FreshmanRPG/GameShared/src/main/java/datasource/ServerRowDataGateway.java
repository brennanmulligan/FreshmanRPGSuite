package datasource;

import dataDTO.PlayerMapLocationDTO;

/**
 * Defines the behavior required for interfacing with a data source that has
 * information about which servers are managing which map files
 *
 * @author Merlin
 *
 */
public interface ServerRowDataGateway
{

	/**
	 *
	 * @return the name of the host that is managing the map file
	 */
	String getHostName();

	/**
	 *
	 * @return the name of the map file we are interested in
	 */
	String getMapName();

	/**
	 *
	 * @return the port number associated with the map file
	 */
	int getPortNumber();

	/**
	 *
	 * @return the user friendly name of the map
	 */
	String getMapTitle();

	/**
	 *
	 * @return the x coord for the the teleport position 
	 */
	int getTeleportPositionX();

	/**
	 *
	 * @return the y coord for the the teleport position 
	 */
	int getTeleportPositionY();

	/**
	 * @param mapName the new map name for this server
	 */
	void setMapName(String mapName);

	/**
	 * @param portNumber the new port number for this server
	 */
	void setPortNumber(int portNumber);

	/**
	 * @param hostName the new host name for this server
	 */
	void setHostName(String hostName);

	/**
	 * @param mapTitle user friendly name of the map
	 */
	void setMapTitle(String mapTitle);
	/**
	 * store the information into the data source
	 *
	 * @throws DatabaseException if we are unable to persist the data
	 */

	/**
	 * @param xPosition the x position to be set
	 *
	 */
	void setTeleportPositionX(int xPosition);

	/**
	 * @param yPosition the y position to be set
	 */
	void setTeleportPositionY(int yPosition);

	void persist() throws DatabaseException;

	/**
	 * @return the position
	 */
	PlayerMapLocationDTO getPosition();

	/**
	 * Initialize the data source to a known state (for testing)
	 */
	void resetData();

	int getMapID();

}

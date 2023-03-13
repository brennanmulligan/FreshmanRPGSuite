package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ServerRowDataGateway;

/**
 * Keeps track of which server/port number each map is being managed by
 *
 * @author Merlin
 */
public final class MapToServerMapping
{
    private static ServerRowDataGateway dataGateway;

    public static ServerRowDataGateway getDataGateway()
    {
        return dataGateway;
    }

    /**
     * Get an object from the database
     *
     * @param mapName the name of the map for which we need the server info
     * @throws DatabaseException if there is a problem connecting to the db or
     *                           the mapName is not unique in the db
     */
    public MapToServerMapping(String mapName) throws DatabaseException
    {
        this.dataGateway = new ServerRowDataGateway(mapName);
    }

    //This is the other constructor for our mock test to
    //assign the mock gateway to the datagateway
    MapToServerMapping(ServerRowDataGateway gateway)
    {
        this.dataGateway = gateway;
    }

    /**
     * Set the map name for this mapping
     *
     * @param mapName the map name (the name of the .tmx file)
     */
    public void setMapFileTitle(String mapName)
    {
        dataGateway.setMapName(mapName);
    }

    /**
     * The port number the server should listen to
     *
     * @param portNumber between 0 and 9999 (not enforced)
     */
    public void setPortNumber(int portNumber)
    {
        dataGateway.setPortNumber(portNumber);
    }

    /**
     * The hostname portion of the URL the server for this map will be on
     *
     * @param hostName something like mmo.cs.ship.edu
     */
    public void setHostName(String hostName)
    {
        dataGateway.setHostName(hostName);
    }

    /**
     * Get the hostname portion of the URL the server for this map will be on
     * (something like mmo.cs.ship.edu)
     *
     * @return the hostname
     */
    public String getHostName()
    {
        if (OptionsManager.getSingleton().getHostName().equals("localhost"))
        {
            return "localhost";
        }
        return dataGateway.getHostName();
    }

    /**
     * Get the portnumber the server managing this map will be listening to
     *
     * @return a port number
     */
    public int getPortNumber()
    {
        return dataGateway.getPortNumber();
    }

    /**
     * Get the name of the map we are interested in
     *
     * @return the name of the tmx file
     */
    public String getMapName()
    {
        return dataGateway.getMapName();
    }

    /**
     * Persist this object out to the db
     *
     * @throws DatabaseException if updating the db failed
     */
    public void persist() throws DatabaseException
    {
        dataGateway.persist();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return this.dataGateway.getHostName() + " on " + dataGateway.getHostName() + ":" +
                dataGateway.getPortNumber();
    }

    public boolean isQuiet()
    {
        return dataGateway.isQuiet();
    }
}

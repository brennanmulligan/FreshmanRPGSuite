package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.datatypes.ServersForTest;
import edu.ship.engr.shipsim.model.OptionsManager;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the behaviors associated with Server data sources
 *
 * @author Merlin
 */
@GameTest("GameShared")
public class ServerRowDataGatewayTest
{
    protected ServerRowDataGateway gateway;

    /**
     * Create a gateway that inserts a new row into the table
     *
     * @param mapName  the name of the map
     * @param hostName the host serving the map
     * @param port     the port associated with the map
     * @param mapTitle the user friendly name of the map
     * @return the gateway for the new row
     * @throws DatabaseException if the data source failed to create the row
     */
    public ServerRowDataGateway createGateway(String mapName, String hostName, int port, String mapTitle, int teleportPositionX, int teleportPositionY) throws DatabaseException
    {
        return new ServerRowDataGateway(mapName, hostName, port, mapTitle, teleportPositionX, teleportPositionY);
    }

    /**
     * same as other but with isQuiet included
     * @param mapName
     * @param hostName
     * @param port
     * @param mapTitle
     * @param teleportPositionX
     * @param teleportPositionY
     * @param isQuiet
     * @return
     * @throws DatabaseException
     */
    public ServerRowDataGateway createGateway(String mapName, String hostName, int port, String mapTitle, int teleportPositionX, int teleportPositionY, boolean isQuiet) throws DatabaseException
    {
        return new ServerRowDataGateway(mapName, hostName, port, mapTitle, teleportPositionX, teleportPositionY, isQuiet);
    }

    /**
     * Try to create a row for a map file that is already in the database
     */
    @Test
    public void createExisting()
    {
        assertThrows(DatabaseException.class, () ->
        {
            gateway = createGateway(ServersForTest.FIRST_SERVER.getMapName(), "noHostName", 1000,
                    ServersForTest.FIRST_SERVER.getMapTitle(), ServersForTest.FIRST_SERVER.getTeleportPositionX(),
                    ServersForTest.FIRST_SERVER.getTeleportPositionY());
        });
    }

    /**
     * Create a new row in the database
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void createNotExisting() throws DatabaseException
    {
        // Use one behavior to create it
        gateway = createGateway("stupid.tmx", "noHostName", 1000, "funny", 0, 0);
        // Make sure another behavior can find it
        ServerRowDataGateway found = findGateway("stupid.tmx");
        assertEquals("noHostName", found.getHostName());
        assertEquals(1000, found.getPortNumber());
        assertEquals("funny", found.getMapTitle());
    }

    /**
     * get a gateway for an existing row of the table
     *
     * @param mapName the name of the map in the row we are testing
     * @return the gateway
     * @throws DatabaseException if the data source is unable to find the row
     */
    public ServerRowDataGateway findGateway(String mapName) throws DatabaseException
    {
        return new ServerRowDataGateway(mapName);
    }

    /**
     * Get a gateway for a row that we know is in the database
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void findExisting() throws DatabaseException
    {
        ServersForTest data = ServersForTest.FIRST_SERVER;
        gateway = findGateway(data.getMapName());
        assertEquals(data.getHostName(), gateway.getHostName());
        assertEquals(data.getPortNumber(), gateway.getPortNumber());
    }

    /**
     * If we ask for a gateway for a row that isn't in the database, we should get
     * an exception
     */
    @Test
    public void findNotExisting()
    {
        assertThrows(DatabaseException.class, () ->
        {
            gateway = findGateway("noFile");
        });
    }

    /**
     * @throws DatabaseException shouldn't
     */
    @Test
    public void editMapName() throws DatabaseException
    {
        gateway = findGateway(ServersForTest.FIRST_SERVER.getMapName());
        gateway.setMapName("Fred.tmx");
        gateway.persist();
        ServerRowDataGateway after = findGateway("Fred.tmx");
        assertEquals(ServersForTest.FIRST_SERVER.getPortNumber(), after.getPortNumber());
        assertEquals(ServersForTest.FIRST_SERVER.getHostName(), after.getHostName());
    }

    /**
     * We should be able to change the host name
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void editHostName() throws DatabaseException
    {
        OptionsManager.getSingleton().setHostName(ServersForTest.FIRST_SERVER.getHostName());
        gateway = findGateway(ServersForTest.FIRST_SERVER.getMapName());
        gateway.setHostName("h");
        gateway.persist();
        ServerRowDataGateway after = findGateway(ServersForTest.FIRST_SERVER.getMapName());
        assertEquals(ServersForTest.FIRST_SERVER.getPortNumber(), after.getPortNumber());
        assertEquals("h", after.getHostName());
    }

    /**
     * We should be able to change the portNumber
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void editPortNumber() throws DatabaseException
    {
        gateway = findGateway(ServersForTest.FIRST_SERVER.getMapName());
        gateway.setPortNumber(42);
        gateway.persist();
        ServerRowDataGateway after = findGateway(ServersForTest.FIRST_SERVER.getMapName());
        assertEquals(42, after.getPortNumber());
        assertEquals(ServersForTest.FIRST_SERVER.getHostName(), after.getHostName());
    }

    /**
     * checks that you can make a quiet server and get that status
     * @throws DatabaseException
     */
    @Test
    public void testQuietConstructor() throws DatabaseException
    {
        gateway = createGateway("testMapQuiet", "host", 100, "mapTitle", 10, 10, false);

        assertFalse(gateway.isQuiet());

        ServerRowDataGateway gateway2 = createGateway("testMapQuiet2", "host2", 100, "mapTitle2", 10, 10, true);

        assertTrue(gateway2.isQuiet());
    }


}

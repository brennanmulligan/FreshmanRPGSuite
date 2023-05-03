package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * An abstract class that tests the map table gateway.
 *
 * @author Scott Bucher
 */
@GameTest("GameServer")
public class MapTableDataGatewayTest
{

    /**
     * @return the gateway we should test
     */
    public MapTableDataGateway getGatewaySingleton()
    {
        return MapTableDataGateway.getSingleton();
    }


    /**
     * Ensures that the gateways are able to retrieve all quests.
     *
     * @throws DatabaseException - problem reading Quests table
     */
    @Test
    public void testGetListOfMapNames() throws DatabaseException
    {
        MapTableDataGateway gateway = getGatewaySingleton();
        assertEquals(gateway.getMapNames().size(), 17);
        assertEquals(gateway.getMapNames().get(0), "map1.tmx");
    }
}

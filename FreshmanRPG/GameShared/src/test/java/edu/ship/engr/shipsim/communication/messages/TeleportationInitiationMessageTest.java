package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.datatypes.ServersForTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests a login message
 *
 * @author merlin
 */
public class TeleportationInitiationMessageTest
{
    /**
     * Make sure its toString is correct
     */
    @Test
    public void testInitialization()
    {
        TeleportationInitiationMessage msg = new TeleportationInitiationMessage(1,
                ServersForTest.FIRST_SERVER.getMapName(), new Position(4, 3));
        assertEquals("TeleportationInitiationMessage: playerID = 1 mapName = "
                + ServersForTest.FIRST_SERVER.getMapName() + " position = " + msg.getPosition(), msg.toString());
        assertEquals(ServersForTest.FIRST_SERVER.getMapName(), msg.getMapName());
        assertEquals(1, msg.getPlayerId());
        assertEquals(new Position(4, 3), msg.getPosition());
    }

}

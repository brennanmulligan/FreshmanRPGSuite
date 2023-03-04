package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.datatypes.ServersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests a login message
 *
 * @author merlin
 */
@GameTest("GameShared")
public class TeleportationInitiationMessageTest
{
    /**
     * Make sure its toString is correct
     */
    @Test
    public void testInitialization()
    {
        TeleportationInitiationMessage msg = new TeleportationInitiationMessage(1, false,
                ServersForTest.FIRST_SERVER.getMapName(), new Position(4, 3));
        assertEquals("TeleportationInitiationMessage: playerID = 1 mapName = "
                + ServersForTest.FIRST_SERVER.getMapName() + " position = " + msg.getPosition(), msg.toString());
        assertEquals(ServersForTest.FIRST_SERVER.getMapName(), msg.getMapName());
        assertEquals(1, msg.getRelevantPlayerID());
        assertEquals(new Position(4, 3), msg.getPosition());
    }

}

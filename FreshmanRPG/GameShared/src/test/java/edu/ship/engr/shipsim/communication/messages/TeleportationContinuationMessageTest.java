package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.ServersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests a login message
 *
 * @author merlin
 */
@GameTest("GameShared")
public class TeleportationContinuationMessageTest
{
    /**
     * Make sure its toString is correct
     */
    @Test
    public void testToStringAndGetters()
    {
        TeleportationContinuationMessage msg = new TeleportationContinuationMessage(
                ServersForTest.FIRST_SERVER.getMapName(), ServersForTest.FIRST_SERVER.getHostName(),
                ServersForTest.FIRST_SERVER.getPortNumber(), 2, 4, false);
        assertTrue(msg.toString()
                .startsWith("TeleportationContinuationMessage: mapName = " + ServersForTest.FIRST_SERVER.getMapName()
                        + " and hostName = " + ServersForTest.FIRST_SERVER.getHostName() + " and portNumber = "
                        + ServersForTest.FIRST_SERVER.getPortNumber()));
        assertEquals(ServersForTest.FIRST_SERVER.getMapName(), msg.getMapName());
        assertEquals(ServersForTest.FIRST_SERVER.getHostName(), msg.getHostName());
        assertEquals(ServersForTest.FIRST_SERVER.getPortNumber(), msg.getPortNumber());
        assertEquals(2, msg.getRelevantPlayerID());
        assertEquals(4, msg.getPin());
    }

}

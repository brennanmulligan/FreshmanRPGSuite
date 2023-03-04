package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests a file message which is designed to carry a tmx file
 *
 * @author merlin
 */
@GameTest("GameShared")
public class AreaCollisionMessageTest
{
    /**
     * Make sure its toString is correct
     *
     * @throws Exception shouldn't
     */
    @Test
    public void testConstructor() throws Exception
    {
        AreaCollisionMessage msg = new AreaCollisionMessage(1, false, "test");
        assertEquals(1, msg.getRelevantPlayerID());
        assertEquals("test", msg.getAreaName());
        assertEquals(false, msg.isQuietMessage());
    }

}

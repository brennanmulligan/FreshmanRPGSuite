package edu.ship.engr.shipsim.communication.messages;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests a file message which is designed to carry a tmx file
 *
 * @author merlin
 */
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
        AreaCollisionMessage msg = new AreaCollisionMessage(1, "test");
        assertEquals(1, msg.getPlayerID());
        assertEquals("test", msg.getAreaName());
    }

}

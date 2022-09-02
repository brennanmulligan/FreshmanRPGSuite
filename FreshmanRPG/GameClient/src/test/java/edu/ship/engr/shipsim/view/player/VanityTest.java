package edu.ship.engr.shipsim.view.player;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VanityTest
{

    /**
     * Test creating a vanity (headless without texture)
     */
    @Test
    public void testInitialize()
    {
        Vanity vanity = new Vanity();

        assertNotNull(vanity);
        assertEquals(vanity.getX(), 0, .05);
        assertEquals(vanity.getY(), 0, .05);
        assertEquals(vanity.getFacing(), Direction.South);
    }

    /**
     * Test moving and tweening a vanity
     */
    @Test
    public void testMoving()
    {
        Vanity vanity = new Vanity();

        assertEquals(vanity.getX(), 0, .05);
        assertEquals(vanity.getY(), 0, .05);
        assertEquals(vanity.getFacing(), Direction.South);

        // set position by pixel
        vanity.update(32, 32);
        assertEquals(vanity.getX(), 0, .05);
        assertEquals(vanity.getY(), 0, .05);
        assertEquals(vanity.getFacing(), Direction.East);

        vanity.act(vanity.getMoveDuration() / 2f);
        assertEquals(0, vanity.getX(), .05);
        assertEquals(0, vanity.getY(), .05);
        assertEquals(Direction.East, vanity.getFacing());
    }
}

package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests the basic VanityDTO class and its functionality
 *
 * @author Jake, Aaron, Kytal
 */
@GameTest("GameShared")
public class VanityDTOTest
{

    @Test
    public void testVanityDTOInitialization()
    {
        VanityDTO v = new VanityDTO(1, "hello", "world", "!", VanityType.HAT, 0);
        assertEquals(1, v.getID());
        assertEquals("hello", v.getName());
        assertEquals("world", v.getDescription());
        assertEquals("!", v.getTextureName());
        assertEquals(VanityType.HAT, v.getVanityType());
        assertEquals(0, v.getPrice());
    }

    @Test
    public void testVanityDTOInitializationWithPrice()
    {
        VanityDTO v = new VanityDTO(1, "hello", "world", "!", VanityType.HAT, 100);
        assertEquals(1, v.getID());
        assertEquals("hello", v.getName());
        assertEquals("world", v.getDescription());
        assertEquals("!", v.getTextureName());
        assertEquals(VanityType.HAT, v.getVanityType());
        assertEquals(100, v.getPrice());
    }
}

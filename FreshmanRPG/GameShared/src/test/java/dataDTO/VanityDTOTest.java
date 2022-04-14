package dataDTO;

import datatypes.VanityType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the basic VanityDTO class and its functionality
 *
 * @author Jake, Aaron, Kytal
 */
public class VanityDTOTest
{

    @Test
    public void testVanityDTOInitialization()
    {
        VanityDTO v = new VanityDTO(1, "hello", "world", "!", VanityType.HAT);
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

package communication.messages;

import dataDTO.VanityDTO;
import datatypes.VanityType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests the VanityShopInventoryResponseMessage
 *
 * @author Jake, Aaron
 */
public class VanityShopInventoryResponseMessageTest
{
    @Test
    public void testInitialization()
    {
        ArrayList<VanityDTO> items = new ArrayList<>();
        items.add(new VanityDTO(1, "Hello", "world", "!", VanityType.HAT, 200));
        assertEquals(items, new VanityShopInventoryResponseMessage(items).getVanityShopInventory());
        assertEquals(1, new VanityShopInventoryResponseMessage(items).getVanityShopInventory().get(0).getID());
    }
}

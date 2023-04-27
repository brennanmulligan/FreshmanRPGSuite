package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the VanityShopInventoryResponseMessage
 *
 * @author Jake, Aaron
 */
@GameTest("GameShared")
public class VanityShopInventoryResponseMessageTest
{
    @Test
    public void testInitialization()
    {
        ArrayList<VanityDTO> items = new ArrayList<>();
        items.add(new VanityDTO(1, "Hello", "world", "!", VanityType.HAT, 200));
        assertEquals(items, new VanityShopInventoryResponseMessage(items, false).getVanityShopInventory());
        assertEquals(1, new VanityShopInventoryResponseMessage(items, false).getVanityShopInventory().get(0).getID());
    }
}

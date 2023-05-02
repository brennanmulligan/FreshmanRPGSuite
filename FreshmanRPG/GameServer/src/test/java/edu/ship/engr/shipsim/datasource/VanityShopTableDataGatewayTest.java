package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.VanityItemsForTest;
import edu.ship.engr.shipsim.datatypes.VanityShopItemsForTest;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the VanityShop
 *
 * @author Aaron, Jake
 */
@GameTest("GameServer")
public class VanityShopTableDataGatewayTest
{
    protected VanityShopTableDataGateway gateway;

    @BeforeEach
    public void setup() throws DatabaseException
    {
        gateway = findGateway();
    }

    @Test
    public void testCanAddItem() throws DatabaseException
    {
        assertEquals(VanityShopItemsForTest.values().length,
                gateway.getVanityShopInventory().size());
        gateway.addItem(VanityItemsForTest.LIGHT_BLUE_EYES.getId(), 5);
        assertEquals(VanityShopItemsForTest.values().length + 1,
                gateway.getVanityShopInventory().size());
    }

    @Test
    public void testCantAddDup()
    {
        assertThrows(DatabaseException.class, () ->
        {
            gateway.addItem(VanityShopItemsForTest.values()[0].getVanityID(),
                    VanityShopItemsForTest.values()[0].getPrice());
        });
    }

    @Test
    public void testCantAddInvalidPrice()
    {
        assertThrows(DatabaseException.class, () ->
        {
            gateway.addItem(VanityItemsForTest.LIGHT_BLUE_EYES.getId(), -1);
        });
    }

    @Test
    public void testCantAddInvalidVanity()
    {
        assertThrows(DatabaseException.class, () ->
        {
            gateway.addItem(-100, 5);
        });
    }

    @Test
    public void testCantUpdateInvalidItem()
    {
        assertThrows(DatabaseException.class, () ->
        {
            gateway.addItem(-10, 10);
        });
    }

    @Test
    public void testCantUpdateInvalidPrice()
    {
        assertThrows(DatabaseException.class, () ->
        {
            gateway.addItem(VanityShopItemsForTest.values()[0].getVanityID(), -10);
        });
    }

    /**
     * Tests the conversion of a row to a dto, and make sure we get all items
     *
     * @throws DatabaseException shouldnt
     */
    @Test
    public void testSizeAndInfo() throws DatabaseException
    {
        int index = VanityShopItemsForTest.BEANIE_HAT.ordinal();
        assertEquals(VanityShopItemsForTest.values().length,
                gateway.getVanityShopInventory().size());
        VanityDTO item = gateway.getVanityShopInventory().get(index);
        assertEquals(VanityItemsForTest.BEANIE_HAT.getVanityType(),
                item.getVanityType());
        assertEquals(VanityItemsForTest.BEANIE_HAT.getId(), item.getID());
        assertEquals(VanityItemsForTest.BEANIE_HAT.getDescription(), item.getDescription());
        assertEquals(VanityItemsForTest.BEANIE_HAT.getName(), item.getName());
        assertEquals(VanityItemsForTest.BEANIE_HAT.getTextureName(), item.getTextureName());
        assertEquals(VanityShopItemsForTest.BEANIE_HAT.getPrice(), item.getPrice());
    }

    @Test
    public void testUpdateItem() throws DatabaseException
    {
        assertTrue(VanityShopItemsForTest.values()[0].getPrice() != 100);
        gateway.updateItem(VanityShopItemsForTest.values()[0].getVanityID(), 100);
        ArrayList<VanityDTO> fromGateway = gateway.getVanityShopInventory();
        for (VanityDTO dto : fromGateway)
        {
            if (dto.getID() == VanityShopItemsForTest.values()[0].getVanityID())
            {
                assertEquals(100, dto.getPrice());
            }
        }
    }

    VanityShopTableDataGateway findGateway() throws DatabaseException
    {
        return VanityShopTableDataGateway.getSingleton();
    }


}

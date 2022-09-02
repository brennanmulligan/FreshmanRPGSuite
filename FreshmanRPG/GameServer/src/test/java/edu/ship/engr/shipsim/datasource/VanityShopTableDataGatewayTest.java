package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.VanityItemsForTest;
import edu.ship.engr.shipsim.datatypes.VanityShopItemsForTest;
import edu.ship.engr.shipsim.datatypes.VanityType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the VanityShop
 *
 * @author Aaron, Jake
 */
public class VanityShopTableDataGatewayTest extends ServerSideTest
{
    protected VanityShopTableDataGateway gateway;

    @Before
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

    @Test(expected = DatabaseException.class)
    public void testCantAddDup() throws DatabaseException
    {
        gateway.addItem(VanityShopItemsForTest.values()[0].getVanityID(),
                VanityShopItemsForTest.values()[0].getPrice());
    }

    @Test(expected = DatabaseException.class)
    public void testCantAddInvalidPrice() throws DatabaseException
    {
        gateway.addItem(VanityItemsForTest.LIGHT_BLUE_EYES.getId(), -1);
    }

    @Test(expected = DatabaseException.class)
    public void testCantAddInvalidVanity() throws DatabaseException
    {
        gateway.addItem(-100, 5);
    }

    @Test(expected = DatabaseException.class)
    public void testCantUpdateInvalidItem() throws DatabaseException
    {
        gateway.addItem(-10, 10);
    }

    @Test(expected = DatabaseException.class)
    public void testCantUpdateInvalidPrice() throws DatabaseException
    {
        gateway.addItem(VanityShopItemsForTest.values()[0].getVanityID(), -10);
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
        assertEquals(VanityType.fromInt(VanityItemsForTest.BEANIE_HAT.getVanityType()),
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

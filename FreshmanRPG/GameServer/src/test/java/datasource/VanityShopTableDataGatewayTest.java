package datasource;

import dataDTO.VanityDTO;
import datatypes.VanityForTest;
import datatypes.VanityType;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the VanityShop
 * @author Aaron, Jake
 */
public abstract class VanityShopTableDataGatewayTest extends DatabaseTest
{
    protected VanityShopTableDataGateway gateway;
    abstract VanityShopTableDataGateway findGateway() throws DatabaseException;

    /**
     * Test to make sure it can get rows from the database with the correct info
     * @throws DatabaseException shouldnt
     */
    @Test
    public void getItems() throws DatabaseException
    {
        gateway = findGateway();
        int index = VanityForTest.BeanieHat.ordinal();
        assertEquals(VanityForTest.BeanieHat.getPrice(), gateway.getRows().get(index).getPrice());
        assertEquals(VanityForTest.BeanieHat.getVanityType(), gateway.getRows().get(index).getVanityType());
        assertEquals(VanityForTest.BeanieHat.getId(), gateway.getRows().get(index).getId());
        assertEquals(VanityForTest.BeanieHat.getDescription(), gateway.getRows().get(index).getDescription());
        assertEquals(VanityForTest.BeanieHat.getName(), gateway.getRows().get(index).getName());
        assertEquals(VanityForTest.BeanieHat.getTextureName(), gateway.getRows().get(index).getTextureName());
    }

    /**
     * Tests the conversion of a row to a dto
     * @throws DatabaseException shouldnt
     */
    @Test
    public void testConversion() throws DatabaseException
    {
        gateway = findGateway();
        int index = VanityForTest.BeanieHat.ordinal();
        assertEquals(VanityForTest.values().length, gateway.getVanityShopInventory().size());
        assertEquals(VanityType.fromInt(VanityForTest.BeanieHat.getVanityType()), gateway.getVanityShopInventory().get(index).getVanityType());
        assertEquals(VanityForTest.BeanieHat.getId(), gateway.getVanityShopInventory().get(index).getID());
        assertEquals(VanityForTest.BeanieHat.getDescription(), gateway.getVanityShopInventory().get(index).getDescription());
        assertEquals(VanityForTest.BeanieHat.getName(), gateway.getVanityShopInventory().get(index).getName());
        assertEquals(VanityForTest.BeanieHat.getTextureName(), gateway.getVanityShopInventory().get(index).getTextureName());
    }

}

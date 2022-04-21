package datasource;

import dataDTO.VanityDTO;
import datatypes.VanityForTest;
import datatypes.VanityType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests for the VanityItemsTableDataGateway
 */
public abstract class VanityItemsTableDataGatewayTest extends DatabaseTest
{
    protected VanityItemsTableDataGatewayInterface gateway;

    abstract VanityItemsTableDataGatewayInterface findGateway() throws DatabaseException;

    /**
     * Get the right gateway and set up the gateway
     * @throws DatabaseException shouldnt
     */
    @Before
    public void setup() throws DatabaseException
    {
        gateway = findGateway();
        gateway.resetData();
    }

    /**
     * Tests to make sure singleton pattern works
     * @throws DatabaseException shouldn't
     */
    @Test
    public void isASingleton() throws DatabaseException
    {
        VanityItemsTableDataGatewayInterface a = findGateway();
        VanityItemsTableDataGatewayInterface b = findGateway();
        assertEquals(a, b);
        assertNotNull(a);
    }

    /**
     * Tests to make sure we can get all vanity items
     * @throws DatabaseException shouldnt
     */
    @Test
    public void getAllVanityTest() throws DatabaseException
    {
        ArrayList<VanityDTO> items = new ArrayList<>();
        for (VanityForTest v : VanityForTest.values())
        {
            VanityDTO d = new VanityDTO(v.getId(), v.getName(), v.getDescription(), v.getTextureName(), VanityType.fromInt(v.getVanityType()));
            items.add(d);
        }

        ArrayList<VanityDTO> itemsFromGateway = gateway.getAllVanityItems();
        assertEquals(items.size(), itemsFromGateway.size());

        assertTrue(items.containsAll(itemsFromGateway));
        assertTrue(itemsFromGateway.containsAll(items));
    }

    /**
     * Test to make sure we can get an item from its ID
     * @throws DatabaseException shouldnt
     */
    @Test
    public void getItemByID() throws DatabaseException
    {
        VanityForTest item = VanityForTest.MerlinHat;
        VanityDTO dto = new VanityDTO(item.getId(), item.getName(), item.getDescription(), item.getTextureName(), VanityType.fromInt(item.getVanityType()));
        assertEquals(dto, gateway.getVanityItemByID(item.getId()));
    }

    /**
     * Test to make sure we can add a vanity item
     * @throws DatabaseException shouldnt
     */
    @Test
    public void addVanityItem() throws DatabaseException
    {
        ArrayList<VanityDTO> old = gateway.getAllVanityItems();

        VanityDTO newItem = new VanityDTO(old.size() + 1, "new", "new item", "new", VanityType.HAT);
        assertFalse(old.contains(newItem));
        gateway.addVanityItem(newItem.getName(), newItem.getDescription(), newItem.getTextureName(), newItem.getVanityType());
        ArrayList<VanityDTO> newGate = gateway.getAllVanityItems();
        VanityDTO lastItem = newGate.get(newGate.size()-1);
        assertEquals(newItem.getName(), lastItem.getName());
        assertEquals(newItem.getDescription(), lastItem.getDescription());
        assertEquals(newItem.getTextureName(), lastItem.getTextureName());
        assertEquals(newItem.getVanityType(), lastItem.getVanityType());
    }

}

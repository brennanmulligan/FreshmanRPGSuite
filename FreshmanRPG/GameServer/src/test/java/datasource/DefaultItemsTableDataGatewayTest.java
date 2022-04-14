package datasource;

import dataDTO.VanityDTO;
import datatypes.DefaultItemsForTest;
import datatypes.VanityForTest;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public abstract class DefaultItemsTableDataGatewayTest extends DatabaseTest
{
    protected DefaultItemsTableDataGateway gateway;
    abstract DefaultItemsTableDataGateway findGateway() throws DatabaseException;

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
        DefaultItemsTableDataGateway a = findGateway();
        DefaultItemsTableDataGateway b = findGateway();
        assertEquals(a, b);
        assertNotNull(a);
    }

    @Test
    public void testGetAllItems() throws DatabaseException
    {
        ArrayList<VanityDTO> itemsFromGateway = gateway.getDefaultItems();
        ArrayList<Integer> itemsFromGatewayIDs;
        ArrayList<Integer> testItems = new ArrayList<>();

        for (DefaultItemsForTest item : DefaultItemsForTest.values())
        {
            testItems.add(item.getDefaultID());
        }

        itemsFromGatewayIDs = getIDsFromVanityDTO(itemsFromGateway);
        assertTrue(testItems.containsAll(itemsFromGatewayIDs) && itemsFromGatewayIDs.containsAll(testItems));
    }

    /**
     * Tests to make sure we can add an item
     * REQUIRES: Merlin hat to not be in default items
     */
    @Test
    public void testAddItem() throws DatabaseException
    {
        ArrayList<Integer> itemsFromGateway = getIDsFromVanityDTO(gateway.getDefaultItems());
        assertFalse(itemsFromGateway.contains(VanityForTest.MerlinHat.getId()));

        gateway.addDefaultItem(VanityForTest.MerlinHat.getId());

        itemsFromGateway = getIDsFromVanityDTO(gateway.getDefaultItems());

        assertTrue(itemsFromGateway.contains(VanityForTest.MerlinHat.getId()));
    }

    @Test (expected = DatabaseException.class)
    public void cannotAddDuplicateItem() throws DatabaseException
    {
        ArrayList<Integer> itemsFromGateway = getIDsFromVanityDTO(gateway.getDefaultItems());
        gateway.addDefaultItem(itemsFromGateway.get(0));
    }

    @Test (expected = DatabaseException.class)
    public void cannotAddInvalidVanityItem() throws DatabaseException
    {
        gateway.addDefaultItem(-1);
    }

    @Test
    public void testRemoveItem() throws DatabaseException
    {
        gateway.addDefaultItem(VanityForTest.MerlinHat.getId());
        gateway.removeDefaultItem(VanityForTest.MerlinHat.getId());

        ArrayList<Integer> itemsFromGateway = getIDsFromVanityDTO(gateway.getDefaultItems());

        assertFalse(itemsFromGateway.contains(VanityForTest.MerlinHat.getId()));
    }

    @Test (expected = DatabaseException.class)
    public void cannotRemoveInvalidItem() throws DatabaseException
    {
        gateway.removeDefaultItem(-1);
    }

    private ArrayList<Integer> getIDsFromVanityDTO(ArrayList<VanityDTO> itemsFromGateway)
    {
        ArrayList<Integer> itemsFromGatewayIDs = new ArrayList<>();
        for (VanityDTO dto : itemsFromGateway)
        {
            itemsFromGatewayIDs.add(dto.getID());
        }
        return itemsFromGatewayIDs;
    }
}

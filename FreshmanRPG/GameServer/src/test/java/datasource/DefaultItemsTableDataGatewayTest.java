package datasource;

import dataDTO.VanityDTO;
import datatypes.DefaultItemsForTest;
import datatypes.VanityForTest;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests for the DefaultItemsTableDataGateway
 */
public class DefaultItemsTableDataGatewayTest extends ServerSideTest
{
    protected DefaultItemsTableDataGateway gateway;

    /**
     * Tests to make sure we cannot add a duplicate default item
     *
     * @throws DatabaseException when duplicate item is added
     */
    @Test(expected = DatabaseException.class)
    public void cannotAddDuplicateItem() throws DatabaseException
    {
        ArrayList<Integer> itemsFromGateway =
                getIDsFromVanityDTO(gateway.getDefaultItems());
        gateway.addDefaultItem(itemsFromGateway.get(0));
    }

    /**
     * Tests to make sure we cannot add an invalid default item
     *
     * @throws DatabaseException when invalid item is added
     */
    @Test(expected = DatabaseException.class)
    public void cannotAddInvalidVanityItem() throws DatabaseException
    {
        gateway.addDefaultItem(-1);
    }

    /**
     * Tests to make sure we cannot remove an invalid default item
     *
     * @throws DatabaseException when removing invalid item
     */
    @Test(expected = DatabaseException.class)
    public void cannotRemoveInvalidItem() throws DatabaseException
    {
        gateway.removeDefaultItem(-1);
    }

    @Test(expected = DatabaseException.class)
    public void cannotSetInvalidDefault() throws DatabaseException
    {
        gateway.setDefaultWearing(-1);
    }

    @Test
    public void changeDefault() throws DatabaseException
    {
        ArrayList<Integer> wearing = getIDsFromVanityDTO(gateway.getDefaultWearing());
        assertFalse(wearing.contains(DefaultItemsForTest.LightBlueEyes.getDefaultID()));
        gateway.setDefaultWearing(DefaultItemsForTest.LightBlueEyes.getDefaultID());
        wearing = getIDsFromVanityDTO(gateway.getDefaultWearing());
        assertTrue(wearing.contains(DefaultItemsForTest.LightBlueEyes.getDefaultID()));
        assertFalse(wearing.contains(DefaultItemsForTest.DefaultEyes.getDefaultID()));
    }

    /**
     * Tests to make sure singleton pattern works
     *
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

    /**
     * Get the right gateway and set up the gateway
     *
     * @throws DatabaseException shouldnt
     */
    @Before
    public void setup() throws DatabaseException
    {
        gateway = findGateway();
        gateway.resetTableGateway();
    }

    /**
     * Tests to make sure we can add an item
     * REQUIRES: Merlin hat to not be in default items
     */
    @Test
    public void testAddItem() throws DatabaseException
    {
        ArrayList<Integer> itemsFromGateway =
                getIDsFromVanityDTO(gateway.getDefaultItems());
        assertFalse(itemsFromGateway.contains(VanityForTest.MerlinHat.getId()));

        gateway.addDefaultItem(VanityForTest.MerlinHat.getId());

        itemsFromGateway = getIDsFromVanityDTO(gateway.getDefaultItems());

        assertTrue(itemsFromGateway.contains(VanityForTest.MerlinHat.getId()));
    }

    /**
     * Tests to make sure we can get all the default items
     *
     * @throws DatabaseException shouldnt
     */
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
        assertTrue(testItems.containsAll(itemsFromGatewayIDs) &&
                itemsFromGatewayIDs.containsAll(testItems));
    }

    @Test
    public void testGetDefaultWearing() throws DatabaseException
    {
        ArrayList<Integer> shouldBe = new ArrayList<>();
        for (DefaultItemsForTest item : DefaultItemsForTest.values())
        {
            if (item.getDefaultWearing() == 1)
            {
                shouldBe.add(item.getDefaultID());
            }
        }

        ArrayList<Integer> actual = getIDsFromVanityDTO(gateway.getDefaultWearing());

        assertTrue(shouldBe.containsAll(actual) && actual.containsAll(shouldBe));
    }

    /**
     * Tests to make sure we can remove a default item
     *
     * @throws DatabaseException shouldnt
     */
    @Test
    public void testRemoveItem() throws DatabaseException
    {
        gateway.addDefaultItem(VanityForTest.MerlinHat.getId());
        gateway.removeDefaultItem(VanityForTest.MerlinHat.getId());

        ArrayList<Integer> itemsFromGateway =
                getIDsFromVanityDTO(gateway.getDefaultItems());

        assertFalse(itemsFromGateway.contains(VanityForTest.MerlinHat.getId()));
    }

    /**
     * @param itemsFromGateway the default items from the gateway
     * @return a list of the awards ids
     */
    private ArrayList<Integer> getIDsFromVanityDTO(ArrayList<VanityDTO> itemsFromGateway)
    {
        ArrayList<Integer> itemsFromGatewayIDs = new ArrayList<>();
        for (VanityDTO dto : itemsFromGateway)
        {
            itemsFromGatewayIDs.add(dto.getID());
        }
        return itemsFromGatewayIDs;
    }

    DefaultItemsTableDataGateway findGateway() throws DatabaseException
    {
        return DefaultItemsTableDataGateway.getSingleton();
    }
}

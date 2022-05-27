package datasource;

import dataDTO.VanityDTO;
import datatypes.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests for the VanityTableDataGateway
 */
public class VanityInventoryTableDataGatewayTest extends ServerSideTest
{
    protected VanityInventoryTableDataGateway gateway;

    /**
     * Test to make sure we can add an item to a player's inventory
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void canAddVanityToInventory() throws DatabaseException
    {
        VanityDTO item = new VanityDTO(VanityForTest.MerlinShirt.getId(),
                VanityForTest.MerlinShirt.getName(),
                VanityForTest.MerlinShirt.getDescription(),
                VanityForTest.MerlinShirt.getTextureName(),
                VanityType.fromInt(VanityForTest.MerlinShirt.getVanityType()));
        ArrayList<VanityDTO> actualItems =
                gateway.getAllOwnedItems(PlayersForTest.JOHN.getPlayerID());

        assertFalse(actualItems.contains(item));

        gateway.addItemToInventory(PlayersForTest.JOHN.getPlayerID(), item.getID());
        actualItems = gateway.getAllOwnedItems(PlayersForTest.JOHN.getPlayerID());

        assertTrue(actualItems.contains(item));
    }

    /**
     * Test to make sure we can add mulitple items to a players inventory
     *
     * @throws DatabaseException shouldnt
     */
    @Test
    public void canUpdateInventoryMultiple() throws DatabaseException
    {
        ArrayList<VanityDTO> actualItems =
                gateway.getAllOwnedItems(PlayersForTest.JOHN.getPlayerID());
        VanityDTO item = new VanityDTO(VanityForTest.MerlinShirt.getId(),
                VanityForTest.MerlinShirt.getName(),
                VanityForTest.MerlinShirt.getDescription(),
                VanityForTest.MerlinShirt.getTextureName(),
                VanityType.fromInt(VanityForTest.MerlinShirt.getVanityType()));
        VanityDTO item2 = new VanityDTO(VanityForTest.BlueShirt.getId(),
                VanityForTest.BlueShirt.getName(),
                VanityForTest.BlueShirt.getDescription(),
                VanityForTest.BlueShirt.getTextureName(),
                VanityType.fromInt(VanityForTest.BlueShirt.getVanityType()));

        assertFalse(actualItems.contains(item));
        assertFalse(actualItems.contains(item2));

        actualItems.add(item);
        actualItems.add(item2);

        gateway.updateInventory(PlayersForTest.JOHN.getPlayerID(), actualItems);

        actualItems = gateway.getAllOwnedItems(PlayersForTest.JOHN.getPlayerID());
        assertTrue(actualItems.contains(item));
        assertTrue(actualItems.contains(item2));

    }

    /**
     * Test to make sure we can update the inventory with no items
     *
     * @throws DatabaseException shouldnt
     */
    @Test
    public void canUpdateInventoryNone() throws DatabaseException
    {
        ArrayList<VanityDTO> actualItemsPrev =
                gateway.getAllOwnedItems(PlayersForTest.JOHN.getPlayerID());

        gateway.updateInventory(PlayersForTest.JOHN.getPlayerID(), actualItemsPrev);

        ArrayList<VanityDTO> actualItemsNew =
                gateway.getAllOwnedItems(PlayersForTest.JOHN.getPlayerID());

        assertTrue(actualItemsPrev.containsAll(actualItemsNew) &&
                actualItemsNew.containsAll(actualItemsPrev));

    }

    /**
     * Test to make sure we update the inventory with a single item
     *
     * @throws DatabaseException shouldnt
     */
    @Test
    public void canUpdateInventorySingle() throws DatabaseException
    {
        ArrayList<VanityDTO> actualItems =
                gateway.getAllOwnedItems(PlayersForTest.JOHN.getPlayerID());
        VanityDTO item = new VanityDTO(VanityForTest.MerlinShirt.getId(),
                VanityForTest.MerlinShirt.getName(),
                VanityForTest.MerlinShirt.getDescription(),
                VanityForTest.MerlinShirt.getTextureName(),
                VanityType.fromInt(VanityForTest.MerlinShirt.getVanityType()));

        assertFalse(actualItems.contains(item));


        actualItems.add(item);


        gateway.updateInventory(PlayersForTest.JOHN.getPlayerID(), actualItems);

        actualItems = gateway.getAllOwnedItems(PlayersForTest.JOHN.getPlayerID());
        assertTrue(actualItems.contains(item));

    }

    /**
     * Test to make sure we can update what they are wearing
     * Requires test player to have an item in their inventory
     * that they aren't currently wearing
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void canUpdateWearing() throws DatabaseException
    {
        ArrayList<VanityDTO> actualItems =
                gateway.getAllOwnedItems(PlayersForTest.JOHN.getPlayerID());
        ArrayList<VanityDTO> actualWearing =
                gateway.getWearing(PlayersForTest.JOHN.getPlayerID());
        boolean found = false;
        int i = 0;
        VanityDTO item = null;
        while (i < actualItems.size() && !found)
        {
            if (!actualWearing.contains(actualItems.get(i)))
            {
                item = actualItems.get(i);
                found = true;
            }
            i++;
        }
        VanityDTO item0 = actualWearing.get(0);
        ArrayList<VanityDTO> newWearing = new ArrayList<>();
        newWearing.add(item);

        assertFalse(actualWearing.contains(item));
        gateway.updateCurrentlyWearing(PlayersForTest.JOHN.getPlayerID(), newWearing);
        actualWearing = gateway.getWearing(PlayersForTest.JOHN.getPlayerID());
        assertTrue(actualWearing.contains(item));
        assertFalse(actualWearing.contains(item0));
    }

    /**
     * Test to make sure we can't add duplicate items to inventory
     *
     * @throws DatabaseException when a duplicate item is added
     */
    @Test(expected = DatabaseException.class)
    public void cantAddDuplicate() throws DatabaseException
    {
        ArrayList<VanityDTO> actualItems =
                gateway.getAllOwnedItems(PlayersForTest.JOHN.getPlayerID());
        VanityDTO item = actualItems.get(0);
        assertTrue(actualItems.contains(item));
        gateway.addItemToInventory(PlayersForTest.JOHN.getPlayerID(), item.getID());
    }

    /**
     * Tests to make sure we can get all the items a player owns,
     * including default items
     */
    @Test
    public void getAllOwnedItemsTest() throws DatabaseException
    {
        ArrayList<Integer> mockOwnedItemIDs = new ArrayList<>();
        for (PlayerOwnsVanityForTest p : PlayerOwnsVanityForTest.values())
        {
            if (p.getPlayerID() == PlayersForTest.JOHN.getPlayerID())
            {
                mockOwnedItemIDs.add(p.getVanityID());
            }
        }
        for (DefaultItemsForTest item : DefaultItemsForTest.values())
        {
            if (!mockOwnedItemIDs.contains(item.getDefaultID()))
            {
                mockOwnedItemIDs.add(item.getDefaultID());
            }
        }
        ArrayList<VanityDTO> itemsFromGateway;
        itemsFromGateway = gateway.getAllOwnedItems(PlayersForTest.JOHN.getPlayerID());
        assertEquals(mockOwnedItemIDs.size(), itemsFromGateway.size());

        int numMatches = 0;
        for (Integer mockOwnedItemID : mockOwnedItemIDs)
        {
            for (VanityDTO vanityDTO : itemsFromGateway)
            {
                if (mockOwnedItemID == vanityDTO.getID())
                {
                    numMatches++;
                }
            }
        }
        assertEquals(itemsFromGateway.size(), numMatches);
    }

    /**
     * Test to make sure we can get what a player is wearing
     */
    @Test
    public void getIsWearingTest() throws DatabaseException
    {
        ArrayList<PlayerOwnsVanityForTest> items = new ArrayList<>();
        for (PlayerOwnsVanityForTest p : PlayerOwnsVanityForTest.values())
        {
            if (p.getPlayerID() == PlayersForTest.JOHN.getPlayerID() &&
                    p.getIsWearing() == 1)
            {
                items.add(p);
            }
        }
        ArrayList<VanityDTO> itemsFromGateway;
        itemsFromGateway = gateway.getWearing(PlayersForTest.JOHN.getPlayerID());
        assertEquals(items.size(), itemsFromGateway.size());

        for (int i = 0; i < items.size(); i++)
        {
            assertEquals(items.get(i).getVanityID(), itemsFromGateway.get(i).getID());
        }
    }

    /**
     * Tests to make sure singleton pattern works
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void isASingleton() throws DatabaseException
    {
        VanityInventoryTableDataGateway a = findGateway();
        VanityInventoryTableDataGateway b = findGateway();
        assertEquals(a, b);
        assertNotNull(a);
    }

    /**
     * Get the right gateway and set up the gateway
     *
     * @throws DatabaseException shouldn't
     */
    @Before
    public void setup() throws DatabaseException
    {
        gateway = findGateway();
    }

    /**
     * Test to make sure they can't put on something they don't own
     *
     * @throws DatabaseException when they try to put on something they don't own
     */
    @Test(expected = DatabaseException.class)
    public void wearButDoesNotOwn() throws DatabaseException
    {
        ArrayList<VanityDTO> actualItems =
                gateway.getAllOwnedItems(PlayersForTest.JOHN.getPlayerID());
        VanityDTO item = new VanityDTO(VanityForTest.TutorShirt.getId(),
                VanityForTest.TutorShirt.getName(),
                VanityForTest.TutorShirt.getDescription(),
                VanityForTest.TutorShirt.getTextureName(),
                VanityType.fromInt(VanityForTest.TutorShirt.getVanityType()),
                VanityForTest.TutorShirt.getPrice());
        assertFalse(actualItems.contains(item));
        ArrayList<VanityDTO> newWearing = new ArrayList<>();
        newWearing.add(item);
        gateway.updateCurrentlyWearing(PlayersForTest.JOHN.getPlayerID(), newWearing);
    }

    VanityInventoryTableDataGateway findGateway() throws DatabaseException
    {
        return VanityInventoryTableDataGateway.getSingleton();
    }
}

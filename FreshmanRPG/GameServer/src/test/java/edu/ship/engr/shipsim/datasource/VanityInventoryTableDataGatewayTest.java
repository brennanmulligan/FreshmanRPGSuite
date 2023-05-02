package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.*;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the VanityTableDataGateway
 */
@GameTest("GameServer")
public class VanityInventoryTableDataGatewayTest
{
    protected VanityInventoryTableDataGateway gateway;

    /**
     * Get the right gateway and set up the gateway
     *
     * @throws DatabaseException shouldn't
     */
    @BeforeEach
    public void setup() throws DatabaseException
    {
        gateway = findGateway();
    }

    /**
     * Test to make sure we can add an item to a player's inventory
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void canAddVanityToInventory() throws DatabaseException
    {
        VanityDTO item = new VanityDTO(VanityItemsForTest.MERLIN_SHIRT.getId(),
                VanityItemsForTest.MERLIN_SHIRT.getName(),
                VanityItemsForTest.MERLIN_SHIRT.getDescription(),
                VanityItemsForTest.MERLIN_SHIRT.getTextureName(),
                VanityItemsForTest.MERLIN_SHIRT.getVanityType(),
                VanityItemsForTest.MERLIN_SHIRT.getPrice());
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
        VanityDTO item = new VanityDTO(VanityItemsForTest.MERLIN_SHIRT.getId(),
                VanityItemsForTest.MERLIN_SHIRT.getName(),
                VanityItemsForTest.MERLIN_SHIRT.getDescription(),
                VanityItemsForTest.MERLIN_SHIRT.getTextureName(),
                VanityItemsForTest.MERLIN_SHIRT.getVanityType(),
                VanityItemsForTest.MERLIN_SHIRT.getPrice());
        VanityDTO item2 = new VanityDTO(VanityItemsForTest.BLUE_SHIRT.getId(),
                VanityItemsForTest.BLUE_SHIRT.getName(),
                VanityItemsForTest.BLUE_SHIRT.getDescription(),
                VanityItemsForTest.BLUE_SHIRT.getTextureName(),
                VanityItemsForTest.BLUE_SHIRT.getVanityType(),
                VanityItemsForTest.BLUE_SHIRT.getPrice());

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
        VanityDTO item = new VanityDTO(VanityItemsForTest.MERLIN_SHIRT.getId(),
                VanityItemsForTest.MERLIN_SHIRT.getName(),
                VanityItemsForTest.MERLIN_SHIRT.getDescription(),
                VanityItemsForTest.MERLIN_SHIRT.getTextureName(),
                VanityItemsForTest.MERLIN_SHIRT.getVanityType(),
                VanityItemsForTest.MERLIN_SHIRT.getPrice());

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
     */
    @Test
    public void cantAddDuplicate()
    {
        assertThrows(DatabaseException.class, () ->
        {
            ArrayList<VanityDTO> actualItems =
                    gateway.getAllOwnedItems(PlayersForTest.JOHN.getPlayerID());
            VanityItemsForTest ownedByJohn = VanityItemsForTest.getByID(VanityInventoryItemsForTest.JOHN_OWNS_HAT.getVanityID());
            VanityDTO item = new VanityDTO(ownedByJohn.getId(), ownedByJohn.getName(), ownedByJohn.getDescription(),
                    ownedByJohn.getTextureName(), ownedByJohn.getVanityType(), ownedByJohn.getPrice());
            assertTrue(actualItems.contains(item));
            gateway.addItemToInventory(PlayersForTest.JOHN.getPlayerID(), item.getID());
        });
    }

    /**
     * Tests to make sure we can get all the items a player owns,
     * including default items
     */
    @Test
    public void getAllOwnedItemsTest() throws DatabaseException
    {
        ArrayList<VanityDTO> mockOwnedItems = new ArrayList<>();
        for (VanityInventoryItemsForTest p : VanityInventoryItemsForTest.values())
        {
            if (p.getPlayerID() == PlayersForTest.JOHN.getPlayerID())
            {
                mockOwnedItems.add(VanityItemsForTest.toVanityDTO(VanityItemsForTest.getByID(p.getVanityID())));
            }
        }
        for (VanityDTO item : VanityItemsForTest.getAllDefaultItems())
        {
            if (!mockOwnedItems.contains(item))
            {
                mockOwnedItems.add(item);
            }
        }
        ArrayList<VanityDTO> itemsFromGateway;
        itemsFromGateway = gateway.getAllOwnedItems(PlayersForTest.JOHN.getPlayerID());
        assertEquals(mockOwnedItems.size(), itemsFromGateway.size());
        assertTrue(mockOwnedItems.containsAll(itemsFromGateway) && itemsFromGateway.containsAll(mockOwnedItems));
    }

    /**
     * Test to make sure we can get what a player is wearing
     */
    @Test
    public void getIsWearingTest() throws DatabaseException
    {
        ArrayList<VanityInventoryItemsForTest> items = new ArrayList<>();
        for (VanityInventoryItemsForTest p : VanityInventoryItemsForTest.values())
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
     * Test to make sure they can't put on something they don't own
     */
    @Test
    public void wearButDoesNotOwn()
    {
        assertThrows(DatabaseException.class, () ->
        {
            ArrayList<VanityDTO> actualItems =
                    gateway.getAllOwnedItems(PlayersForTest.JOHN.getPlayerID());
            VanityDTO item = new VanityDTO(VanityItemsForTest.TUTOR_SHIRT.getId(),
                    VanityItemsForTest.TUTOR_SHIRT.getName(),
                    VanityItemsForTest.TUTOR_SHIRT.getDescription(),
                    VanityItemsForTest.TUTOR_SHIRT.getTextureName(),
                    VanityItemsForTest.TUTOR_SHIRT.getVanityType(),
                    VanityItemsForTest.TUTOR_SHIRT.getPrice()
            );
            assertFalse(actualItems.contains(item));
            ArrayList<VanityDTO> newWearing = new ArrayList<>();
            newWearing.add(item);
            gateway.updateCurrentlyWearing(PlayersForTest.JOHN.getPlayerID(), newWearing);
        });
    }

    VanityInventoryTableDataGateway findGateway() throws DatabaseException
    {
        return VanityInventoryTableDataGateway.getSingleton();
    }
}

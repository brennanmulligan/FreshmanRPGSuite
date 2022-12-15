package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.DefaultItemsForTest;
import edu.ship.engr.shipsim.datatypes.VanityItemsForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the DefaultItemsTableDataGateway
 */
@GameTest("GameServer")
public class DefaultItemsTableDataGatewayTest
{
    protected DefaultItemsTableDataGateway gateway;

    /**
     * Tests to make sure we cannot add a duplicate default item
     */
    @Test
    public void cannotAddDuplicateItem()
    {
        assertThrows(DatabaseException.class, () ->
        {
            ArrayList<Integer> itemsFromGateway =
                    getIDsFromVanityDTO(gateway.getDefaultItems());
            gateway.addDefaultItem(itemsFromGateway.get(0));
        });
    }

    /**
     * Tests to make sure we cannot add an invalid default item
     */
    @Test
    public void cannotAddInvalidVanityItem()
    {
        assertThrows(DatabaseException.class, () ->
        {
            gateway.addDefaultItem(-1);
        });
    }

    /**
     * Tests to make sure we cannot remove an invalid default item
     */
    @Test
    public void cannotRemoveInvalidItem()
    {
        assertThrows(DatabaseException.class, () ->
        {
            gateway.removeDefaultItem(-1);
        });
    }

    @Test
    public void cannotSetInvalidDefault()
    {
        assertThrows(DatabaseException.class, () ->
        {
            gateway.setDefaultWearing(-1);
        });
    }

    @Test
    public void changeDefault() throws DatabaseException
    {
        ArrayList<Integer> wearing = getIDsFromVanityDTO(gateway.getDefaultWearing());
        assertFalse(wearing.contains(DefaultItemsForTest.LIGHT_BLUE_EYES.getDefaultID()));
        gateway.setDefaultWearing(DefaultItemsForTest.LIGHT_BLUE_EYES.getDefaultID());
        wearing = getIDsFromVanityDTO(gateway.getDefaultWearing());
        assertTrue(wearing.contains(DefaultItemsForTest.LIGHT_BLUE_EYES.getDefaultID()));
        assertFalse(wearing.contains(DefaultItemsForTest.DEFAULT_EYES.getDefaultID()));
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
    @BeforeEach
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
        assertFalse(itemsFromGateway.contains(VanityItemsForTest.MERLIN_HAT.getId()));

        gateway.addDefaultItem(VanityItemsForTest.MERLIN_HAT.getId());

        itemsFromGateway = getIDsFromVanityDTO(gateway.getDefaultItems());

        assertTrue(itemsFromGateway.contains(VanityItemsForTest.MERLIN_HAT.getId()));
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
        gateway.addDefaultItem(VanityItemsForTest.MERLIN_HAT.getId());
        gateway.removeDefaultItem(VanityItemsForTest.MERLIN_HAT.getId());

        ArrayList<Integer> itemsFromGateway =
                getIDsFromVanityDTO(gateway.getDefaultItems());

        assertFalse(itemsFromGateway.contains(VanityItemsForTest.MERLIN_HAT.getId()));
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

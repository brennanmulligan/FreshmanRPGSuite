package edu.ship.engr.shipsim.model;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.testing.annotations.GameTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class VanityInventoryTest
{

    /**
     * Get a player id to use with tests
     */
    int playerID = PlayersForTest.VANITY_PLAYER.getPlayerID();

    /**
     * Create some items to use with tests
     */
    VanityDTO item1 = new VanityDTO(100, "hello", "world", "!", VanityType.HAT, 0);
    VanityDTO item2 = new VanityDTO(200, "lorem", "ipsum", "!", VanityType.BODY, 0);

    public VanityInventoryTest() throws DatabaseException
    {
    }

    /**
     * Tests the addVanityItem method
     * Creates an empty itemList and inventory and then checks that items
     * are successfully added
     */
    @Test
    public void testAddVanityItem() throws DatabaseException
    {
        VanityInventory inventory = new VanityInventory(playerID);

        assertFalse(inventory.getInventory().contains(item1));
        inventory.addVanityItem(item1);
        assertTrue(inventory.getInventory().contains(item1));

        assertFalse(inventory.getInventory().contains(item2));
        inventory.addVanityItem(item2);
        assertTrue(inventory.getInventory().contains(item2));
    }

    /**
     * Tests the removeVanityItem method
     * Creates an empty itemList and inventory and then checks that items
     * are successfully removed
     */
    @Test
    public void testRemoveVanityItem() throws DatabaseException
    {
        VanityInventory inventory = new VanityInventory(playerID);
        inventory.addVanityItem(item1);
        inventory.addVanityItem(item2);

        assertTrue(inventory.getInventory().contains(item1));
        inventory.removeVanityItem(item1);
        assertFalse(inventory.getInventory().contains(item1));

        assertTrue(inventory.getInventory().contains(item2));
        inventory.removeVanityItem(item2);
        assertFalse(inventory.getInventory().contains(item2));
    }

    /**
     * Tests the getVanityItemByID method
     * Creates an empty itemList and inventory and then checks that items
     * are successfully retrieved from the inventory by using their ID as
     * well as a dummy ID to check that it successfully returns null if it
     * doesn't exist in the inventory
     */
    @Test
    public void testGetVanityItemByID() throws DatabaseException
    {
        VanityInventory inventory = new VanityInventory(playerID);
        inventory.addVanityItem(item1);
        inventory.addVanityItem(item2);

        assertEquals(item1, inventory.getVanityItemByID(item1.getID()));
        assertEquals(item2, inventory.getVanityItemByID(item2.getID()));
        assertNull(inventory.getVanityItemByID(3));
    }
}

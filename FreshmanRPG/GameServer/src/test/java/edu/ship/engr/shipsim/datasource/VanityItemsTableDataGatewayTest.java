package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.VanityItemsForTest;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the VanityItemsTableDataGateway
 */
@GameTest("GameServer")
public class VanityItemsTableDataGatewayTest
{
    protected VanityItemsTableDataGateway gateway;

    /**
     * Get the right gateway and set up the gateway
     *
     * @throws DatabaseException shouldnt
     */
    @BeforeEach
    public void setup() throws DatabaseException
    {
        gateway = findGateway();
    }

    /**
     * Test to make sure we can add a vanity item
     *
     * @throws DatabaseException shouldnt
     */
    @Test
    public void addVanityItem() throws DatabaseException
    {
        ArrayList<VanityDTO> old = gateway.getAllVanityItems();

        VanityDTO newItem =
                new VanityDTO(old.size() + 1, "new", "new item", "new",
                        VanityType.HAT, 0);
        assertFalse(old.contains(newItem));
        gateway.addVanityItem(newItem.getName(), newItem.getDescription(),
                newItem.getTextureName(), newItem.getVanityType(), 0, false, false, false, false);
        ArrayList<VanityDTO> newGate = gateway.getAllVanityItems();
        VanityDTO lastItem = newGate.get(newGate.size() - 1);
        assertEquals(newItem.getName(), lastItem.getName());
        assertEquals(newItem.getDescription(), lastItem.getDescription());
        assertEquals(newItem.getTextureName(), lastItem.getTextureName());
        assertEquals(newItem.getVanityType(), lastItem.getVanityType());
    }

    /**
     * Tests to make sure we can get all vanity items
     *
     * @throws DatabaseException shouldnt
     */
    @Test
    public void getAllVanityTest() throws DatabaseException
    {
        ArrayList<VanityDTO> items = new ArrayList<>();
        for (VanityItemsForTest v : VanityItemsForTest.values())
        {
            VanityDTO d = new VanityDTO(v.getId(), v.getName(), v.getDescription(),
                    v.getTextureName(), v.getVanityType(), v.getPrice());
            items.add(d);
        }

        ArrayList<VanityDTO> itemsFromGateway = gateway.getAllVanityItems();
        assertEquals(items.size(), itemsFromGateway.size());

        assertTrue(items.containsAll(itemsFromGateway));
        assertTrue(itemsFromGateway.containsAll(items));
    }

    /**
     * Test to make sure we can get an item from its ID
     *
     * @throws DatabaseException shouldnt
     */
    @Test
    public void getItemByID() throws DatabaseException
    {
        VanityItemsForTest item = VanityItemsForTest.MERLIN_HAT;
        VanityDTO dto = new VanityDTO(item.getId(), item.getName(), item.getDescription(),
                item.getTextureName(), item.getVanityType(), item.getPrice()
        );
        assertEquals(dto, gateway.getVanityItemByID(item.getId()));
    }

    /**
     * Tests to make sure singleton pattern works
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void isASingleton() throws DatabaseException
    {
        VanityItemsTableDataGateway a = findGateway();
        VanityItemsTableDataGateway b = findGateway();
        assertEquals(a, b);
        assertNotNull(a);
    }

    VanityItemsTableDataGateway findGateway() throws DatabaseException
    {
        return VanityItemsTableDataGateway.getSingleton();
    }

    /**
     * Test to make sure that the default items present in the enums, and then pushed into the database are retrieved correctly
     * @throws DatabaseException shouldn't
     */
    @Test
    public void testGetDefaultItems() throws DatabaseException
    {
        ArrayList<VanityDTO> fromEnum = VanityItemsForTest.getAllDefaultItems();
        ArrayList<VanityDTO> fromDB = VanityItemsTableDataGateway.getAllDefaultItems();

        //make sure the two lists contain exactly the same items
        assertTrue(fromEnum.containsAll(fromDB) && fromDB.containsAll(fromEnum));
    }

    /**
     * Test to make sure that the shop items present in the enums, and then pushed into the database are retrieved correctly
     * @throws DatabaseException shouldn't
     */
    @Test
    public void testGetAllInShopItems() throws DatabaseException
    {
        ArrayList<VanityDTO> fromEnum = VanityItemsForTest.getAllShopItems();
        ArrayList<VanityDTO> fromDB = VanityItemsTableDataGateway.getAllInShopItems();

        assertTrue(fromEnum.containsAll(fromDB) && fromDB.containsAll(fromEnum));
    }

    /**
     * Test that makes sure the gateway correctly identifies if an item is deletable or not
     * @throws DatabaseException if the item does not exist (will not happen).
     */
    @Test
    public void testDeletability() throws DatabaseException
    {
        //Currently BIKE is the only one where isDeletable is true
        assertTrue(VanityItemsTableDataGateway.checkDeletability(VanityItemsForTest.BIKE.getId()));
        //just picked a random vanity item where isDeletable is false
        assertFalse(VanityItemsTableDataGateway.checkDeletability(VanityItemsForTest.PINK_EYES.getId()));
    }




}

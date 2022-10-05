package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.InteractableItemDTO;
import edu.ship.engr.shipsim.datatypes.InteractableItemsForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jake Moore (I think)
 */
@GameTest("GameServer")
public class InteractableItemTableDataGatewayTest
{

    // gateway instance
    private InteractableItemTableDataGateway gateway;

    /**
     * @return the gateway we should test
     */
    public InteractableItemTableDataGateway getGatewaySingleton()
    {
        return InteractableItemTableDataGateway.getSingleton();
    }

    /**
     * See that retrieved gateway is always the referencing the same instance
     *
     * @throws DatabaseException if an error occurs
     */
    @Test
    public void isASingleton() throws DatabaseException
    {
        InteractableItemTableDataGateway x = getGatewaySingleton();
        InteractableItemTableDataGateway y = getGatewaySingleton();
        assertSame(x, y);
        assertNotNull(x);
    }

    /**
     * Test get all items
     *
     * @throws DatabaseException if an error occurs
     */
    @Test
    public void testGetAllItems() throws DatabaseException
    {
        InteractableItemTableDataGateway gateway = getGatewaySingleton();
        ArrayList<InteractableItemDTO> itemList = gateway.getAllItems();

        assertEquals(itemList.get(0).getId(), InteractableItemsForTest.BOOK.getItemID());
        assertEquals(itemList.get(1).getId(), InteractableItemsForTest.CHEST.getItemID());
    }

    /**
     * test that we can get all interactable items in the same map
     *
     * @throws DatabaseException if an error occurs
     */
    @Test
    public void testItemsForMap() throws DatabaseException
    {
        InteractableItemTableDataGateway gateway = getGatewaySingleton();
        ArrayList<InteractableItemDTO> itemList = gateway.getItemsOnMap("quad.tmx");

        assertEquals(itemList.get(0).getId(), InteractableItemsForTest.BOOK.getItemID());
        assertEquals(itemList.get(1).getId(), InteractableItemsForTest.CHEST.getItemID());

    }
}

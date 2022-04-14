package communication.handlers;

import communication.messages.VanityShopInventoryResponseMessage;
import dataDTO.VanityDTO;
import datatypes.VanityType;
import model.ClientModelFacade;
import model.CommandVanityShopInventoryResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the Vanity Shop Inventory Response Message Handler
 * @author Aaron, Jake
 */
public class VanityShopInventoryResponseMessageHandlerTest
{
    /**
     * Reset the ModelFacade
     */
    @Before
    public void reset()
    {
        ClientModelFacade.resetSingleton();
        ClientModelFacade.getSingleton(true, false);
    }

    /**
     * Test to make sure we handle the right message
     */
    @Test
    public void testTypeWeHandle()
    {
        VanityShopInventoryResponseMessageHandler h = new VanityShopInventoryResponseMessageHandler();
        assertEquals(VanityShopInventoryResponseMessage.class, h.getMessageTypeWeHandle());
    }

    /**
     * Tests that we queue the message to the facade.
     * @throws InterruptedException shouldn't
     */
    @Test
    public void handleHighScoreResponseMessage() throws InterruptedException
    {
        ArrayList<VanityDTO> inventory = new ArrayList<>();
        inventory.add(new VanityDTO(0, "test0", "test desc", "myTexture", VanityType.BODY, 0));
        inventory.add(new VanityDTO(1, "test1", "test desc", "myTexture", VanityType.BODY, 1));

        VanityShopInventoryResponseMessage msg = new VanityShopInventoryResponseMessage(inventory);
        VanityShopInventoryResponseMessageHandler handle = new VanityShopInventoryResponseMessageHandler();
        handle.process(msg);

        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
        CommandVanityShopInventoryResponse cmd = (CommandVanityShopInventoryResponse) ClientModelFacade.getSingleton().getNextCommand();
        assertEquals(inventory, cmd.getInventory());
        ClientModelFacade.getSingleton().emptyQueue();
    }
}

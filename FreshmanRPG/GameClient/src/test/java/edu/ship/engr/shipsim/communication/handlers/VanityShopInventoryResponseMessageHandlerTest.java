package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.VanityShopInventoryResponseMessage;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandVanityShopInventoryResponse;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the Vanity Shop Inventory Response Message Handler
 *
 * @author Aaron, Jake
 */
@GameTest("GameClient")
@ResetClientModelFacade(shouldClearQueue = true)
public class VanityShopInventoryResponseMessageHandlerTest
{
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
     *
     * @throws InterruptedException shouldn't
     */
    @Test
    public void handleHighScoreResponseMessage() throws InterruptedException
    {
        ArrayList<VanityDTO> inventory = new ArrayList<>();
        inventory.add(new VanityDTO(0, "test0", "test desc", "myTexture", VanityType.BODY, 0));
        inventory.add(new VanityDTO(1, "test1", "test desc", "myTexture", VanityType.BODY, 1));

        VanityShopInventoryResponseMessage msg = new VanityShopInventoryResponseMessage(inventory, false);
        VanityShopInventoryResponseMessageHandler handle = new VanityShopInventoryResponseMessageHandler();
        handle.process(msg);

        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
        CommandVanityShopInventoryResponse cmd = (CommandVanityShopInventoryResponse) ClientModelFacade.getSingleton().getNextCommand();
        assertEquals(inventory, cmd.getInventory());
    }
}

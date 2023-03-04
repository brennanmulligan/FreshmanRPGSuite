package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.VanityShopInventoryRequestMessage;
import edu.ship.engr.shipsim.communication.messages.VanityShopInventoryResponseMessage;
import edu.ship.engr.shipsim.model.PlayerManager;

/**
 * Receives and handles a VanityShopInventoryResponseMessage
 *
 * @author Jake, Aaron
 */
public class VanityShopInventoryRequestMessageHandler extends MessageHandler
{
    @Override
    public void process(Message msg)
    {
        try
        {
            this.getStateAccumulator()
                    .queueMessage(new VanityShopInventoryResponseMessage(PlayerManager.getSingleton().getVanityShopInventory(), msg.isQuietMessage()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return VanityShopInventoryRequestMessage.class;
    }
}

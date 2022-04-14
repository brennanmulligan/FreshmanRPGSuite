package communication.handlers;

import communication.messages.Message;
import communication.messages.VanityShopInventoryResponseMessage;
import communication.messages.VanityShopInventoryRequestMessage;
import model.PlayerManager;

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
                    .queueMessage(new VanityShopInventoryResponseMessage(PlayerManager.getSingleton().getVanityShopInventory()));
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

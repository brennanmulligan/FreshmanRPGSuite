package communication.handlers;

import communication.messages.Message;
import communication.messages.VanityShopInventoryResponseMessage;
import model.ClientModelFacade;
import model.CommandVanityShopInventoryResponse;

/**
 * Handler for the VanityShopInventoryResponseMessage
 * @author Aaron, Jake
 */
public class VanityShopInventoryResponseMessageHandler extends MessageHandler
{
    /**
     * Processes an incoming message
     *
     * @param msg the message to handle
     */
    @Override
    public void process(Message msg)
    {
        if (msg.getClass().equals(VanityShopInventoryResponseMessage.class))
        {
            VanityShopInventoryResponseMessage actualMsg = (VanityShopInventoryResponseMessage) msg;
            CommandVanityShopInventoryResponse cmd = new CommandVanityShopInventoryResponse(actualMsg.getVanityShopInventory());
            ClientModelFacade.getSingleton().queueCommand(cmd);
        }
    }

    /**
     * get the type of message this handler can process
     *
     * @return the type of message
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return VanityShopInventoryResponseMessage.class;
    }
}

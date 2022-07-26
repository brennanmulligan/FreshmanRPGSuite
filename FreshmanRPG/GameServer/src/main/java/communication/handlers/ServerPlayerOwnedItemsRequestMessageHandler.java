package communication.handlers;

import communication.messages.Message;
import communication.messages.ServerPlayerOwnedItemsRequestMessage;
import communication.messages.ServerPlayerOwnedItemsResponseMessage;
import dataDTO.VanityDTO;
import model.PlayerManager;

import java.util.ArrayList;

public class ServerPlayerOwnedItemsRequestMessageHandler extends MessageHandler
{

    @Override
    public void process(Message msg)
    {
        ServerPlayerOwnedItemsRequestMessage actualMsg = (ServerPlayerOwnedItemsRequestMessage) msg;
        try
        {
            ArrayList<VanityDTO> ownedItems = PlayerManager.getSingleton().getPlayerFromID(actualMsg.getPlayerID()).getAllOwnedItems();
            ServerPlayerOwnedItemsResponseMessage msgToSend = new ServerPlayerOwnedItemsResponseMessage(ownedItems);
            this.getStateAccumulator().queueMessage(msgToSend);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return ServerPlayerOwnedItemsRequestMessage.class;
    }
}

package communication.handlers;

import communication.messages.Message;
import communication.messages.ServerPlayerOwnedItemsRequestMessage;
import communication.messages.ServerPlayerOwnedItemsResponseMessage;
import model.PlayerManager;

public class ServerPlayerOwnedItemsRequestMessageHandler extends MessageHandler
{

    @Override
    public void process(Message msg) {
        ServerPlayerOwnedItemsRequestMessage actualMsg = (ServerPlayerOwnedItemsRequestMessage) msg;
        System.out.println("step 4 " + actualMsg.getPlayerID());
        try
        {
            this.getStateAccumulator()
                    .queueMessage(new ServerPlayerOwnedItemsResponseMessage(PlayerManager.getSingleton()
                            .getPlayerFromID(actualMsg.getPlayerID()).getAllOwnedItems()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Class<?> getMessageTypeWeHandle() {
        return ServerPlayerOwnedItemsRequestMessage.class;
    }
}

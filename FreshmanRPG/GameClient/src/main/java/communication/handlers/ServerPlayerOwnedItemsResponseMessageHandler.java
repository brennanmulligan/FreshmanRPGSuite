package communication.handlers;

import communication.messages.Message;
import communication.messages.ServerPlayerOwnedItemsResponseMessage;
import model.ClientModelFacade;
import model.CommandServerPlayerOwnedItemsResponse;

/**
 * Handler for the ServerPlayerOwnedItemsResponseMessage
 */
public class ServerPlayerOwnedItemsResponseMessageHandler extends MessageHandler
{

    /**
     * Processes an incoming message
     *
     * @param msg the message to handle
     */
    @Override
    public void process(Message msg) {
        if (msg.getClass().equals(ServerPlayerOwnedItemsResponseMessage.class))
        {
            System.out.println("step 6");
            ServerPlayerOwnedItemsResponseMessage actualMsg = (ServerPlayerOwnedItemsResponseMessage) msg;
            CommandServerPlayerOwnedItemsResponse cmd = new CommandServerPlayerOwnedItemsResponse(actualMsg.getServerOwnedVanities());
            ClientModelFacade.getSingleton().queueCommand(cmd);
        }
    }

    /**
     * get the type of message this handler can process
     *
     * @return the type of message
     */
    @Override
    public Class<?> getMessageTypeWeHandle() {
        return ServerPlayerOwnedItemsResponseMessage.class;
    }
}

package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.ServerPlayerOwnedItemsResponseMessage;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandServerPlayerOwnedItemsResponse;

import java.util.ArrayList;

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
    public void process(Message msg)
    {
        if (msg.getClass().equals(ServerPlayerOwnedItemsResponseMessage.class))
        {
            System.out.println("step 6");
            ServerPlayerOwnedItemsResponseMessage actualMsg = (ServerPlayerOwnedItemsResponseMessage) msg;

            ArrayList<VanityDTO> items = actualMsg.getServerOwnedVanities();
            System.out.println("\nItems from ServerPlayerOwnedItemsResponseMessageHandler");
            items.forEach(System.out::println);

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
    public Class<?> getMessageTypeWeHandle()
    {
        return ServerPlayerOwnedItemsResponseMessage.class;
    }
}

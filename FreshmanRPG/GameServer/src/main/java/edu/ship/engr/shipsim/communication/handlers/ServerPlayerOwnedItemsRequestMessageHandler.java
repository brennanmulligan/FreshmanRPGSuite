package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.ServerPlayerOwnedItemsRequestMessage;
import edu.ship.engr.shipsim.communication.messages.ServerPlayerOwnedItemsResponseMessage;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;

import java.util.ArrayList;

public class ServerPlayerOwnedItemsRequestMessageHandler extends MessageHandler
{

    @Override
    public void process(Message msg)
    {
        ServerPlayerOwnedItemsRequestMessage actualMsg = (ServerPlayerOwnedItemsRequestMessage) msg;
        try
        {
            Player player =
                    PlayerManager.getSingleton().getPlayerFromID(actualMsg.getRelevantPlayerID());
            if (player == null)
            {
                return;
            }
            ArrayList<VanityDTO> ownedItems = player.getAllOwnedItems();
            ServerPlayerOwnedItemsResponseMessage msgToSend = new ServerPlayerOwnedItemsResponseMessage(ownedItems, msg.isQuietMessage());
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

package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PlayerPurchasedClothingMessage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandPlayerPurchasedClothing;

public class PlayerPurchasedClothingHandler extends MessageHandler
{
    @Override
    public void process(Message msg)
    {
        if (msg.getClass().equals(PlayerPurchasedClothingMessage.class))
        {
            PlayerPurchasedClothingMessage clothingMessage = (PlayerPurchasedClothingMessage) msg;
            CommandPlayerPurchasedClothing cmd = new CommandPlayerPurchasedClothing(clothingMessage.getRelevantPlayerID(), clothingMessage.getVanityDTO());
            ClientModelFacade.getSingleton().queueCommand(cmd);
        }
    }

    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return PlayerPurchasedClothingMessage.class;
    }
}

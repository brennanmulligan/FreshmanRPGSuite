package communication.handlers;

import communication.messages.Message;
import communication.messages.PlayerPurchasedClothingMessage;
import model.ClientModelFacade;
import model.CommandPlayerPurchasedClothing;

public class PlayerPurchasedClothingHandler extends MessageHandler
{
    @Override
    public void process(Message msg)
    {
        if(msg.getClass().equals(PlayerPurchasedClothingMessage.class))
        {
            PlayerPurchasedClothingMessage clothingMessage = (PlayerPurchasedClothingMessage) msg;
            CommandPlayerPurchasedClothing cmd = new CommandPlayerPurchasedClothing(clothingMessage.getPlayerID(), clothingMessage.getVanityDTO());
            ClientModelFacade.getSingleton().queueCommand(cmd);
        }
    }

    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return PlayerPurchasedClothingMessage.class;
    }
}

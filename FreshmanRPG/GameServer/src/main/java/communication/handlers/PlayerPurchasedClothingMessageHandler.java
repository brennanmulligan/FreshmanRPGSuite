package communication.handlers;

import communication.messages.Message;
import communication.messages.PlayerPurchasedClothingMessage;
import model.CommandPlayerPurchasedClothing;
import model.ModelFacade;

/**
 * Handler for when the player gets a clothing item to add to their inventory
 */
public class PlayerPurchasedClothingMessageHandler  extends MessageHandler
{
    /**
     * Processes an incoming message
     *
     * @param msg the message to handle
     */
    @Override
    public void process(Message msg)
    {
        PlayerPurchasedClothingMessage actualMsg = (PlayerPurchasedClothingMessage) msg;
        CommandPlayerPurchasedClothing cmd = new CommandPlayerPurchasedClothing(actualMsg.getPlayerID(), actualMsg.getVanityDTO());
        ModelFacade.getSingleton().queueCommand(cmd);
    }

    /**
     * get the type of message this handler can process
     *
     * @return the type of message
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return PlayerPurchasedClothingMessage.class;
    }
}

package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.ItemPurchasedMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.CommandItemPurchased;
import edu.ship.engr.shipsim.model.ModelFacade;

/**
 * Receives the itemPurchasedMessage and creates and item purchased command
 *
 * @author Andrew Stake
 */
public class ItemPurchasedHandler extends MessageHandler
{

    /**
     * When a purchase is made on the client side, the player object on the server must be updated
     *
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        if (msg.getClass().equals(ItemPurchasedMessage.class))
        {
            ItemPurchasedMessage iMsg = (ItemPurchasedMessage) msg;
            CommandItemPurchased cmd = new CommandItemPurchased(iMsg.getRelevantPlayerID(), iMsg.getPrice());
            ModelFacade.getSingleton().queueCommand(cmd);
        }
    }

    /**
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return ItemPurchasedMessage.class;
    }

}

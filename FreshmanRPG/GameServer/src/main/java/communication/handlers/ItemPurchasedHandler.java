package communication.handlers;

import communication.messages.ItemPurchasedMessage;
import communication.messages.Message;
import communication.messages.PlayerMovedMessage;
import model.CommandItemPurchased;
import model.ModelFacade;

/**
 * Receives the itemPurchasedMessage and creates and item purchased command
 *
 * @author Andrew Stake
 *
 */
public class ItemPurchasedHandler extends MessageHandler
{

	/**
	 * When a purchase is made on the client side, the player object on the server must be updated
	 *
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(ItemPurchasedMessage.class))
		{
			ItemPurchasedMessage iMsg = (ItemPurchasedMessage) msg;
			CommandItemPurchased cmd = new CommandItemPurchased(iMsg.getPlayerID(), iMsg.getPrice());
			ModelFacade.getSingleton().queueCommand(cmd);
		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return ItemPurchasedMessage.class;
	}

}

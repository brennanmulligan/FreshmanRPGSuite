package communication.handlers;

import communication.messages.Message;
import communication.messages.updateFriendListMessage;
import model.ClientModelFacade;
import model.UpdateFriendsListCommand;

/**
 *
 * @author Christian C, Andrew M
 *
 * This is the updated friend list handler
 *
 */
public class UpdatedFriendListHandler extends MessageHandler
{


	/**
	 * Processes the message and sends the command that the handler will get
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(updateFriendListMessage.class))
		{
			updateFriendListMessage friendListMessage = (updateFriendListMessage) msg;

			UpdateFriendsListCommand cmd = new UpdateFriendsListCommand(friendListMessage.getFriend());
			ClientModelFacade.getSingleton().queueCommand(cmd);
		}
	}

	/**
	 * get the message type
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{

		return updateFriendListMessage.class;
	}
}

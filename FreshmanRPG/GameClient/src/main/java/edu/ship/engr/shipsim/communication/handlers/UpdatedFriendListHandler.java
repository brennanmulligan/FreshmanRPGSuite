package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.updateFriendListMessage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.UpdateFriendsListCommand;

/**
 * @author Christian C, Andrew M
 * <p>
 * This is the updated friend list handler
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

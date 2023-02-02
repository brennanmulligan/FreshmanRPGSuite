package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.FriendDTO;

public class UpdateFriendsListCommand extends Command
{

    private final FriendDTO friendList;

    public UpdateFriendsListCommand(FriendDTO friend)
    {
        this.friendList = friend;
    }

    @Override
    void execute()
    {
        ThisClientsPlayer thisClientsPlayer = ClientPlayerManager.getSingleton().getThisClientsPlayer();
        thisClientsPlayer.updateCurrentFriendListReport(friendList);
    }


}

package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.FriendDTO;

import java.io.IOException;

public class UpdateFriendsListCommand extends Command
{

    private FriendDTO friendList;

    public UpdateFriendsListCommand(FriendDTO friend)
    {
        this.friendList = friend;
    }

    @Override
    boolean execute() throws IOException
    {

        ThisClientsPlayer thisClientsPlayer = ClientPlayerManager.getSingleton().getThisClientsPlayer();
        thisClientsPlayer.updateCurrentFriendListReport(friendList);
        System.out.println("In command");
        return true;
    }


}

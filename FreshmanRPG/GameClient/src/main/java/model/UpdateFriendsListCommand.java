package model;

import java.io.IOException;

import dataDTO.FriendDTO;

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

package model;

import java.io.IOException;
import java.util.ArrayList;

import dataDTO.FriendDTO;

/**
 * @author mina0
 *
 */
public class CommandSendCurrentFriendList extends Command
{

	ArrayList<FriendDTO> friendList;

	public CommandSendCurrentFriendList(ArrayList<FriendDTO> friendList)
	{
		this.friendList = friendList;
	}

	@Override
	boolean execute() throws IOException
	{
		ClientPlayerManager.getSingleton().getThisClientsPlayer().sendCurrentFriendListReport(friendList);
		return false;
	}

}

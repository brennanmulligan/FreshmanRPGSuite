package model.reports;

import java.util.ArrayList;

import dataDTO.FriendDTO;
import model.QualifiedObservableReport;

public class UpdateFriendsListReport implements QualifiedObservableReport
{

	private ArrayList<FriendDTO> friendList;

	/**
	 * Report constructor
	 * @param friendList
	 */
	public UpdateFriendsListReport(ArrayList<FriendDTO> friendList)
	{
		this.friendList = friendList;
	}

	/**
	 * Getter for friendlist
	 * @return
	 */
	public ArrayList<FriendDTO> getFriendList()
	{
		return friendList;
	}

}

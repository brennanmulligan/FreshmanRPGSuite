package model.reports;

import dataDTO.FriendDTO;
import datatypes.FriendStatusEnum;
import model.QualifiedObservableReport;

/*
 * @Author Christian Crouthamel, Andrew McCoy
 */
public class updateFriendListReport implements QualifiedObservableReport
{

	FriendDTO friend;

	/*
	 * constructor for the repeort that hold player ID and friend name
	 */
	public updateFriendListReport(FriendDTO friend)
	{

		this.friend = friend;
	}

	/**
	 * get player id
	 * @return
	 */
	public int getPlayerID()
	{
		return friend.getPlayerID();
	}

	/**
	 * getter for friend name
	 * @return
	 */
	public String getFriends()
	{
		return friend.getFriendName();
	}

	/*
	 * getter for status
	 */
	public FriendStatusEnum getStatus()
	{
		return friend.getStatus();
	}

	/*
	 * getter for friend ID
	 */
	public int getFriendID()
	{
		// TODO Auto-generated method stub
		return friend.getFriendID();
	}

	/**
	 * return the friend DTO
	 * @return
	 */
	public FriendDTO getFriendDTO()
	{
		return friend;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((friend == null) ? 0 : friend.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		updateFriendListReport other = (updateFriendListReport) obj;
		if (friend == null)
		{
			if (other.friend != null)
			{
				return false;
			}
		}
		else if (!friend.equals(other.friend))
		{
			return false;
		}
		return true;
	}
}
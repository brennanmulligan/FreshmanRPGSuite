package dataDTO;

import java.io.Serializable;

import datatypes.FriendStatusEnum;

/**
 *
 * @author Christian c, Andrew M
 *
 * This is the class for the friend data transfer object
 *
 */
public class FriendDTO implements Serializable
{

	private static final long serialVersionUID = 1L;
	private int playerID;
	private int friendID;
	private FriendStatusEnum status;
	private String playerName;
	private String friendName;

	/**
	 * FriendDTO constructor
	 * @param playerID
	 * @param friendID
	 * @param status
	 * @param playerName TODO
	 * @param friendName TODO
	 */
	public FriendDTO(int playerID, int friendID, FriendStatusEnum status, String playerName, String friendName)
	{
		this.playerID = playerID;
		this.friendID = friendID;
		this.status = status;
		this.playerName = playerName;
		this.friendName = friendName;
	}

	/**
	 * getter for player ID
	 * @return
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * getter for friend ID
	 * @return
	 */
	public int getFriendID()
	{
		return friendID;
	}

	/**
	 * getter for status
	 * @return
	 */
	public FriendStatusEnum getStatus()
	{
		return status;
	}

	public void setStatus(FriendStatusEnum status)
	{
		this.status = status;
	}

	/**
	 *getter for the player name 
	 * @return
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * getter for the friend name
	 * @return
	 */
	public String getFriendName()
	{
		return friendName;
	}


	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + friendID;
		result = prime * result + ((friendName == null) ? 0 : friendName.hashCode());
		result = prime * result + playerID;
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		FriendDTO other = (FriendDTO) obj;
		if (friendID != other.friendID)
		{
			return false;
		}
		if (friendName == null)
		{
			if (other.friendName != null)
			{
				return false;
			}
		}
		else if (!friendName.equals(other.friendName))
		{
			return false;
		}
		if (playerID != other.playerID)
		{
			return false;
		}
		if (playerName == null)
		{
			if (other.playerName != null)
			{
				return false;
			}
		}
		else if (!playerName.equals(other.playerName))
		{
			return false;
		}
		if (status != other.status)
		{
			return false;
		}
		return true;
	}


}
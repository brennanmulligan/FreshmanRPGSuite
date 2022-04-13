package model.reports;

import model.QualifiedObservableReport;

public class FriendChangedStateReport implements QualifiedObservableReport
{
	private int playerID;
	private String playerName;

	public FriendChangedStateReport(int playerID)
	{
		this.playerID = playerID;
		this.playerName = playerName;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + playerID;
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
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
		FriendChangedStateReport other = (FriendChangedStateReport) obj;
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
		return true;
	}

	public int getPlayerID()
	{
		return playerID;
	}

	public String getPlayerName()
	{
		return playerName;
	}
}

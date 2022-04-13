package model.reports;

import datatypes.Position;
import model.QualifiedObservableReport;

/**
 * Reports movement of any player playing on this server
 *
 * @author Merlin
 *
 */
public final class PlayerMovedReport implements QualifiedObservableReport
{

	private final Position newPosition;
	private final int playerID;
	private final String playerName;
	private final String mapName;

	/**
	 * @param playerID the ID of the player that moved
	 * @param playerName the unique name of the player that moved
	 * @param position the position he moved to
	 * @param mapName the name of the map the player is moving to
	 */
	public PlayerMovedReport(int playerID, String playerName, Position position, String mapName)
	{
		newPosition = position;
		this.playerID = playerID;
		this.playerName = playerName;
		this.mapName = mapName;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		PlayerMovedReport other = (PlayerMovedReport) obj;
		if (mapName == null)
		{
			if (other.mapName != null)
			{
				return false;
			}
		}
		else if (!mapName.equals(other.mapName))
		{
			return false;
		}
		if (newPosition == null)
		{
			if (other.newPosition != null)
			{
				return false;
			}
		}
		else if (!newPosition.equals(other.newPosition))
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
		return true;
	}

	/**
	 * @return the map the player moved on
	 */
	public String getMapName()
	{
		return mapName;
	}

	/**
	 * @return the newPosition
	 */
	public Position getNewPosition()
	{
		return newPosition;
	}

	/**
	 * @return the playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * Get the players unique name
	 *
	 * @return the name
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mapName == null) ? 0 : mapName.hashCode());
		result = prime * result + ((newPosition == null) ? 0 : newPosition.hashCode());
		result = prime * result + playerID;
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
		return result;
	}

}

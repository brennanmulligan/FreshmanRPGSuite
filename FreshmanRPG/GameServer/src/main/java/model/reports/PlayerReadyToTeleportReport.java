package model.reports;

import model.QualifiedObservableReport;

/**
 * Created after a player gets saved to the database.
 */
public class PlayerReadyToTeleportReport implements QualifiedObservableReport
{

	private int id = 0;
	private String map;

	/**
	 * @param playerID The id of the player that got saved.
	 * @param map The map that the player got saved to.
	 */
	public PlayerReadyToTeleportReport(int playerID, String map)
	{
		this.id = playerID;
		this.map = map;
	}

	/**
	 * @return The player id.
	 */
	public int getPlayerID()
	{
		return id;
	}

	/**
	 * @return The map that the player is on.
	 */
	public String getMap()
	{
		return map;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		return result;
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
		PlayerReadyToTeleportReport other = (PlayerReadyToTeleportReport) obj;
		if (id != other.id)
		{
			return false;
		}
		if (map == null)
		{
			if (other.map != null)
			{
				return false;
			}
		}
		else if (!map.equals(other.map))
		{
			return false;
		}
		return true;
	}

}

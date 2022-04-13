package model.reports;

import model.QualifiedObservableReport;

/**
 * Report that a player's appearance has been changed
 *
 * @author merlin
 *
 */
public class ChangePlayerAppearanceReport implements QualifiedObservableReport
{

	private int playerID;
	private String appearanceType;

	/**
	 * @param playerID
	 *            the player's unique id
	 * @param appearanceType
	 *            the new appearance type
	 */
	public ChangePlayerAppearanceReport(int playerID, String appearanceType)
	{
		super();
		this.playerID = playerID;
		this.appearanceType = appearanceType;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appearanceType == null) ? 0 : appearanceType.hashCode());
		result = prime * result + playerID;
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
		ChangePlayerAppearanceReport other = (ChangePlayerAppearanceReport) obj;
		if (appearanceType == null)
		{
			if (other.appearanceType != null)
			{
				return false;
			}
		}
		else if (!appearanceType.equals(other.appearanceType))
		{
			return false;
		}
		if (playerID != other.playerID)
		{
			return false;
		}
		return true;
	}

	/**
	 * @return the player's new appearance type
	 */
	public String getAppearanceType()
	{
		return appearanceType;
	}

	/**
	 * @return the ID of the player being redrawn
	 */
	public int getPlayerID()
	{
		return playerID;
	}
}

package model.reports;

import model.QualifiedObservableReport;

import java.util.Objects;

/**
 * @author Ryan Handley and Scott Bowling
 *
 */
public final class PlayerAppearanceChangeReport implements QualifiedObservableReport
{
//	private final int playerID;
//	private final String appearanceType;

	public PlayerAppearanceChangeReport()
	{

	}

//	/**
//	 * Sets the necessary fields
//	 * @param id The player's ID
//	 * @param appearanceType The players appearance type
//	 */
//	public PlayerAppearanceChangeReport(int id, String appearanceType)
//	{
//		this.playerID = id;
//		this.appearanceType = appearanceType;
//	}
//
//	/**
//	 * @return the players ID
//	 */
//	public int getPlayerID()
//	{
//		return this.playerID;
//	}
//
//	/**
//	 * @return The players appearance type
//	 */
//	public String getAppearanceType()
//	{
//		return this.appearanceType;
//	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
//		PlayerAppearanceChangeReport that = (PlayerAppearanceChangeReport) o;
//		return playerID == that.playerID && Objects.equals(appearanceType, that.appearanceType);
		return true;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash();
	}
}

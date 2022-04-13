package model.reports;

import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;
import model.QualifiedObservableReport;

import java.util.Objects;

/**
 * This report is sent when a player successfully connects to this area server
 *
 * @author Merlin
 *
 */
public final class PlayerConnectionReport implements QualifiedObservableReport
{

	private final int playerID;
	private final String playerName;
	private final String appearanceType;
	private final Position position;
	private final Crew crew;
	private final Major major;
	private final int section;

	/**
	 * Information about a player who has just joined this server
	 *
	 * @param playerID the player's ID
	 * @param playerName the player's name
	 * @param appearanceType the player's appearance type
	 * @param position where the player is standing
	 * @param crew the crew to which the player belongs
	 * @param major the major of this player
	 * @param section the section of the player
	 */
	public PlayerConnectionReport(int playerID, String playerName, String appearanceType, Position position, Crew crew,
								  Major major, int section)
	{
		this.playerID = playerID;
		this.playerName = playerName;
		this.appearanceType = appearanceType;
		this.position = position;
		this.crew = crew;
		this.major = major;
		this.section = section;
	}

	/**
	 * @return the section number
	 */
	public int getSection()
	{
		return section;
	}

	/**
	 * @return the appearance type for this player
	 */
	public String getAppearanceType()
	{
		return appearanceType;
	}

	/**
	 * @return the crew to which this player belongs
	 */
	public Crew getCrew()
	{
		return crew;
	}

	/**
	 * @return the player's unique ID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return the player's name
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * @return the player's major
	 */
	public Major getMajor()
	{
		return major;
	}

	/**
	 * Get this player's position on this area's map
	 *
	 * @return the position
	 */
	public Position getPosition()
	{
		return position;
	}

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
		PlayerConnectionReport that = (PlayerConnectionReport) o;
		return playerID == that.playerID && section == that.section && Objects.equals(playerName, that.playerName) && Objects.equals(appearanceType, that.appearanceType) && Objects.equals(position, that.position) && crew == that.crew && major == that.major;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(playerID, playerName, appearanceType, position, crew, major, section);
	}
}

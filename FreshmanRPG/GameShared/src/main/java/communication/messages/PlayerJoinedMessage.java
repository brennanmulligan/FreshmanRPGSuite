package communication.messages;

import java.io.Serializable;

import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;

/**
 * Sent to all clients when a new player connects to an area server
 *
 * @author Merlin
 *
 */
public class PlayerJoinedMessage implements Message, Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final String playerName;

	private int playerID;
	private String appearanceType;
	private Position position;
	private Crew crew;
	private Major major;
	private int section;

	/**
	 * @param playerID the unique ID of the player
	 * @param playerName the name of the new player
	 * @param position where this player is on the map on this server
	 * @param appearanceType the way the player should be drawn on the screen
	 * @param crew the crew to which this player belongs
	 * @param major of the player
	 * @param section of the player
	 */
	public PlayerJoinedMessage(int playerID, String playerName, String appearanceType, Position position, Crew crew,
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
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appearanceType == null) ? 0 : appearanceType.hashCode());
		result = prime * result + ((crew == null) ? 0 : crew.hashCode());
		result = prime * result + ((major == null) ? 0 : major.hashCode());
		result = prime * result + playerID;
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
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
		PlayerJoinedMessage other = (PlayerJoinedMessage) obj;
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
		if (crew != other.crew)
		{
			return false;
		}
		if (major != other.major)
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
		if (position == null)
		{
			if (other.position != null)
			{
				return false;
			}
		}
		else if (!position.equals(other.position))
		{
			return false;
		}
		return true;
	}

	/**
	 * Get the appearance type that shows how this player wants to be displayed
	 *
	 * @return the appearance type
	 */
	public String getAppearanceType()
	{
		return appearanceType;
	}

	/**
	 * @return the crew this player belongs to
	 */
	public Crew getCrew()
	{
		return crew;
	}

	/**
	 * get this player's unique ID
	 *
	 * @return the player's ID
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
	 * Get the position this player is in
	 *
	 * @return the position
	 */
	public Position getPosition()
	{
		return position;
	}

	/**
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "PlayerJoined Message: playerName = " + playerName;
	}

	/**
	 * @return the major
	 */
	public Major getMajor()
	{
		return major;
	}

	/**
	 * Gets the section of the player
	 * @return section
	 */
	public int getSection()
	{
		return section;
	}
}

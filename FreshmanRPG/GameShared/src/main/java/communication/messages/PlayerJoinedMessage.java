package communication.messages;

import dataDTO.VanityDTO;
import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;
import datatypes.VanityType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Sent to all clients when a new player connects to an area server
 *
 * @author Merlin
 *
 */
public class PlayerJoinedMessage implements Message, Serializable
{
	private static final long serialVersionUID = 1L;
	private final String playerName;
	private int playerID;
	private List<VanityDTO> vanities;
	private List<VanityDTO> ownedItems;
	private Position position;
	private Crew crew;
	private Major major;
	private int section;

	/**
	 * @param playerID the unique ID of the player
	 * @param playerName the name of the new player
	 * @param position where this player is on the map on this server
	 * @param vanities the player's owned vanity items
	 * @param crew the crew to which this player belongs
	 * @param major of the player
	 * @param section of the player
	 */
	public PlayerJoinedMessage(int playerID, String playerName, List<VanityDTO> vanities,
							   Position position, Crew crew, Major major, int section)
	{
		this(playerID, playerName, vanities, position, crew, major, section, new ArrayList<>());
	}

	/**
	 * @param playerID the unique ID of the player
	 * @param playerName the name of the new player
	 * @param position where this player is on the map on this server
	 * @param vanities the player's owned vanity items
	 * @param crew the crew to which this player belongs
	 * @param major of the player
	 * @param section of the player
	 */
	public PlayerJoinedMessage(int playerID, String playerName, List<VanityDTO> vanities,
							   Position position, Crew crew, Major major, int section,
							   List<VanityDTO> ownedItems)
	{
		this.playerID = playerID;
		this.playerName = playerName;
		this.vanities = vanities;
		this.position = position;
		this.crew = crew;
		this.major = major;
		this.section = section;
		this.ownedItems = ownedItems;
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
		PlayerJoinedMessage that = (PlayerJoinedMessage) o;
		return playerID == that.playerID && section == that.section &&
				playerName.equals(that.playerName) && Objects.equals(vanities, that.vanities) &&
				position.equals(that.position) &&
				crew == that.crew && major == that.major;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(playerName, playerID, vanities, position, crew, major, section);
	}

	/**
	 * @return the crew this player belongs to
	 */
	public Crew getCrew()
	{
		return crew;
	}

	/**
	 * @return The list of vanities a player is wearing
	 */
	public List<VanityDTO> getVanities()
	{
		return vanities;
	}

	/**
	 * @return the list of all the vanities a player owns
	 */
	public List<VanityDTO> getAllOwnedItems()
	{
		return ownedItems;
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

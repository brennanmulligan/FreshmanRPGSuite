package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Sent to all clients when a new player connects to an area server
 *
 * @author Merlin
 */
public class PlayerJoinedMessage extends Message implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final String playerName;
    private final List<VanityDTO> vanities;
    private final List<VanityDTO> ownedItems;
    private final Position position;
    private final Crew crew;
    private final Major major;
    private final int section;

    /**
     * @param playerID   the unique ID of the player
     * @param playerName the name of the new player
     * @param position   where this player is on the map on this server
     * @param vanities   the player's owned vanity items
     * @param crew       the crew to which this player belongs
     * @param major      of the player
     * @param section    of the player
     */
    public PlayerJoinedMessage(int playerID, boolean quietMessage, String playerName, List<VanityDTO> vanities,
                               Position position, Crew crew, Major major, int section)
    {
        this(playerID, quietMessage, playerName, vanities, position, crew, major, section, new ArrayList<>());
    }

    /**
     * @param playerID   the unique ID of the player
     * @param playerName the name of the new player
     * @param position   where this player is on the map on this server
     * @param vanities   the player's owned vanity items
     * @param crew       the crew to which this player belongs
     * @param major      of the player
     * @param section    of the player
     */
    public PlayerJoinedMessage(int playerID, boolean quietMessage, String playerName, List<VanityDTO> vanities,
                               Position position, Crew crew, Major major, int section,
                               List<VanityDTO> ownedItems)
    {
        super(playerID, quietMessage);
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

        if (relevantPlayerID != that.relevantPlayerID)
        {
            return false;
        }
        if (section != that.section)
        {
            return false;
        }
        if (!playerName.equals(that.playerName))
        {
            return false;
        }
        if (!vanities.containsAll(that.vanities) || !that.vanities.containsAll(vanities))
        {
            return false;
        }
        if (!ownedItems.containsAll(that.ownedItems) || !that.ownedItems.containsAll(ownedItems))
        {
            return false;
        }
        if (!position.equals(that.position))
        {
            return false;
        }
        if (crew != that.crew)
        {
            return false;
        }
        return major == that.major;
    }

    @Override
    public int hashCode()
    {
        int result = playerName.hashCode();
        result = 31 * result + relevantPlayerID;
        result = 31 * result + vanities.hashCode();
        result = 31 * result + ownedItems.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + crew.hashCode();
        result = 31 * result + major.hashCode();
        result = 31 * result + section;
        return result;
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
     *
     * @return section
     */
    public int getSection()
    {
        return section;
    }
}

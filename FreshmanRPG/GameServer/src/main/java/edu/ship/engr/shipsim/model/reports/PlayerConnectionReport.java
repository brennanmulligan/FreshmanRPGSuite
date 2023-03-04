package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.Position;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This report is sent when a player successfully connects to this area server
 *
 * @author Merlin, Aaron W., Jake H.
 */
public final class PlayerConnectionReport extends SendMessageReport
{

    private final int playerID;
    private final String playerName;
    private final String appearanceType;
    private final Position position;
    private final Crew crew;
    private final Major major;
    private final int section;
    private final ArrayList<VanityDTO> vanityDTOs;
    private final ArrayList<VanityDTO> ownedItems;

    /**
     * Information about a player who has just joined this server
     *
     * @param playerID       the player's ID
     * @param playerName     the player's name
     * @param appearanceType the player's appearance type
     * @param position       where the player is standing
     * @param crew           the crew to which the player belongs
     * @param major          the major of this player
     * @param section        the section of the player
     * @param vanityDTOs     holds info about what the player is wearing
     */
    public PlayerConnectionReport(int playerID, String playerName, String appearanceType, Position position, Crew crew,
                                  Major major, int section, ArrayList<VanityDTO> vanityDTOs)
    {
        this(playerID, playerName, appearanceType, position, crew, major, section, vanityDTOs, new ArrayList<>());
    }

    /**
     * Information about a player who has just joined this server
     *
     * @param playerID       the player's ID
     * @param playerName     the player's name
     * @param appearanceType the player's appearance type
     * @param position       where the player is standing
     * @param crew           the crew to which the player belongs
     * @param major          the major of this player
     * @param section        the section of the player
     * @param vanityDTOs     holds info about what the player is wearing
     */
    public PlayerConnectionReport(int playerID, String playerName, String appearanceType, Position position, Crew crew,
                                  Major major, int section, ArrayList<VanityDTO> vanityDTOs, ArrayList<VanityDTO> ownedItems)
    {
        super(playerID, true);
        this.playerID = playerID;
        this.playerName = playerName;
        this.appearanceType = appearanceType;
        this.position = position;
        this.crew = crew;
        this.major = major;
        this.section = section;
        this.vanityDTOs = vanityDTOs;
        this.ownedItems = ownedItems;
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

    /**
     * @return the dto holding info about what the player is wearing
     */
    public ArrayList<VanityDTO> getVanity()
    {
        return vanityDTOs;
    }

    public ArrayList<VanityDTO> getOwnedItems()
    {
        return ownedItems;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof PlayerConnectionReport that))
        {
            return false;
        }
        return getPlayerID() == that.getPlayerID() &&
                getSection() == that.getSection() &&
                Objects.equals(getPlayerName(), that.getPlayerName()) &&
                Objects.equals(getAppearanceType(),
                        that.getAppearanceType()) &&
                Objects.equals(getPosition(), that.getPosition()) &&
                getCrew() == that.getCrew() && getMajor() == that.getMajor() &&
                Objects.equals(vanityDTOs, that.vanityDTOs) &&
                Objects.equals(getOwnedItems(), that.getOwnedItems()) &&
                this.getRelevantPlayerID() == that.getRelevantPlayerID() &&
                this.isQuiet() == that.isQuiet();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getPlayerID(), getPlayerName(), getAppearanceType(),
                getPosition(), getCrew(), getMajor(), getSection(), vanityDTOs,
                getOwnedItems(), getRelevantPlayerID(), isQuiet());
    }
}

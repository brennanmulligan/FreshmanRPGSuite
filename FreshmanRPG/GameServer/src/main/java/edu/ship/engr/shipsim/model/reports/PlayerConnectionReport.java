package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.Position;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;

/**
 * This report is sent when a player successfully connects to this area server
 *
 * @author Merlin, Aaron W., Jake H.
 */
@EqualsAndHashCode(callSuper = true)
public final class PlayerConnectionReport extends SendMessageReport
{

    @Getter private final int playerID;
    @Getter private final String playerName;
    @Getter private final String appearanceType;
    @Getter private final Position position;
    @Getter private final Crew crew;
    @Getter private final Major major;
    @Getter private final int section;
    @Getter private final ArrayList<VanityDTO> vanity;
    @Getter private final ArrayList<VanityDTO> ownedItems;

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
        this.vanity = vanityDTOs;
        this.ownedItems = ownedItems;
    }
}

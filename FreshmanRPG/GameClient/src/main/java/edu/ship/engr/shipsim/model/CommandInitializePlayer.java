package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Command the puts a new player in the system when that new player joins our
 * area server.
 *
 * @author merlin
 */
public class CommandInitializePlayer extends Command
{

    private final int playerID;
    private final String playerName;
    private final Position position;
    private final Crew crew;
    private final Major major;
    private final int section;
    private final List<VanityDTO> vanities;
    private final List<VanityDTO> ownedItems;

    /**
     * For now, we just know his name
     *
     * @param playerID   the unique player name of the new player
     * @param playerName this player's name
     * @param vanities   the list of all vanity objects the player is wearing
     * @param position   The position of this player
     * @param crew       the crew to which this player belongs
     * @param major      the major to which this player belongs
     * @param section    the section number of the player
     */
    public CommandInitializePlayer(int playerID, String playerName,
                                   List<VanityDTO> vanities, Position position, Crew crew, Major major, int section)
    {
        this(playerID, playerName, vanities, position, crew, major, section, new ArrayList<>());
    }

    /**
     * For now, we just know his name
     *
     * @param playerID   the unique player name of the new player
     * @param playerName this player's name
     * @param vanities   the list of all vanity objects the player is wearing
     * @param position   The position of this player
     * @param crew       the crew to which this player belongs
     * @param major      the major to which this player belongs
     * @param section    the section number of the player
     */
    public CommandInitializePlayer(int playerID, String playerName,
                                   List<VanityDTO> vanities, Position position, Crew crew, Major major, int section, List<VanityDTO> ownedItems)
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

    /**
     * @see Command#execute()
     */
    @Override
    void execute()
    {
        ClientPlayerManager.getSingleton().initializePlayer(playerID, playerName,
                vanities, position, crew, major, section, ownedItems);
    }

    /**
     * @return the crew of the new player
     */
    public Crew getCrew()
    {
        return crew;
    }

    /**
     * @return the id of the new player
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return the new player's name
     */
    public String getPlayerName()
    {
        return playerName;
    }

    /**
     * @return where the new player is standing
     */
    public Position getPosition()
    {
        return position;
    }

    /**
     * @return the major of the player
     */
    public Major getMajor()
    {
        return major;
    }

    /**
     * @return section number of the player
     */
    public int getSection()
    {
        return section;
    }

    /**
     * @return The list of all of the vanities a player has
     */
    public List<VanityDTO> getVanities()
    {
        return vanities;
    }
}

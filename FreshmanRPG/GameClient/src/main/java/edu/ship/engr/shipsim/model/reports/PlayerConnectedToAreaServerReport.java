package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.view.player.PlayerType;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Is sent when the player on this client has completed the connection to an
 * area server (must be sent AFTER the map report has been sent)
 *
 * @author Merlin
 */
@EqualsAndHashCode
public final class PlayerConnectedToAreaServerReport implements Report
{
    private final int playerID;
    private final String playerName;
    private final Position position;
    private final boolean isThisClientsPlayer;
    private final Crew crew;
    private final Major major;
    private final List<VanityDTO> vanities;

    /**
     * @param playerID            the id of the player
     * @param playerName          the name of the player
     * @param position            the position of the player
     * @param crew                the crew to which this player belongs
     * @param major               the major to which this player belongs
     * @param isThisClientsPlayer statement saying if the player connected was the one
     *                            controlled by the client
     * @param vanities            List of all of the Vanities on the player
     * @see PlayerType
     */
    public PlayerConnectedToAreaServerReport(int playerID, String playerName,
                                             Position position, Crew crew,
                                             Major major, boolean isThisClientsPlayer, List<VanityDTO> vanities)
    {
        this.playerID = playerID;
        this.playerName = playerName;
        this.position = position;
        this.isThisClientsPlayer = isThisClientsPlayer;
        this.crew = crew;
        this.major = major;
        this.vanities = vanities;
    }

    /**
     * @return the crew to which this player belongs
     */
    public Crew getCrew()
    {
        return crew;
    }

    /**
     * @return the player id
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * Report the name this player used to login to the system
     *
     * @return the name
     */
    public String getPlayerName()
    {
        return playerName;
    }

    /**
     * @return the player's position
     */
    public Position getPlayerPosition()
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

    public List<VanityDTO> getVanities()
    {
        return vanities;
    }


    /**
     * @return if the player connected was the client's player
     */
    public boolean isThisClientsPlayer()
    {
        return isThisClientsPlayer;
    }

}

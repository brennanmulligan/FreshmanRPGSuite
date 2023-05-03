package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.PlayerManager;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Reports movement of any player playing on this server
 *
 * @author Merlin
 */
@EqualsAndHashCode(callSuper = true)
public final class PlayerMovedReport extends SendMessageReport
{
    @Getter private final Position position;
    @Getter private final int playerID;
    @Getter private final String playerName;
    @Getter private final String mapName;

    /**
     * @param playerID   the ID of the player that moved
     * @param playerName the unique name of the player that moved
     * @param position   the position he moved to
     * @param mapName    the name of the map the player is moving to
     */
    public PlayerMovedReport(int playerID, String playerName, Position position, String mapName)
    {
        super(playerID, !PlayerManager.getSingleton().isNPC(playerID));

        this.position = position;
        this.playerID = playerID;
        this.playerName = playerName;
        this.mapName = mapName;
    }
}

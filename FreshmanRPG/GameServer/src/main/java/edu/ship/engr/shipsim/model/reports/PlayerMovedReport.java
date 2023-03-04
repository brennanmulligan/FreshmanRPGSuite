package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.PlayerManager;

import java.util.Objects;

/**
 * Reports movement of any player playing on this server
 *
 * @author Merlin
 */
public final class PlayerMovedReport extends SendMessageReport
{

    private final Position newPosition;
    private final int playerID;
    private final String playerName;
    private final String mapName;

    /**
     * @param playerID   the ID of the player that moved
     * @param playerName the unique name of the player that moved
     * @param position   the position he moved to
     * @param mapName    the name of the map the player is moving to
     */
    public PlayerMovedReport(int playerID, String playerName, Position position, String mapName)
    {
        super(playerID, !PlayerManager.getSingleton().isNPC(playerID));
        newPosition = position;
        this.playerID = playerID;
        this.playerName = playerName;
        this.mapName = mapName;
    }

    /**
     * @return the map the player moved on
     */
    public String getMapName()
    {
        return mapName;
    }

    /**
     * @return the newPosition
     */
    public Position getNewPosition()
    {
        return newPosition;
    }

    /**
     * @return the playerID
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * Get the players unique name
     *
     * @return the name
     */
    public String getPlayerName()
    {
        return playerName;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof PlayerMovedReport that))
        {
            return false;
        }

        return getPlayerID() == that.getPlayerID() &&
                Objects.equals(getNewPosition(), that.getNewPosition()) &&
                Objects.equals(getPlayerName(), that.getPlayerName()) &&
                Objects.equals(getMapName(), that.getMapName()) &&
                Objects.equals(getRelevantPlayerID(), that.getRelevantPlayerID()) &&
                Objects.equals(isQuiet(), that.isQuiet());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getNewPosition(), getPlayerID(), getPlayerName(),
                getMapName(), getRelevantPlayerID(), isQuiet());
    }
}

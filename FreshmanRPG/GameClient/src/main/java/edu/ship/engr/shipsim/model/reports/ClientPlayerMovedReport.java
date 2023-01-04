package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.Report;

import java.util.Objects;

/**
 * Reports movement of the player playing on this client
 *
 * @author Matt Kujawski
 */
public final class ClientPlayerMovedReport implements Report
{
    private final int playerID;
    private final Position thePosition;
    private final boolean thisClientsPlayer;

    /**
     * @return true if this client's player is the one who moved
     */
    public boolean isThisClientsPlayer()
    {
        return thisClientsPlayer;
    }

    /**
     * @param playerID          the unique identifier of the player moving
     * @param position          the position he moved to
     * @param thisClientsPlayer true if the player moving is the one running this client
     */
    public ClientPlayerMovedReport(int playerID, Position position, boolean thisClientsPlayer)
    {
        thePosition = position;
        this.playerID = playerID;
        this.thisClientsPlayer = thisClientsPlayer;
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
        ClientPlayerMovedReport that = (ClientPlayerMovedReport) o;
        return playerID == that.playerID && thisClientsPlayer == that.thisClientsPlayer && Objects.equals(thePosition, that.thePosition);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(playerID, thePosition, thisClientsPlayer);
    }

    /**
     * @return the id of the player that is moving
     */
    public int getID()
    {
        return playerID;
    }

    /**
     * @return where the player moved to
     */
    public Position getNewPosition()
    {
        return thePosition;
    }
}

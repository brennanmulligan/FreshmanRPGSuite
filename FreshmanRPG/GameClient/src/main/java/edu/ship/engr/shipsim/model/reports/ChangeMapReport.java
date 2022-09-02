package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;

import java.util.Objects;

/**
 * This is the ChangeMapReport contains information for connecting to a server.
 *
 * @author Steve
 */
public final class ChangeMapReport implements QualifiedObservableReport
{
    private final int playerID;
    private final Position position;
    private final String mapName;


    /**
     * @param playerID the ID of the player teleporting
     * @param position The position to connect to
     * @param mapName  is the Map the player is going to
     */
    public ChangeMapReport(int playerID, Position position, String mapName)
    {
        this.playerID = playerID;
        this.position = position;
        this.mapName = mapName;
    }

    /**
     * @return the Id of the player teleporting
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return the position to connect to
     */
    public Position getPosition()
    {
        return position;
    }

    /**
     * @return The map the player is going to
     */
    public String getMapName()
    {
        return mapName;
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
        ChangeMapReport that = (ChangeMapReport) o;
        return playerID == that.playerID && Objects.equals(position, that.position) && Objects.equals(mapName, that.mapName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(playerID, position, mapName);
    }
}

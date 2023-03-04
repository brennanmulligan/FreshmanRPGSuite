package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.Position;

import java.util.Objects;

/**
 * This is the ChangeMapReport contains information for connecting to a server.
 *
 * @author Steve
 */
public final class ChangeMapReport extends SendMessageReport
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
        // Happens on client, thus it will always be loud
        super(0, false);

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
        if (!(o instanceof ChangeMapReport that))
        {
            return false;
        }
        return getPlayerID() == that.getPlayerID() &&
                Objects.equals(getPosition(), that.getPosition()) &&
                Objects.equals(getMapName(), that.getMapName()) &&
                this.getRelevantPlayerID() == that.getRelevantPlayerID() &&
                this.isQuiet() == that.isQuiet();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getPlayerID(), getPosition(), getMapName(), getRelevantPlayerID(), isQuiet());
    }
}

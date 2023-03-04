package edu.ship.engr.shipsim.model.reports;

import java.util.Objects;

/**
 * @author nhydock
 */
public class AreaCollisionReport extends SendMessageReport
{
    private final int playerID;
    private final String areaName;

    /**
     * @param playerID The player who moved
     * @param areaName The name of the area the player has stepped into
     */
    public AreaCollisionReport(int playerID, String areaName)
    {
        super(playerID, true);
        this.playerID = playerID;
        this.areaName = areaName;
    }

    /**
     * @return the player who moved
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return the position to which the player moved
     */
    public String getAreaName()
    {
        return areaName;
    }

    @Override
    public final boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof AreaCollisionReport that))
        {
            return false;
        }
        return getPlayerID() == that.getPlayerID() &&
                Objects.equals(getAreaName(), that.getAreaName()) &&
                this.getRelevantPlayerID() == that.getRelevantPlayerID() &&
                this.isQuiet() == that.isQuiet();
    }

    @Override
    public final int hashCode()
    {
        return Objects.hash(getPlayerID(), getAreaName(), getRelevantPlayerID(), isQuiet());
    }
}

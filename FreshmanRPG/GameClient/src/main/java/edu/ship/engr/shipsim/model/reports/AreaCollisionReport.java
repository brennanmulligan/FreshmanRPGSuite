package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.QualifiedObservableReport;

/**
 * @author nhydock
 */
public class AreaCollisionReport implements QualifiedObservableReport
{
    private final int playerID;
    private final String areaName;

    /**
     * @param playerID The player who moved
     * @param areaName The name of the area the player has stepped into
     */
    public AreaCollisionReport(int playerID, String areaName)
    {
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
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((areaName == null) ? 0 : areaName.hashCode());
        result = prime * result + playerID;
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof AreaCollisionReport))
        {
            return false;
        }
        AreaCollisionReport other = (AreaCollisionReport) obj;
        if (areaName == null)
        {
            if (other.areaName != null)
            {
                return false;
            }
        }
        else if (!areaName.equals(other.areaName))
        {
            return false;
        }
        if (playerID != other.playerID)
        {
            return false;
        }
        return true;
    }

    /**
     * @return the position to which the player moved
     */
    public String getAreaName()
    {
        return areaName;
    }
}

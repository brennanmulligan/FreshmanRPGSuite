package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

/**
 * Is sent when the player on the same area server as the client player has
 * disconnected and we need to remove them from our display
 *
 * @author nhydock
 */
public class PlayerDisconnectedFromAreaServerReport implements
        Report
{

    private int playerID;

    /**
     * @param id the id of the player
     */
    public PlayerDisconnectedFromAreaServerReport(int id)
    {
        this.playerID = id;
    }

    /**
     * @return the player id
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        PlayerDisconnectedFromAreaServerReport other = (PlayerDisconnectedFromAreaServerReport) obj;
        boolean result = true;
        result = result && this.getPlayerID() == other.getPlayerID();
        return result;
    }
}

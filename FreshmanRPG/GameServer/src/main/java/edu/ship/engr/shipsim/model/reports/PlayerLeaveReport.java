package edu.ship.engr.shipsim.model.reports;

/**
 * Is created when the player on the server has disconnected and we need to
 * inform the clients that they need to stop watching the player
 *
 * @author nhydock
 */
public class PlayerLeaveReport extends SendMessageReport
{
    private int playerID;

    /**
     * @param id the id of the player
     */
    public PlayerLeaveReport(int id)
    {
        super(id, true);
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
        PlayerLeaveReport other = (PlayerLeaveReport) obj;
        boolean result = true;
        result = result && this.getPlayerID() == other.getPlayerID();
        return result;
    }
}

package edu.ship.engr.shipsim.model.reports;

/**
 * Reports that a player tried to connect with an invalid pin
 *
 * @author Matt and Andy
 */
public final class PinFailedReport extends SendMessageReport
{

    private final int playerID;

    /**
     * @param playerID the unique ID of the player who tried to connect
     *                 incorrectly
     */
    public PinFailedReport(int playerID)
    {
        super(playerID, true);
        this.playerID = playerID;
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
        PinFailedReport other = (PinFailedReport) obj;
        if (playerID != other.playerID)
        {
            return false;
        }
        return true;
    }

    /**
     * @return the ID of the player involved
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + playerID;
        return result;
    }

    /**
     * @return the kind of pin error message this is
     */
    public String toString()
    {
        return "Pin failed report for player #" + playerID;
    }

}

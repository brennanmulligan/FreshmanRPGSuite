package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.QualifiedObservableReport;

/**
 * @author Matthew Croft
 */
public final class DoubloonChangeReport implements QualifiedObservableReport
{

    /**
     *
     */
    public final int doubloons;

    private final int playerID;

    private final int buffPool;


    /**
     * @param playerID  of the current player
     * @param doubloons of the player
     * @param buffPool  the current value of the players remaining bonus points
     */
    public DoubloonChangeReport(int playerID, int doubloons, int buffPool)
    {
        this.playerID = playerID;
        this.doubloons = doubloons;
        this.buffPool = buffPool;
    }

    /**
     * @return doubloons
     */
    public int getDoubloons()
    {
        return doubloons;
    }

    /**
     * @return playerID
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * gets buff pool
     *
     * @return buffPool
     */
    public int getBuffPool()
    {
        return this.buffPool;
    }


    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + doubloons;
        result = prime * result + playerID;
        result = prime * result + this.buffPool;
        return result;
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
        DoubloonChangeReport other = (DoubloonChangeReport) obj;
        if (doubloons != other.doubloons)
        {
            return false;
        }
        if (this.buffPool != other.getBuffPool())
        {
            return false;
        }
        if (playerID != other.playerID)
        {
            return false;
        }
        return true;
    }

}

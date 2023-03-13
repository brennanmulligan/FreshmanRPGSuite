package edu.ship.engr.shipsim.model.reports;

import java.util.Objects;

/**
 * @author Matthew Croft
 */
public final class DoubloonChangeReport extends SendMessageReport
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
        super(playerID, true);
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


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof DoubloonChangeReport that))
        {
            return false;
        }
        return getDoubloons() == that.getDoubloons() &&
                getPlayerID() == that.getPlayerID() &&
                getBuffPool() == that.getBuffPool() &&
                this.getRelevantPlayerID() == that.getRelevantPlayerID() &&
                this.isQuiet() == that.isQuiet();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getDoubloons(), getPlayerID(), getBuffPool(), getRelevantPlayerID(), isQuiet());
    }
}

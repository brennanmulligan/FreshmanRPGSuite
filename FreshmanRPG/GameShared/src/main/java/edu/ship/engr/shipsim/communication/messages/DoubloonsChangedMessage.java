package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * @author Matthew Croft
 * @author Evan Stevenson
 */
public class DoubloonsChangedMessage implements Message, Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int doubloons;

    private int playerID;

    private int buffPool;


    /**
     * @param playerID  of the current player
     * @param doubloons the player has
     * @param playerID  of the current player
     * @param buffPool  - the player's current bonuspoint pool
     */
    public DoubloonsChangedMessage(int playerID, int doubloons, int buffPool)
    {
        this.doubloons = doubloons;
        this.playerID = playerID;
        this.buffPool = buffPool;
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

        DoubloonsChangedMessage that = (DoubloonsChangedMessage) o;

        if (doubloons != that.doubloons)
        {
            return false;
        }
        if (playerID != that.playerID)
        {
            return false;
        }
        return buffPool == that.buffPool;
    }

    @Override
    public int hashCode()
    {
        int result = doubloons;
        result = 31 * result + playerID;
        result = 31 * result + buffPool;
        return result;
    }

    /**
     * @return doubloons
     */
    public int getDoubloons()
    {
        return doubloons;
    }

    /**
     * @return the playerID
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return the buffPool
     */
    public int getBuffPool()
    {
        return buffPool;
    }
}

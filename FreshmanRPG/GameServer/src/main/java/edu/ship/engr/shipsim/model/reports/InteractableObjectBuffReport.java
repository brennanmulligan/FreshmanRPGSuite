package edu.ship.engr.shipsim.model.reports;

/**
 * @author Adam Pine, Jacob Knight
 */
public class InteractableObjectBuffReport extends SendMessageReport
{
    private final int playerID;
    private final int experiencePointPool;

    /**
     * Constructor
     *
     * @param playerID            - int
     * @param experiencePointPool - int
     */
    public InteractableObjectBuffReport(int playerID, int experiencePointPool)
    {
        super(playerID, true);
        this.playerID = playerID;
        this.experiencePointPool = experiencePointPool;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + experiencePointPool;
        result = prime * result + playerID;
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
        InteractableObjectBuffReport other = (InteractableObjectBuffReport) obj;
        if (experiencePointPool != other.experiencePointPool)
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
     * @return playerID - int
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return the expPointPool for the buff
     */
    public int getExpPointPool()
    {
        return experiencePointPool;
    }
}

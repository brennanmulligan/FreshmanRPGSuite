package edu.ship.engr.shipsim.model.reports;

/**
 * Created after a player gets saved to the database.
 */
public class PlayerReadyToTeleportReport extends SendMessageReport
{

    private int playerID = 0;
    private String mapName;

    /**
     * @param playerID The id of the player that got saved.
     * @param mapName      The map that the player got saved to.
     */
    public PlayerReadyToTeleportReport(int playerID, String mapName)
    {
        super(playerID, true);
        this.playerID = playerID;
        this.mapName = mapName;
    }

    /**
     * @return The player id.
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return The map that the player is on.
     */
    public String getMapName()
    {
        return mapName;
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
        result = prime * result + ((mapName == null) ? 0 : mapName.hashCode());
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
        PlayerReadyToTeleportReport other = (PlayerReadyToTeleportReport) obj;
        if (playerID != other.playerID)
        {
            return false;
        }
        if (mapName == null)
        {
            if (other.mapName != null)
            {
                return false;
            }
        }
        else if (!mapName.equals(other.mapName))
        {
            return false;
        }
        return true;
    }

}

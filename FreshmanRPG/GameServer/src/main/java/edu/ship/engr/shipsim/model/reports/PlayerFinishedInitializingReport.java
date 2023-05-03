package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

/**
 * This report is sent when a player is finished being initialized.
 *
 * @author Brad Olah
 */
public final class PlayerFinishedInitializingReport implements Report
{
    private final int playerID;
    private final String playerName;
    private final String appearanceType;

    /**
     * Information about which player finished loading.
     *
     * @param playerID         the player's ID
     * @param playerName       the player's name
     * @param appearanceType the player's appearance type
     */
    public PlayerFinishedInitializingReport(int playerID, String playerName, String appearanceType)
    {
        this.playerID = playerID;
        this.playerName = playerName;
        this.appearanceType = appearanceType;
    }

    /**
     * @return the players ID
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return the players name
     */
    public String getPlayerName()
    {
        return playerName;
    }

    /**
     * @return the players appearance
     */
    public String getAppearanceType()
    {
        return appearanceType;
    }

    /**
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((
                appearanceType == null) ? 0 : appearanceType.hashCode());
        result = prime * result + playerID;
        result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
        return result;
    }

    /**
     * (non-Javadoc)
     *
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
        PlayerFinishedInitializingReport other = (PlayerFinishedInitializingReport) obj;
        if (appearanceType == null)
        {
            if (other.appearanceType != null)
            {
                return false;
            }
        }
        else if (!appearanceType.equals(other.appearanceType))
        {
            return false;
        }
        if (playerID != other.playerID)
        {
            return false;
        }
        if (playerName == null)
        {
            if (other.playerName != null)
            {
                return false;
            }
        }
        else if (!playerName.equals(other.playerName))
        {
            return false;
        }
        return true;
    }
}

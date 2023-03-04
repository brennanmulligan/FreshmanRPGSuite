package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;

import java.util.List;

/**
 * Report that a player's appearance has been changed
 *
 * @author merlin
 */
public class ChangePlayerAppearanceReport extends SendMessageReport
{

    private int playerID;
    private List<VanityDTO> vanities;

    /**
     * @param playerID the player's unique id
     * @param vanities the list of all vanity objects the player is wearing
     */
    public ChangePlayerAppearanceReport(int playerID, List<VanityDTO> vanities)
    {
        // Happens on client, thus it will always be loud
        super(0, false);

        this.playerID = playerID;
        this.vanities = vanities;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((vanities == null) ? 0 : vanities.hashCode());
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
        ChangePlayerAppearanceReport other = (ChangePlayerAppearanceReport) obj;
        if (vanities == null)
        {
            if (other.vanities != null)
            {
                return false;
            }
        }
        else if (!vanities.equals(other.vanities))
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
     * @return the ID of the player being redrawn
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return The list of all of the vanities a player has
     */
    public List<VanityDTO> getVanities()
    {
        return vanities;
    }
}

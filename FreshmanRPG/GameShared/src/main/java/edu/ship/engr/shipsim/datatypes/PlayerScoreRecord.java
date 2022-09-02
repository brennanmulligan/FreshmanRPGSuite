package edu.ship.engr.shipsim.datatypes;

import java.io.Serializable;

/**
 * A data transfer object that holds information about a player's score. Used
 * for the high score list. Comparability is based on the value of the
 * experiences so records can be sorted
 *
 * @author Merlin
 */
public class PlayerScoreRecord implements Comparable<PlayerScoreRecord>, Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String playerName;
    private int experiencePoints;
    private String crewName;
    private int section;

    /**
     * @param playerName       the player's name
     * @param experiencePoints the player's experience points
     */
    public PlayerScoreRecord(String playerName, int experiencePoints)
    {
        super();
        this.playerName = playerName;
        this.experiencePoints = experiencePoints;
    }

    /**
     * @param playerName       the player's name
     * @param experiencePoints the player's experience points
     * @param crewName         the name of the crew this player belongs to
     */
    public PlayerScoreRecord(String playerName, int experiencePoints, String crewName)
    {
        this(playerName, experiencePoints);
        this.crewName = crewName;
    }

    /**
     * @param playerName       the player's name
     * @param experiencePoints the player's experience points
     * @param crewName         the name of the crew this player belongs to
     * @param section          the section this player belongs to
     */
    public PlayerScoreRecord(String playerName, int experiencePoints, String crewName, int section)
    {
        this(playerName, experiencePoints, crewName);
        this.section = section;
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(PlayerScoreRecord o)
    {
        if (o.getExperiencePoints() < this.getExperiencePoints())
        {
            return -1;
        }
        else if (o.getExperiencePoints() == this.getExperiencePoints())
        {
            return 0;
        }
        return 1;
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
        PlayerScoreRecord other = (PlayerScoreRecord) obj;
        if (experiencePoints != other.experiencePoints)
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

    /**
     * @return the name of the crew this player belongs to
     */
    public String getCrewName()
    {
        return crewName;
    }

    /**
     * @return the player's experience points
     */
    public int getExperiencePoints()
    {
        return experiencePoints;
    }

    /**
     * @return the player's name
     */
    public String getPlayerName()
    {
        return playerName;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + experiencePoints;
        result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
        return result;
    }

    /**
     * @return the player's section
     */
    public int getSection()
    {
        return section;
    }

}

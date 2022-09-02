package edu.ship.engr.shipsim.criteria;

import edu.ship.engr.shipsim.datatypes.Position;

/**
 * Contains information about a particular place in the game
 *
 * @author Merlin
 */
public class GameLocationDTO implements ObjectiveCompletionCriteria, QuestCompletionActionParameter
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String mapName;

    private Position position;

    /**
     * Stores one place in the game
     *
     * @param mapName  the map containing the place
     * @param position the position with that map of the place
     */
    public GameLocationDTO(String mapName, Position position)
    {
        this.mapName = mapName;
        this.position = position;
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
        GameLocationDTO other = (GameLocationDTO) obj;
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
        if (position == null)
        {
            if (other.position != null)
            {
                return false;
            }
        }
        else if (!position.equals(other.position))
        {
            return false;
        }
        return true;
    }

    /**
     * @return the name of the map on which this position resides
     */
    public String getMapName()
    {
        return mapName;
    }

    /**
     * @return the coordinates on the given map of this location
     */
    public Position getPosition()
    {
        return position;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mapName == null) ? 0 : mapName.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Criteria Game Location: " + this.position.toString();
    }
}

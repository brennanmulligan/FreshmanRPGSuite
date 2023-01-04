package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datasource.LevelRecord;
import edu.ship.engr.shipsim.model.Report;

/**
 * Report that contains the player's experience points and a level record
 * which contains the level description and the number of points until next
 * level.
 *
 * @author nk3668
 */
public final class ExperiencePointsChangeReport implements Report
{

    private final int exp;
    private final LevelRecord rec;

    /**
     * Constructor
     *
     * @param exp experience points
     * @param rec level record
     */
    public ExperiencePointsChangeReport(int exp, LevelRecord rec)
    {
        this.exp = exp;
        this.rec = rec;
    }

    /**
     * Getter for level record
     *
     * @return level record
     */
    public LevelRecord getLevelRecord()
    {
        return rec;
    }

    /**
     * Getter for current experience points
     *
     * @return experience points
     */
    public int getExperiencePoints()
    {
        return exp;
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
        result = prime * result + exp;
        result = prime * result + ((rec == null) ? 0 : rec.hashCode());
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
        if (!(obj instanceof ExperiencePointsChangeReport))
        {
            return false;
        }

        ExperiencePointsChangeReport other = (ExperiencePointsChangeReport) obj;
        if (exp != other.exp)
        {
            return false;
        }
        if (rec == null)
        {
            if (other.rec != null)
            {
                return false;
            }
        }
        else if (!rec.equals(other.rec))
        {
            return false;
        }
        return true;
    }


}

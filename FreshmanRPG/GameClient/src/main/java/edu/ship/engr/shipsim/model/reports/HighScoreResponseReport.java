package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.PlayerScoreRecord;
import edu.ship.engr.shipsim.model.Report;

import java.util.ArrayList;

/**
 * @author nk3668
 */
public final class HighScoreResponseReport implements Report
{
    private final ArrayList<PlayerScoreRecord> scores = new ArrayList<>();

    /**
     * @param list aweiofj
     */
    public HighScoreResponseReport(ArrayList<PlayerScoreRecord> list)
    {
        for (PlayerScoreRecord r : list)
        {
            scores.add(r);
        }
    }

    /**
     * Returns score list
     *
     * @return score list
     */
    public ArrayList<PlayerScoreRecord> getScoreList()
    {
        return scores;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((scores == null) ? 0 : scores.hashCode());
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
        if (!(obj instanceof HighScoreResponseReport))
        {
            return false;
        }
        HighScoreResponseReport other = (HighScoreResponseReport) obj;
        if (scores == null)
        {
            if (other.scores != null)
            {
                return false;
            }
        }
        else if (!scores.equals(other.scores))
        {
            return false;
        }
        return true;
    }
}

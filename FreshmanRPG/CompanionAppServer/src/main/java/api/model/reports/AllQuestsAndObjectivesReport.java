package api.model.reports;

import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.QuestRecord;

import java.util.ArrayList;

/**
 * A report containing a list of quests and objective. Assumes that the sender puts all of
 * the quests and objectives into the list
 *
 * @author Darnell Martin & Darin Alleman
 */
public class AllQuestsAndObjectivesReport implements Report
{

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((list == null) ? 0 : list.hashCode());
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
        AllQuestsAndObjectivesReport other = (AllQuestsAndObjectivesReport) obj;
        if (list == null)
        {
            return other.list == null;
        }
        else
        {
            return list.equals(other.list);
        }
    }

    private final ArrayList<QuestRecord> list;

    /**
     * @param list the quests
     */
    public AllQuestsAndObjectivesReport(ArrayList<QuestRecord> list)
    {
        this.list = list;
    }

    /**
     * @return the quests
     */
    public ArrayList<QuestRecord> getQuestInfo()
    {
        return list;
    }

}

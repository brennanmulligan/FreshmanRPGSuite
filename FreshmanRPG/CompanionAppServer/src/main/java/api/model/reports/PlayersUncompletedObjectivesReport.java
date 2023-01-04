package api.model.reports;

import edu.ship.engr.shipsim.model.ObjectiveRecord;
import edu.ship.engr.shipsim.model.Report;

import java.util.ArrayList;

/**
 * @author Scott Bowling
 */
public class PlayersUncompletedObjectivesReport implements Report
{

    private final ArrayList<ObjectiveRecord> list;


    /**
     * @param list of Objectives
     */
    public PlayersUncompletedObjectivesReport(ArrayList<ObjectiveRecord> list)
    {
        this.list = list;
    }

    /**
     * @return the list of objectives
     */
    public ArrayList<ObjectiveRecord> getAllUncompletedObjectives()
    {
        return list;
    }

}

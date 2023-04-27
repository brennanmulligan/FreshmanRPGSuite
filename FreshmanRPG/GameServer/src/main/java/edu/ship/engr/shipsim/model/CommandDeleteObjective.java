package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ObjectiveRowDataGateway;

import edu.ship.engr.shipsim.model.reports.ObjectiveDeletedReport;


public class CommandDeleteObjective extends Command
{
    private final int objectiveID;
    private final int questID;

    public CommandDeleteObjective(int objectiveID, int questID)
    {
        this.objectiveID = objectiveID;
        this.questID = questID;
    }

    @Override
    void execute()
    {
        try
        {
            ObjectiveRowDataGateway objectiveRowDataGateway = new ObjectiveRowDataGateway(objectiveID, questID);
            objectiveRowDataGateway.remove();
            ReportObserverConnector.getSingleton().sendReport(new ObjectiveDeletedReport(true));
        }
        catch(DatabaseException e)
        {
            e.printStackTrace();
        }
    }
}

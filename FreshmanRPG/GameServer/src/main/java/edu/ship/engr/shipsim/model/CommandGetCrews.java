package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.CrewDTO;
import edu.ship.engr.shipsim.datasource.CrewTableDataGateway;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.model.reports.GetAllCrewsReport;

import java.util.ArrayList;

public class CommandGetCrews extends Command
{
    public CommandGetCrews()
    {
    }
    @Override
    void execute()
    {
        CrewTableDataGateway gw = CrewTableDataGateway.getSingleton();

        try
        {
            ArrayList<CrewDTO> crewDTOs = gw.getAllCrews();
            GetAllCrewsReport crewsReport = new GetAllCrewsReport(crewDTOs);
            ReportObserverConnector.getSingleton().sendReport(crewsReport);
        }
        catch (DatabaseException e)
        {
            throw new RuntimeException(e);
        }
    }
}

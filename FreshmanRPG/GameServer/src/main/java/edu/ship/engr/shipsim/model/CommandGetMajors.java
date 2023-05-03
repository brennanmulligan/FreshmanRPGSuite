package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.MajorDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.MajorTableDataGateway;
import edu.ship.engr.shipsim.model.reports.GetAllMajorsReport;

import java.util.ArrayList;

public class CommandGetMajors extends Command
{
    public CommandGetMajors()
    {
    }
    @Override
    void execute()
    {
        MajorTableDataGateway gw = MajorTableDataGateway.getSingleton();

        try
        {
            ArrayList<MajorDTO> majorDTOs = gw.getAllMajors();
            GetAllMajorsReport majorsReport = new GetAllMajorsReport(majorDTOs);
            ReportObserverConnector.getSingleton().sendReport(majorsReport);
        }
        catch (DatabaseException e)
        {
            throw new RuntimeException(e);
        }
    }
}

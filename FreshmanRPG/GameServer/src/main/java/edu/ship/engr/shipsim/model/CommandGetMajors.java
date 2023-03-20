package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.CrewDTO;
import edu.ship.engr.shipsim.dataDTO.MajorDTO;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.model.reports.GetAllCrewsReport;
import edu.ship.engr.shipsim.model.reports.GetAllMajorsReport;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CommandGetMajors extends Command
{
    public CommandGetMajors()
    {
    }
    @Override
    void execute()
    {
        ArrayList<MajorDTO> majorDTOs = new ArrayList<>();
        ArrayList<Major> majors = Major.getAllMajors();

        for (Major major : majors)
        {
            majorDTOs.add(new MajorDTO(major.getID(),
                    major.getMajorName()));
        }

        GetAllMajorsReport majorsReport = new GetAllMajorsReport(majorDTOs);
        ReportObserverConnector.getSingleton().sendReport(majorsReport);
    }
}

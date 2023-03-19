package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.CrewDTO;
import edu.ship.engr.shipsim.dataDTO.MajorDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.model.reports.GetAllCrewsReport;
import edu.ship.engr.shipsim.model.reports.GetAllMajorsReport;

import java.util.ArrayList;

public class CommandGetMajorsCrews extends Command
{
    public CommandGetMajorsCrews()
    {
    }
    @Override
    void execute()
    {
        ArrayList<MajorDTO> majorDTOs = null;
        ArrayList<CrewDTO> crewDTOs = null;
        ArrayList<Major> majors = Major.getAllMajors();
        ArrayList<Crew> crews = Crew.getAllCrews();

        for(int i = 0; i < majors.size(); i++)
        {
            majorDTOs.set(i, new MajorDTO(majors.get(i).getID(),
                    majors.get(i).getMajorName()));
        }
        for(int i = 0; i < crews.size(); i++)
        {
            crewDTOs.set(i, new CrewDTO(crews.get(i).getID(),
                    crews.get(i).getCrewName()));
        }

        GetAllMajorsReport majorsReport = new GetAllMajorsReport(majorDTOs);
        ReportObserverConnector.getSingleton().sendReport(majorsReport);

        GetAllCrewsReport crewsReport = new GetAllCrewsReport(crewDTOs);
        ReportObserverConnector.getSingleton().sendReport(crewsReport);
    }
}

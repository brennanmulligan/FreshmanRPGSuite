package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.CrewDTO;
import edu.ship.engr.shipsim.dataDTO.MajorDTO;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.model.reports.GetAllCrewsReport;
import edu.ship.engr.shipsim.model.reports.GetAllMajorsReport;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CommandGetCrews extends Command
{
    public CommandGetCrews()
    {
    }
    @Override
    void execute()
    {
        ArrayList<CrewDTO> crewDTOs = new ArrayList<>();
        ArrayList<Crew> crews = Crew.getAllCrews();

        for (Crew crew : crews)
        {
            crewDTOs.add(new CrewDTO(crew.getID(),
                    crew.getCrewName()));
        }

        GetAllCrewsReport crewsReport = new GetAllCrewsReport(crewDTOs);
        ReportObserverConnector.getSingleton().sendReport(crewsReport);
    }
}

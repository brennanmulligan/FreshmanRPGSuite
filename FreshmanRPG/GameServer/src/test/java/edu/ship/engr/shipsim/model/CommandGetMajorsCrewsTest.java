package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.MajorDTO;
import edu.ship.engr.shipsim.dataDTO.CrewDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.model.reports.GetAllMajorsReport;
import edu.ship.engr.shipsim.model.reports.GetAllCrewsReport;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CommandGetMajorsCrewsTest
{
    @Test
    public void testGetAllMajors()
    {
        ReportObserver obs = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton().registerObserver(obs, GetAllMajorsReport.class);

        //TODO When the gateways are made next iteration, this will be used.
        //MajorGateway majorGateway = MajorGateway.getSingleton();
        //MajorGateway mockMajorGateway = mock(MajorGateway.class);
        //majorGateway.setSingleton(mockMajorGateway);

        MajorDTO majorDTO1 = new MajorDTO();
        majorDTO1.setName("SOFTWARE_ENGINEERING");
        MajorDTO majorDTO2 = new MajorDTO();
        majorDTO2.setName("ELECTRICAL_ENGINEERING");

        CrewDTO crewDTO1 = new CrewDTO();
        crewDTO1.setCrewID(1);
        CrewDTO crewDTO2 = new CrewDTO();
        crewDTO2.setCrewID(2);

        ArrayList<MajorDTO> majors = new ArrayList<>()
        {{
            add(majorDTO1);
            add(majorDTO2);
        }};

        ArrayList<CrewDTO> crews = new ArrayList<>()
        {{
            add(crewDTO1);
            add(crewDTO2);
        }};

        //when(MajorGateway.getAllMajors()).thenReturn(majors);
        //when(CrewGateway.getAllCrews()).thenReturn(crews);

        CommandGetMajorsCrews command = new CommandGetMajorsCrews();
        command.execute();

        verify(obs, times(1)).receiveReport(new GetAllMajorsReport(majors));
        verify(obs, times(1)).receiveReport(new GetAllCrewsReport(crews));
    }
}
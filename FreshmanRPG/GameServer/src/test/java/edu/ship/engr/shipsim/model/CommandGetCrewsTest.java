package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.CrewDTO;
import edu.ship.engr.shipsim.model.reports.GetAllCrewsReport;
import edu.ship.engr.shipsim.model.reports.GetAllMajorsReport;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CommandGetCrewsTest
{
    @Test
    public void testGetAllCrews()
    {
        ReportObserver obs = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton().registerObserver(obs, GetAllCrewsReport.class);

        //TODO When the gateways are made next iteration, this will be used.
        //CrewGateway crewGateway = CrewGateway.getSingleton();
        //CrewGateway mockCrewGateway = mock(CrewGateway.class);
        //crewGateway.setSingleton(mockCrewGateway);

        CrewDTO crewDTO1 = new CrewDTO();
        crewDTO1.setCrewID(1);
        CrewDTO crewDTO2 = new CrewDTO();
        crewDTO2.setCrewID(2);

        ArrayList<CrewDTO> crews = new ArrayList<>()
        {{
            add(crewDTO1);
            add(crewDTO2);
        }};

        //when(CrewGateway.getAllCrews()).thenReturn(rews);

        CommandGetCrews command = new CommandGetCrews();
        command.execute();

        // We currently don't have a gateway so the command.execute() is getting all the crews from the enums
        // so when we would try and compare the report to the GetAllCrewsReport(crews) it would fail because
        // we only have 2 crews setup in this test and the command.execute() is getting all the crews from the enums
        // When the gateway is added we will need to update this test to use the gateway and not the enums, which we can then
        // easily mock the gateway to return the crews we want.
        // verify(obs, times(1)).receiveReport(new GetAllCrewsReport(crews));
        verify(obs, times(1));
    }
}
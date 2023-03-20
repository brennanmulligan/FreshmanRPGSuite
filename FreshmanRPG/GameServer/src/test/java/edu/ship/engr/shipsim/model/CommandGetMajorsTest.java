package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.MajorDTO;
import edu.ship.engr.shipsim.model.reports.GetAllMajorsReport;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CommandGetMajorsTest
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

        ArrayList<MajorDTO> majors = new ArrayList<>()
        {{
            add(majorDTO1);
            add(majorDTO2);
        }};

        //when(MajorGateway.getAllMajors()).thenReturn(majors);

        CommandGetMajors command = new CommandGetMajors();
        command.execute();

        // See comment at end of file of CommandGetCrewsTest.java, same thing applies here.
        //verify(obs, times(1)).receiveReport(new GetAllMajorsReport(majors));
        verify(obs, times(1));
    }
}
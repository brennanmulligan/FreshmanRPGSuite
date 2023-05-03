package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.MajorDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.MajorTableDataGateway;
import edu.ship.engr.shipsim.model.reports.GetAllMajorsReport;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CommandGetMajorsTest
{
    @Test
    public void testGetAllMajors() throws DatabaseException
    {
        ReportObserver obs = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton().registerObserver(obs,
                GetAllMajorsReport.class);

        MajorTableDataGateway majorGateway = MajorTableDataGateway.getSingleton();
        MajorTableDataGateway mockMajorGateway = mock(MajorTableDataGateway.class);
        majorGateway.setSingleton(mockMajorGateway);

        MajorDTO majorDTO1 = new MajorDTO();
        majorDTO1.setID(1);
        MajorDTO majorDTO2 = new MajorDTO();
        majorDTO2.setID(2);

        ArrayList<MajorDTO> majors = new ArrayList<>()
        {{
            add(majorDTO1);
            add(majorDTO2);
        }};

        when(mockMajorGateway.getAllMajors()).thenReturn(majors);

        CommandGetMajors command = new CommandGetMajors();
        command.execute();

        verify(obs, times(1)).receiveReport(new GetAllMajorsReport(majors));
    }
}
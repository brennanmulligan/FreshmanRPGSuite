package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.CrewDTO;
import edu.ship.engr.shipsim.datasource.CrewTableDataGateway;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.model.reports.GetAllCrewsReport;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CommandGetCrewsTest
{
    @Test
    public void testGetAllCrews() throws DatabaseException
    {
        ReportObserver obs = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton().registerObserver(obs,
                GetAllCrewsReport.class);

        CrewTableDataGateway crewGateway = CrewTableDataGateway.getSingleton();
        CrewTableDataGateway mockCrewGateway = mock(CrewTableDataGateway.class);
        crewGateway.setSingleton(mockCrewGateway);

        CrewDTO crewDTO1 = new CrewDTO();
        crewDTO1.setID(1);
        CrewDTO crewDTO2 = new CrewDTO();
        crewDTO2.setID(2);

        ArrayList<CrewDTO> crews = new ArrayList<>()
        {{
            add(crewDTO1);
            add(crewDTO2);
        }};

        when(mockCrewGateway.getAllCrews()).thenReturn(crews);

        CommandGetCrews command = new CommandGetCrews();
        command.execute();

        verify(obs, times(1)).receiveReport(new GetAllCrewsReport(crews));
    }
}
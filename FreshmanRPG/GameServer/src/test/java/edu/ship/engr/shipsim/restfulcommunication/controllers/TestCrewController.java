package edu.ship.engr.shipsim.restfulcommunication.controllers;

import edu.ship.engr.shipsim.dataDTO.CrewDTO;
import edu.ship.engr.shipsim.dataDTO.PlayerDTO;
import edu.ship.engr.shipsim.model.reports.CreatePlayerResponseReport;
import edu.ship.engr.shipsim.model.reports.GetAllCrewsReport;
import edu.ship.engr.shipsim.model.reports.GetAllPlayersReport;
import edu.ship.engr.shipsim.restfulcommunication.representation.CreatePlayerInformation;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetQuestManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Scott Bucher
 */
@GameTest("GameServer")
@ResetModelFacade
@ResetQuestManager
@ResetPlayerManager
@ResetReportObserverConnector
public class TestCrewController
{

    @Test
    public void goodResponseCrews()
    {
        CrewController mock = mock(CrewController.class);

        CrewDTO crew1 = new CrewDTO();
        crew1.setCrewID(1);
        CrewDTO crew2 = new CrewDTO();
        crew2.setCrewID(2);

        when(mock.processAction(any(Runnable.class), eq(GetAllCrewsReport.class))).thenReturn(
                new GetAllCrewsReport(new ArrayList<>()
                    {{
                        add(crew1);
                        add(crew2);
                    }}
                ));

        when(mock.getAllCrews()).thenCallRealMethod();


        ResponseEntity<Object> response = mock.getAllCrews();

        // Response body is the array list of players
        ArrayList<CrewDTO> crews = (ArrayList<CrewDTO>) response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(crews);
        assertEquals(crews.get(0).getCrewID(), 1);
        assertEquals(crews.get(1).getCrewID(), 2);
    }
}


package edu.ship.engr.shipsim.restfulcommunication.controllers;

import edu.ship.engr.shipsim.dataDTO.MajorDTO;
import edu.ship.engr.shipsim.model.reports.GetAllMajorsReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetQuestManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

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
public class TestMajorController
{

    @Test
    public void goodResponseMajors()
    {
        MajorController mock = mock(MajorController.class);

        MajorDTO major1 = new MajorDTO();
        major1.setMajorID(1);
        MajorDTO major2 = new MajorDTO();
        major2.setMajorID(2);

        when(mock.processAction(any(Runnable.class), eq(GetAllMajorsReport.class))).thenReturn(
                new GetAllMajorsReport(new ArrayList<>()
                    {{
                        add(major1);
                        add(major2);
                    }}
                ));

        when(mock.getAllMajors()).thenCallRealMethod();


        ResponseEntity<Object> response = mock.getAllMajors();

        // Response body is the array list of players
        ArrayList<MajorDTO> majors = (ArrayList<MajorDTO>) response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(majors);
        assertEquals(majors.get(0).getMajorID(), 1);
        assertEquals(majors.get(1).getMajorID(), 2);
    }
}


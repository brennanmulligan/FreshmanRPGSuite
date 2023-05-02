package edu.ship.engr.shipsim.restfulcommunication.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
            throws JsonProcessingException, JSONException
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
        assertEquals(HttpStatus.OK, response.getStatusCode());

        JSONObject last = new JSONObject((String) response.getBody());
        JSONArray records = (JSONArray) last.get("crews");

        assertEquals(records.getJSONObject(0).getInt("crewID"), 1);
        assertEquals(records.getJSONObject(1).getInt("crewID"), 2);
    }
}


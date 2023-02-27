package edu.ship.engr.shipsim.restfulcommunication.controllers;

import edu.ship.engr.shipsim.dataDTO.PlayerDTO;
import edu.ship.engr.shipsim.model.reports.CreatePlayerResponseReport;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Derek
 */
@GameTest("GameServer")
@ResetModelFacade
@ResetQuestManager
@ResetPlayerManager
@ResetReportObserverConnector
public class TestPlayerController
{

    @Test
    public void goodResponseCreatePlayer()
    {
        PlayerController mock = mock(PlayerController.class);
        when(mock.processAction(any(Runnable.class), eq(CreatePlayerResponseReport.class))).thenReturn(
                new CreatePlayerResponseReport(true));
        when(mock.createPlayer(any(CreatePlayerInformation.class))).thenCallRealMethod();

        ResponseEntity<Object> response = mock.createPlayer(new CreatePlayerInformation("fred", "ow", 1, 2, 3));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"description\":\"Created\",\"success\":true}", Objects.requireNonNull(response.getBody()).toString());
    }

    @Test
    public void badResponseCreatePlayer()
    {
        PlayerController mock = mock(PlayerController.class);
        when(mock.processAction(any(Runnable.class), eq(CreatePlayerResponseReport.class))).thenReturn(
                new CreatePlayerResponseReport(false, "ERROR"));
        when(mock.createPlayer(any(CreatePlayerInformation.class))).thenCallRealMethod();

        ResponseEntity<Object> response = mock.createPlayer(new CreatePlayerInformation("fred", "ow", 1, 2, 3));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"description\":\"ERROR\",\"success\":false}", Objects.requireNonNull(response.getBody()).toString());
    }

    @Test
    public void goodResponseGetAllPlayers()
    {
        PlayerController mock = mock(PlayerController.class);

        when(mock.processAction(any(Runnable.class), eq(GetAllPlayersReport.class))).thenReturn(
                new GetAllPlayersReport(new ArrayList<>() {{
                    add(mock(PlayerDTO.class));
                    add(mock(PlayerDTO.class));
                    add(mock(PlayerDTO.class));
                }}));

        when(mock.getAllPlayers()).thenCallRealMethod();

        ResponseEntity<Object> response = mock.getAllPlayers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("[{\"id\":0,\"username\":\"\",\"password\":\"\",\"crewNum\":0,\"majorNum\":0,\"section\":0,\"questId\":0,\"questProgress\":0,\"questCompleted\":false,\"questFailed\":false},{\"id\":0,\"username\":\"\",\"password\":\"\",\"crewNum\":0,\"majorNum\":0,\"section\":0,\"questId\":0,\"questProgress\":0,\"questCompleted\":false,\"questFailed\":false},{\"id\":0,\"username\":\"\",\"password\":\"\",\"crewNum\":0,\"majorNum\":0,\"section\":0,\"questId\":0,\"questProgress\":0,\"questCompleted\":false,\"questFailed\":false}]",
//                Objects.requireNonNull(response.getBody()).toString());
    }
}


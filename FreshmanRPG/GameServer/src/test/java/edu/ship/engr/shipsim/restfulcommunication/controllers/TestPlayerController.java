package edu.ship.engr.shipsim.restfulcommunication.controllers;

import edu.ship.engr.shipsim.model.reports.CreatePlayerResponseReport;
import edu.ship.engr.shipsim.restfulcommunication.representation.CreatePlayerInformation;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetQuestManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    public void goodResponse()
    {
        PlayerController mock = mock(PlayerController.class);
        when(mock.processAction(any(Runnable.class), eq(CreatePlayerResponseReport.class))).thenReturn(
                new CreatePlayerResponseReport(true));
        when(mock.createPlayer(any(CreatePlayerInformation.class))).thenCallRealMethod();

        ResponseEntity<Object> response = mock.createPlayer(new CreatePlayerInformation("fred", "ow", 1, 2, 3));

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void badResponse()
    {
        PlayerController mock = mock(PlayerController.class);
        when(mock.processAction(any(Runnable.class), eq(CreatePlayerResponseReport.class))).thenReturn(
                new CreatePlayerResponseReport(false, "ERROR"));
        when(mock.createPlayer(any(CreatePlayerInformation.class))).thenCallRealMethod();

        ResponseEntity<Object> response = mock.createPlayer(new CreatePlayerInformation("fred", "ow", 1, 2, 3));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"description\":\"ERROR\"}", Objects.requireNonNull(response.getBody()).toString());
    }
}


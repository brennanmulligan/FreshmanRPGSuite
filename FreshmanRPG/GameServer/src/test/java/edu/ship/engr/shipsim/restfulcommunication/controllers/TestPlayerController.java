package edu.ship.engr.shipsim.restfulcommunication.controllers;

import edu.ship.engr.shipsim.dataDTO.PlayerDTO;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.model.reports.ChangePlayerResponseReport;
import edu.ship.engr.shipsim.model.reports.CreatePlayerResponseReport;
import edu.ship.engr.shipsim.model.reports.GetAllPlayersReport;
import edu.ship.engr.shipsim.restfulcommunication.representation.ChangePlayerInformation;
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

        PlayerDTO player1 = new PlayerDTO();
        player1.setPlayerID(1);
        PlayerDTO player2 = new PlayerDTO();
        player2.setPlayerID(2);

        when(mock.processAction(any(Runnable.class), eq(GetAllPlayersReport.class))).thenReturn(
                new GetAllPlayersReport(new ArrayList<>()
                {{
                    add(player1);
                    add(player2);
                }}
                ));

        when(mock.getAllPlayers()).thenCallRealMethod();

        ResponseEntity<Object> response = mock.getAllPlayers();

        // Response body is the array list of players
        ArrayList<PlayerDTO> players = (ArrayList<PlayerDTO>) response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(players);
        assertEquals(players.get(0).getPlayerID(), 1);
        assertEquals(players.get(1).getPlayerID(), 2);
    }

    @Test
    public void goodResponseChangePlayer()
    {
        PlayerController mock = mock(PlayerController.class);
        when(mock.processAction(any(Runnable.class), eq(
                ChangePlayerResponseReport.class))).thenReturn(
                new ChangePlayerResponseReport(true));
        when(mock.changePlayer(any(ChangePlayerInformation.class))).thenCallRealMethod();

        ResponseEntity<Object> response = mock.changePlayer(new ChangePlayerInformation("fred", "ow"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"description\":\"Changed\",\"success\":true}", Objects.requireNonNull(response.getBody()).toString());
    }
}
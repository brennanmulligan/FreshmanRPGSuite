package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.PlayerDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.PlayerTableDataGateway;
import edu.ship.engr.shipsim.model.reports.GetAllPlayersReport;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CommandGetAllPlayersTest
{
    @Test
    public void testGetAllPlayers() throws DatabaseException
    {
        ReportObserver obs = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton().registerObserver(obs, GetAllPlayersReport.class);

        PlayerTableDataGateway gateway = PlayerTableDataGateway.getSingleton();
        PlayerTableDataGateway mockGateway = mock(PlayerTableDataGateway.class);
        gateway.setSingleton(mockGateway);

        PlayerDTO player1 = new PlayerDTO();
        player1.setPlayerID(1);
        PlayerDTO player2 = new PlayerDTO();
        player2.setPlayerID(2);

        ArrayList<PlayerDTO> players = new ArrayList<>()
        {{
            add(player1);
            add(player2);
        }};

        when(mockGateway.getAllPlayers()).thenReturn(
                players);

        CommandGetAllPlayers cmd = new CommandGetAllPlayers();
        cmd.execute();

        verify(obs, times(1)).receiveReport(new GetAllPlayersReport(players));
    }
}

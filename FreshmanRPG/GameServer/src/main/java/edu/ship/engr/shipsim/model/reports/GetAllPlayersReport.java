package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.PlayerDTO;
import edu.ship.engr.shipsim.model.Report;
import lombok.Data;

import java.util.ArrayList;

@Data
public class GetAllPlayersReport implements Report
{
    private final ArrayList<PlayerDTO> players;

    public GetAllPlayersReport(ArrayList<PlayerDTO> players)
    {
        this.players = players;
    }
}

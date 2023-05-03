package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.PlayerDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.PlayerTableDataGateway;
import edu.ship.engr.shipsim.model.reports.GetAllPlayersReport;

import java.util.ArrayList;

public class CommandGetAllPlayers extends Command
{
    public CommandGetAllPlayers()
    {

    }
    @Override
    void execute()
    {
        try
        {
            PlayerTableDataGateway gateway = PlayerTableDataGateway.getSingleton();

            ArrayList<PlayerDTO> players = gateway.getAllPlayers();
            GetAllPlayersReport report = new GetAllPlayersReport(players);
            ReportObserverConnector.getSingleton().sendReport(report);
        }
        catch (DatabaseException e)
        {
            throw new RuntimeException(e);
        }
    }
}

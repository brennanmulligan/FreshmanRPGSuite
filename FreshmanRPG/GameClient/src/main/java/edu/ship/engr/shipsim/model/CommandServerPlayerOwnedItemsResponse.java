package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.model.reports.ServerPlayerOwnedItemsResponseReport;

import java.util.ArrayList;

public class CommandServerPlayerOwnedItemsResponse extends Command
{
    private final ArrayList<VanityDTO> serverOwnedItems;

    public CommandServerPlayerOwnedItemsResponse(ArrayList<VanityDTO> serverOwnedItems)
    {
        System.out.println("step 7");
        this.serverOwnedItems = serverOwnedItems;
    }

    @Override
    void execute()
    {
        ServerPlayerOwnedItemsResponseReport report = new ServerPlayerOwnedItemsResponseReport(serverOwnedItems);
        ReportObserverConnector.getSingleton().sendReport(report);
    }
}

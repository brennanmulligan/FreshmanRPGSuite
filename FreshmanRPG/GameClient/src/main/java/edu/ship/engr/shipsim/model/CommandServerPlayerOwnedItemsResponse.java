package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.model.reports.ServerPlayerOwnedItemsResponseReport;

import java.io.IOException;
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
    boolean execute() throws IOException
    {
        ServerPlayerOwnedItemsResponseReport report = new ServerPlayerOwnedItemsResponseReport(serverOwnedItems);
        QualifiedObservableConnector.getSingleton().sendReport(report);

        return true;
    }
}

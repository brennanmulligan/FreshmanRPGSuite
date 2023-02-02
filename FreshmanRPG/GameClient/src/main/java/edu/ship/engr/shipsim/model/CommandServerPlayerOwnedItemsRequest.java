package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.ServerPlayerOwnedItemsRequestReport;

/**
 * Command that requests the server's version of the player's owned items
 */
public class CommandServerPlayerOwnedItemsRequest extends Command
{

    @Override
    void execute()
    {
        System.out.println("step 1");
        ServerPlayerOwnedItemsRequestReport r = new ServerPlayerOwnedItemsRequestReport();
        ReportObserverConnector.getSingleton().sendReport(r);
    }
}

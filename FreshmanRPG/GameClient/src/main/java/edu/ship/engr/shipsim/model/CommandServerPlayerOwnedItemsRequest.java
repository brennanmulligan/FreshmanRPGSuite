package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.ServerPlayerOwnedItemsRequestReport;

import java.io.IOException;

/**
 * Command that requests the server's version of the player's owned items
 */
public class CommandServerPlayerOwnedItemsRequest extends Command
{

    @Override
    boolean execute() throws IOException
    {
        System.out.println("step 1");
        ServerPlayerOwnedItemsRequestReport r = new ServerPlayerOwnedItemsRequestReport();
        QualifiedObservableConnector.getSingleton().sendReport(r);

        return true;
    }
}

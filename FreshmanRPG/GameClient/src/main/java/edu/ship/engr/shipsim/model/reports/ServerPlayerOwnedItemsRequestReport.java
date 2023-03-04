package edu.ship.engr.shipsim.model.reports;

public class ServerPlayerOwnedItemsRequestReport extends SendMessageReport
{
    public ServerPlayerOwnedItemsRequestReport()
    {
        // Happens on client, thus it will always be loud
        super(0, false);
    }
}

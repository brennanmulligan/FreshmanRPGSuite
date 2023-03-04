package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.ClientPlayerManager;

public class ShopInventoryRequestReport extends SendMessageReport
{
    public ShopInventoryRequestReport()
    {
        // Happens on client, thus it will always be loud
        super(0, false);
    }
}

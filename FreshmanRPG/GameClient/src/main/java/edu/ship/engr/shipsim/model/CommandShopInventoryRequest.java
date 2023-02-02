package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.ShopInventoryRequestReport;

/**
 * Command that requests the available shop inventory
 */
public class CommandShopInventoryRequest extends Command
{
    @Override
    void execute()
    {
        ShopInventoryRequestReport r = new ShopInventoryRequestReport();
        ReportObserverConnector.getSingleton().sendReport(r);
    }
}

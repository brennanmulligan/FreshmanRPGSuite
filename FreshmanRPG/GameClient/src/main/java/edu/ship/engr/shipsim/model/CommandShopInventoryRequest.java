package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.ShopInventoryRequestReport;

/**
 * Command that requests the available shop inventory
 */
public class CommandShopInventoryRequest extends Command
{
    @Override
    boolean execute()
    {
        ShopInventoryRequestReport r = new ShopInventoryRequestReport();
        QualifiedObservableConnector.getSingleton().sendReport(r);

        return true;
    }
}

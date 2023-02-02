package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.model.reports.VanityShopInventoryResponseReport;

import java.util.ArrayList;

/**
 * Command to send a report of the Vanity Shop inventory
 *
 * @author Aaron, Jake
 */
public class CommandVanityShopInventoryResponse extends Command
{
    ArrayList<VanityDTO> inventory;

    /**
     * Make a new command
     *
     * @param inventory the list of Vanity items in the shop
     */
    public CommandVanityShopInventoryResponse(ArrayList<VanityDTO> inventory)
    {
        this.inventory = inventory;
    }

    /**
     * perform the action associated with this command
     */
    @Override
    void execute()
    {
        VanityShopInventoryResponseReport report = new VanityShopInventoryResponseReport(inventory);
        ReportObserverConnector.getSingleton().sendReport(report);
    }

    /**
     * @return the list of Vanity this command is holding
     */
    public ArrayList<VanityDTO> getInventory()
    {
        return inventory;
    }
}

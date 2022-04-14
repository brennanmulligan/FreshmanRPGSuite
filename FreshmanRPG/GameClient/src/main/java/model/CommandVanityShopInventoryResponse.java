package model;

import dataDTO.VanityDTO;
import model.reports.VanityShopInventoryResponseReport;
import java.util.ArrayList;

/**
 * Command to send a report of the Vanity Shop inventory
 * @author Aaron, Jake
 */
public class CommandVanityShopInventoryResponse extends Command
{
    ArrayList<VanityDTO> inventory;

    /**
     * Make a new command
     * @param inventory the list of Vanity items in the shop
     */
    public CommandVanityShopInventoryResponse(ArrayList<VanityDTO> inventory)
    {
        this.inventory = inventory;
    }

    /**
     * perform the action associated with this command
     *
     * @return true of the operation was successful
     */
    @Override
    boolean execute()
    {
        VanityShopInventoryResponseReport report = new VanityShopInventoryResponseReport(inventory);
        QualifiedObservableConnector.getSingleton().sendReport(report);
        return true;
    }

    /**
     * @return the list of Vanity this command is holding
     */
    public ArrayList<VanityDTO> getInventory()
    {
        return inventory;
    }
}

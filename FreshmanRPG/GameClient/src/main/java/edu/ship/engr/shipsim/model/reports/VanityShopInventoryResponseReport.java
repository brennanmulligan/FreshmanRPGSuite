package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.model.Report;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A report for when we get the response containing the Vanity Shop inventory items
 *
 * @author Aaron, Jake
 */
public final class VanityShopInventoryResponseReport implements Report
{
    private final ArrayList<VanityDTO> inventory;

    /**
     * New report
     *
     * @param inventory the list of inventory items
     */
    public VanityShopInventoryResponseReport(ArrayList<VanityDTO> inventory)
    {
        this.inventory = inventory;
    }

    /**
     * @return the list of inventory items
     */
    public ArrayList<VanityDTO> getInventory()
    {
        return inventory;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof VanityShopInventoryResponseReport))
        {
            return false;
        }
        VanityShopInventoryResponseReport other = (VanityShopInventoryResponseReport) obj;
        if (inventory == null)
        {
            if (other.inventory != null)
            {
                return false;
            }
        }
        else if (!inventory.equals(other.inventory))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(inventory);
    }
}

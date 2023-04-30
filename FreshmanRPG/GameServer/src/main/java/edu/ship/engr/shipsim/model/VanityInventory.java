package edu.ship.engr.shipsim.model;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.VanityInventoryTableDataGateway;
import edu.ship.engr.shipsim.datasource.VanityItemsTableDataGateway;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Objects;

public class VanityInventory
{
    private ArrayList<VanityDTO> wornItems;
    private ArrayList<VanityDTO> vanityItems;

    public VanityInventory(int playerID) throws DatabaseException
    {
        vanityItems = VanityInventoryTableDataGateway.getSingleton().getAllOwnedItems(playerID);
        wornItems = VanityInventoryTableDataGateway.getSingleton().getWearing(playerID);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        VanityInventory that = (VanityInventory) o;

        return Objects.equals(vanityItems, that.vanityItems);
    }

    @Override
    public int hashCode()
    {
        return vanityItems != null ? vanityItems.hashCode() : 0;
    }

    public void addVanityItem(VanityDTO vanityItem)
    {
        if (vanityItem != null)
        {
            vanityItems.add(vanityItem);
        }
    }

    public void removeVanityItem(VanityDTO vanityItem)
    {
        if (vanityItem != null)
        {
            vanityItems.remove(vanityItem);
        }
    }

    @SneakyThrows
    public void removeVanityItemByID(int itemID)
    {
        // TODO: Actually make this check functional
        if(VanityItemsTableDataGateway.getSingleton().checkDeletability(itemID))
        {
            this.vanityItems.removeIf(item -> item.getID() == itemID);
        }
    }

    public VanityDTO getVanityItemByID(int id)
    {
        for (VanityDTO item : this.vanityItems)
        {
            if(item.getID() == id)
            {
                return item;
            }
        }
        return null;
    }

    public void setInventory(ArrayList<VanityDTO> vanityItems)
    {
        this.vanityItems = vanityItems;
    }

    public ArrayList<VanityDTO> getInventory()
    {
        return this.vanityItems;
    }

    public ArrayList<VanityDTO> getWornItems()
    {
        return this.wornItems;
    }

    public void addWornItem(VanityDTO newItem)
    {
        for (VanityDTO item : wornItems)
        {
            if (item.getVanityType() == newItem.getVanityType())
            {
                wornItems.remove(item);
                wornItems.add(newItem);
            }
        }
    }

    public void setWornItems(ArrayList<VanityDTO> vanityItems)
    {
        this.wornItems = vanityItems;
    }

    public void removeOwnedItem(VanityDTO vanityItem)
    {
        if (vanityItem != null)
        {
            wornItems.remove(vanityItem);
        }
    }
}

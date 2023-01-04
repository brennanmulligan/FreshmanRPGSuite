package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.model.Report;

import java.util.ArrayList;
import java.util.Objects;

public class ServerPlayerOwnedItemsResponseReport implements Report
{
    private final ArrayList<VanityDTO> serverOwnedItems;

    public ServerPlayerOwnedItemsResponseReport(ArrayList<VanityDTO> serverOwnedItems)
    {
        System.out.println("step 8");
        this.serverOwnedItems = serverOwnedItems;
    }

    /**
     * @return the list of the server's copy of owned items
     */
    public ArrayList<VanityDTO> getServerOwnedVanities()
    {
//        System.out.println("\nItems from getServerOwnedVanities() ServerPlayerOwnedItemsResponseReport");
//        serverOwnedItems.forEach(System.out::println);
        return serverOwnedItems;
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
        if (!(obj instanceof ServerPlayerOwnedItemsResponseReport))
        {
            return false;
        }
        ServerPlayerOwnedItemsResponseReport other = (ServerPlayerOwnedItemsResponseReport) obj;
        if (serverOwnedItems == null)
        {
            if (other.serverOwnedItems != null)
            {
                return false;
            }
        }
        else if (!serverOwnedItems.equals(other.serverOwnedItems))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(serverOwnedItems);
    }
}

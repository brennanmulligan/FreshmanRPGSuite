package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Contains the list of items in the vanity shop
 *
 * @author Jake, Aaron
 */
public class VanityShopInventoryResponseMessage extends Message
{
    private static final long serialVersionUID = 1L;
    private ArrayList<VanityDTO> vanityShopInventory;

    /**
     * @param vanityShopItems the list of items in the vanity shop
     */
    public VanityShopInventoryResponseMessage(ArrayList<VanityDTO> vanityShopItems, boolean quietMessage)
    {
        super(0,quietMessage);
        this.vanityShopInventory = vanityShopItems;
    }

    /**
     * @return the list of items in the vanity shop
     */
    public ArrayList<VanityDTO> getVanityShopInventory()
    {
        return vanityShopInventory;
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
        VanityShopInventoryResponseMessage that = (VanityShopInventoryResponseMessage) o;
        return vanityShopInventory.containsAll(that.vanityShopInventory) && that.vanityShopInventory.containsAll(vanityShopInventory);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(vanityShopInventory);
    }
}

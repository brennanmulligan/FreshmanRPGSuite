package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Contains the server's copy of the player's owned items
 */
public class ServerPlayerOwnedItemsResponseMessage extends Message
{
    private static final long serialVersionUID = 1L;
    private final ArrayList<VanityDTO> serverOwnedItems;

    /**
     * @param serverOwnedItems server's copy of the player's owned items
     */
    public ServerPlayerOwnedItemsResponseMessage(ArrayList<VanityDTO> serverOwnedItems, boolean quietMessage)
    {
        super(0, quietMessage);
        this.serverOwnedItems = new ArrayList<>(serverOwnedItems);
    }

    /**
     * @return the list of the server's copy of owned items
     */
    public ArrayList<VanityDTO> getServerOwnedVanities()
    {
        System.out.println("\nItems from getServerOwnedVanities() ServerPlayerOwnedItemsResponseMessage");
        serverOwnedItems.forEach(System.out::println);
        return serverOwnedItems;
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
        ServerPlayerOwnedItemsResponseMessage that = (ServerPlayerOwnedItemsResponseMessage) o;
        return serverOwnedItems.containsAll(that.serverOwnedItems) && that.serverOwnedItems.containsAll(serverOwnedItems);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(serverOwnedItems);
    }

    public String toString()
    {
        String str = "";
        for (VanityDTO dto : serverOwnedItems)
        {
            str += dto.getName() + "\n";
        }
        return str;
    }
}

package communication.messages;

import dataDTO.VanityDTO;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Contains the server's copy of the player's owned items
 */
public class ServerPlayerOwnedItemsResponseMessage implements Message
{
    private static final long serialVersionUID = 1L;
    private final ArrayList<VanityDTO> serverOwnedItems;

    /**
     * @param serverOwnedItems server's copy of the player's owned items
     */
    public ServerPlayerOwnedItemsResponseMessage(ArrayList<VanityDTO> serverOwnedItems)
    {
        System.out.println("step 5");
        this.serverOwnedItems = serverOwnedItems;
    }

    /**
     * @return the list of the server's copy of owned items
     */
    public ArrayList<VanityDTO> getServerOwnedVanities()
    {
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
}

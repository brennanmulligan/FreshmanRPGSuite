package edu.ship.engr.shipsim.communication.messages;

public class ServerPlayerOwnedItemsRequestMessage extends Message
{
    private static final long serialVersionUID = 1L;

    public ServerPlayerOwnedItemsRequestMessage(int playerID)
    {
        super(playerID);
        System.out.println("step 3");

    }
}

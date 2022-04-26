package communication.messages;

public class ServerPlayerOwnedItemsRequestMessage implements Message
{
    private static final long serialVersionUID = 1L;
    int playerID;

    public ServerPlayerOwnedItemsRequestMessage(int playerID)
    {
        System.out.println("step 3");
        this.playerID = playerID;
    }

    public int getPlayerID()
    {
        return playerID;
    }
}

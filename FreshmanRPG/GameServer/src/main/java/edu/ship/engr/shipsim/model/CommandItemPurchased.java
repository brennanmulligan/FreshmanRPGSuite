package edu.ship.engr.shipsim.model;

/**
 * Command used to update the doubloons of a player when a purchase is made
 *
 * @author Andrew Stake
 */
public class CommandItemPurchased extends Command
{
    private int playerID;
    private int price;

    /**
     * @param playerID the player who made the purchase
     * @param price    how many points to deduct
     */
    public CommandItemPurchased(int playerID, int price)
    {
        this.playerID = playerID;
        this.price = price;
    }

    /**
     * @see Command#execute()
     */
    @Override
    boolean execute()
    {
        Player player = PlayerManager.getSingleton().getPlayerFromID(playerID);

        if (player == null)
        {
            return false;
        }

        player.removeDoubloons(price);
        return true;
    }

}
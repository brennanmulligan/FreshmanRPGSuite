package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;

/**
 * Command to add a clothing item to a players inventory
 */
public class CommandPlayerPurchasedClothing extends Command
{
    private final int playerID;
    private final VanityDTO vanity;

    /**
     * Command to add a clothing item to a players inventory
     *
     * @param playerID the id of the player
     * @param vanity   the vanity item
     */
    public CommandPlayerPurchasedClothing(int playerID, VanityDTO vanity)
    {
        this.playerID = playerID;
        this.vanity = vanity;
    }

    /**
     * perform the action associated with this command
     *
     */
    @Override
    void execute()
    {
        Player player = PlayerManager.getSingleton().getPlayerFromID(playerID);
        player.addItemToInventory(vanity);
    }
}

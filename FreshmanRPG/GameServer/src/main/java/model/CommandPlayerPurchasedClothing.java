package model;

import dataDTO.VanityDTO;

import java.io.IOException;

/**
 * Command to add a clothing item to a players inventory
 */
public class CommandPlayerPurchasedClothing extends Command
{
    private int playerID;
    private VanityDTO vanity;

    /**
     * Command to add a clothing item to a players inventory
     * @param playerID the id of the player
     * @param vanity the vanity item
     */
    public CommandPlayerPurchasedClothing(int playerID, VanityDTO vanity)
    {
        this.playerID = playerID;
        this.vanity = vanity;
    }

    /**
     * perform the action associated with this command
     *
     * @return true of the operation was successful
     * @throws IOException
     */
    @Override
    boolean execute() throws IOException
    {
        Player player = PlayerManager.getSingleton().getPlayerFromID(playerID);
        player.addItemToInventory(vanity);
        return true;
    }
}

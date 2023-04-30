package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.model.Command;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;

import java.util.ArrayList;

public class CommandRemovePlayerBike extends Command
{
    private final int playerID;

    CommandRemovePlayerBike(int playerID)
    {
        this.playerID = playerID;
    }

    /**
     * Removes the bike from the player
     */
    @Override
    void execute() throws DatabaseException
    {
        Player player = PlayerManager.getSingleton().getPlayerFromID(playerID);
        if (player != null)
        {
            player.removeVanityType(VanityType.BIKE);
        }
    }
}

package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.LoggerManager;

/**
 * Persist a player through a command
 *
 * @author Steve
 */
public class CommandPersistPlayer extends Command
{
    private final int playerID;

    /**
     * @param playerID The playerID to persist
     */
    public CommandPersistPlayer(int playerID)
    {
        this.playerID = playerID;
    }

    /**
     * @see Command#execute()
     */
    @Override
    void execute()
    {
        try
        {
            PlayerManager.getSingleton().persistPlayer(playerID);
        }
        catch (DatabaseException e)
        {
            LoggerManager.getSingleton().getLogger().fine(
                    "Database error trying to persist player " + playerID + ": "
                            + e.getMessage());
        }
    }

}

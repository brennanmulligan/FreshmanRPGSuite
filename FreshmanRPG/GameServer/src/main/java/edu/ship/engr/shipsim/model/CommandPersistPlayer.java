package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;

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
    boolean execute()
    {
        try
        {
            return PlayerManager.getSingleton().persistPlayer(playerID);
        }
        catch (DatabaseException e)
        {
            return false;
        }
    }

}

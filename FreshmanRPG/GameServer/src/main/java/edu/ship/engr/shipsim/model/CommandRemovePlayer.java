package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;

/**
 * Command used to remove a player from this area server's part of the game
 *
 * @author nhydock
 */
public class CommandRemovePlayer extends Command
{

    private final int playerID;

    /**
     * @param playerID the player's player id
     */
    public CommandRemovePlayer(int playerID)
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
            PlayerManager.getSingleton().removePlayer(playerID);
        }
        catch (DatabaseException e)
        {
            //Couldn't disconnect/remove player
            e.printStackTrace();
        }
        QuestManager.getSingleton().removeQuestStates(playerID);
    }

}

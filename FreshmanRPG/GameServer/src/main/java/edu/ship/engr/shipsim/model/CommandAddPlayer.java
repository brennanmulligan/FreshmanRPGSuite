package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;

/**
 * Command used to add a new player to this area server's part of the game
 *
 * @author Merlin
 */
public class CommandAddPlayer extends Command
{

    private final int playerID;
    private final double pin;

    /**
     * @param playerID the player's player id
     * @param pin      the player's pin
     */
    public CommandAddPlayer(int playerID, double pin)
    {
        this.playerID = playerID;
        this.pin = pin;
    }

    /**
     * @see Command#execute()
     */
    @Override
    void execute()
    {
        try
        {
            PlayerManager.getSingleton().addPlayer(playerID, pin);
        }
        catch (DatabaseException | IllegalQuestChangeException e)
        {
            e.printStackTrace();
        }
    }

}

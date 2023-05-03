package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.Position;

/**
 * Command used to move a player on the server's part of the game
 *
 * @author Frank
 */
public class CommandMovePlayer extends Command
{
    private final int playerId;
    private final Position newPosition;

    /**
     * @param playerId    The player who to move
     * @param newPosition The new Position to move
     */
    public CommandMovePlayer(int playerId, Position newPosition)
    {
        this.playerId = playerId;
        this.newPosition = newPosition;
    }

    /**
     * @see Command#execute()
     */
    @Override
    void execute()
    {
        Player player = PlayerManager.getSingleton().getPlayerFromID(playerId);
        if (player != null)
        {
            player.setPosition(newPosition);
        }
    }
}

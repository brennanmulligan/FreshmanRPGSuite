package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.Position;

/**
 * Changes a player's position without notifying the rest of the players on the
 * server.
 *
 * @author Merlin
 */
public class CommandMovePlayerSilently extends Command
{

    private final Position position;
    private final int playerID;
    private final String mapName;

    /**
     * @param mapName  the name of the map the player should be on
     * @param playerID the player who is moving
     * @param position the position they should be moving to
     */
    public CommandMovePlayerSilently(String mapName, int playerID, Position position)
    {
        this.mapName = mapName;
        this.position = position;
        this.playerID = playerID;
    }

    /**
     * @see Command#execute()
     */
    @Override
    void execute()
    {
        Player player = PlayerManager.getSingleton().getPlayerFromID(playerID);
        if (player != null)
        {
            player.setPositionWithoutNotifying(position);
            player.setMapName(mapName);
        }
    }

}

package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.LoggerManager;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.PlayerReadyToTeleportReport;

/**
 * Moves the player without generating a message, and then saves them to the database.
 */
public class CommandMovePlayerToAnotherMapAndPersist extends Command
{

    private final int playerId;
    private final String map;
    private final Position position;

    /**
     * @param playerId The player id that should be moved and then saved.
     * @param map      The map that they are moved to.
     * @param position The position that they are moved to.
     */
    public CommandMovePlayerToAnotherMapAndPersist(int playerId, String map, Position position)
    {
        this.playerId = playerId;
        this.map = map;
        this.position = position;
    }

    @Override
    void execute()
    {
        PlayerManager playerManager = PlayerManager.getSingleton();
        Player player = playerManager.getPlayerFromID(playerId);

        if (player != null)
        {
            player.setPlayerPositionWithoutNotifying(position);
            player.setMapName(map);

            try
            {
                playerManager.persistPlayer(playerId);
                PlayerReadyToTeleportReport report = new PlayerReadyToTeleportReport(playerId, map);
                ReportObserverConnector.getSingleton().sendReport(report);
            }
            catch (DatabaseException e)
            {
                LoggerManager.getSingleton().getLogger().info("Exception in moving the " +
                        "player:" + e.getMessage());
            }
        }
    }

}

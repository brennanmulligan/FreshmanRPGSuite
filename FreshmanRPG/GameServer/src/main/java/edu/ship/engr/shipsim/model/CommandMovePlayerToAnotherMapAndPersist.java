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

    private final int playerID;
    private final String mapName;
    private final Position position;

    /**
     * @param playerID The player id that should be moved and then saved.
     * @param mapName      The map that they are moved to.
     * @param position The position that they are moved to.
     */
    public CommandMovePlayerToAnotherMapAndPersist(int playerID, String mapName, Position position)
    {
        this.playerID = playerID;
        this.mapName = mapName;
        this.position = position;
    }

    @Override
    void execute()
    {
        PlayerManager playerManager = PlayerManager.getSingleton();
        Player player = playerManager.getPlayerFromID(playerID);

        if (player != null)
        {
            player.setPositionWithoutNotifying(position);
            player.setMapName(mapName);

            try
            {
                playerManager.persistPlayer(playerID);
                PlayerReadyToTeleportReport report = new PlayerReadyToTeleportReport(
                        playerID, mapName);
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

package model;

import datasource.DatabaseException;
import datatypes.Position;
import model.reports.PlayerReadyToTeleportReport;

/**
 * Moves the player without generating a message, and then saves them to the database.
 */
public class CommandMovePlayerSilentlyAndPersist extends Command
{

	private int playerId;
	private String map;
	private Position position;

	/**
	 * @param playerId The player id that should be moved and then saved.
	 * @param map The map that they are moved to.
	 * @param position The position that they are moved to.
	 */
	public CommandMovePlayerSilentlyAndPersist(int playerId, String map, Position position)
	{
		this.playerId = playerId;
		this.map = map;
		this.position = position;
	}

	@Override
	boolean execute()
	{
		PlayerManager playerManager = PlayerManager.getSingleton();
		Player player = playerManager.getPlayerFromID(playerId);

		if (player != null)
		{
			player.setPlayerPositionWithoutNotifying(position);
			player.setMapName(map);

			try
			{
				boolean result = playerManager.persistPlayer(playerId);
				PlayerReadyToTeleportReport report = new PlayerReadyToTeleportReport(playerId, map);
				QualifiedObservableConnector.getSingleton().sendReport(report);
				return result;

			}
			catch (DatabaseException | IllegalQuestChangeException e)
			{
			}
		}

		return false;
	}

}

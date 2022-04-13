package model;

import datatypes.Position;

/**
 * Command used to move a player on the server's part of the game
 *
 * @author Frank
 *
 */
public class CommandMovePlayer extends Command
{
	private int playerId;
	private Position newPosition;

	/**
	 * @param playerId The player who to move
	 * @param newPosition The new Position to move
	 */
	public CommandMovePlayer(int playerId, Position newPosition)
	{
		this.playerId = playerId;
		this.newPosition = newPosition;
	}

	/**
	 * @see model.Command#execute()
	 */
	@Override
	boolean execute()
	{
		Player player = PlayerManager.getSingleton().getPlayerFromID(playerId);
		if (player != null)
		{
			player.setPlayerPosition(newPosition);
			return true;
		}
		else
		{
			return false;
		}
	}
}

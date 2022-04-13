package model;

import datasource.DatabaseException;

/**
 * Command that tells a PlayerManager to delete a player.
 *
 */
public class CommandDeletePlayer extends Command
{
	private int playerID;

	/**
	 * Construct and instantiate a new CommandDeletePlayer.
	 *
	 * @param playerID - the id of the player to be deleted
	 */
	public CommandDeletePlayer(int playerID)
	{
		this.playerID = playerID;
	}

	/**
	 *
	 */
	@Override
	boolean execute()
	{
		try
		{
			GameManagerPlayerManager playerManager = GameManagerPlayerManager.getInstance();
			playerManager.removePlayer(playerID);
			return true;
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
			return false;
		}
	}
}

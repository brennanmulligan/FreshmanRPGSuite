package model;

import datasource.DatabaseException;

/**
 * Command used to remove a player from this area server's part of the game
 *
 * @author nhydock
 *
 */
public class CommandRemovePlayer extends Command
{

	private int playerID;

	/**
	 *
	 * @param playerID the player's player id
	 */
	public CommandRemovePlayer(int playerID)
	{
		this.playerID = playerID;
	}

	/**
	 *
	 * @see model.Command#execute()
	 */
	@Override
	boolean execute()
	{

		try
		{
			PlayerManager.getSingleton().removePlayer(playerID);
		}
		catch (DatabaseException | IllegalQuestChangeException e)
		{
			//Couldn't disconnect/remove player
			e.printStackTrace();
		}
		QuestManager.getSingleton().removeQuestStatesForPlayer(playerID);
		return true;
	}

}

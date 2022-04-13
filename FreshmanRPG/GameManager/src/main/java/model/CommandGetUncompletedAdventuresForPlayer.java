package model;

import datasource.DatabaseException;

/**
 * A command to retrieve all of the uncompleted adventures
 *
 *
 */
public class CommandGetUncompletedAdventuresForPlayer extends Command
{
	private int playerID;

	/**
	 * @param playerID
	 *            the player to get adventures for
	 */
	public CommandGetUncompletedAdventuresForPlayer(int playerID)
	{
		this.playerID = playerID;
	}

	/**
	 * @see model.Command#execute()
	 */
	@Override
	boolean execute()
	{
		GameManagerQuestManager manager = null;
		manager = GameManagerQuestManager.getInstance();
		try
		{
			manager.sendUncompletedAdventuresReport(playerID);
		}
		catch (DatabaseException e)
		{
			return false;
		}

		return true;
	}

}

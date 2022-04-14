package model;

import datasource.DatabaseException;

/**
 * A command to retrieve all of the uncompleted objectives
 *
 *
 */
public class CommandGetUncompletedObjectivesForPlayer extends Command
{
	private int playerID;

	/**
	 * @param playerID
	 *            the player to get objectives for
	 */
	public CommandGetUncompletedObjectivesForPlayer(int playerID)
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
			manager.sendUncompletedObjectivesReport(playerID);
		}
		catch (DatabaseException e)
		{
			return false;
		}

		return true;
	}

}

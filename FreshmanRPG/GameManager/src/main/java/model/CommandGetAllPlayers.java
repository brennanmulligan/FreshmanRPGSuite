package model;

import datasource.DatabaseException;

/**
 * @author Darin Alleman
 *
 */
public class CommandGetAllPlayers extends Command
{
	/**
	 * create a new report with all players
	 */
	@Override
	boolean execute()
	{
		try
		{
			GameManagerPlayerManager pm = GameManagerPlayerManager.getInstance();
			pm.refreshPlayerList();
			return true;
		}
		catch (DatabaseException e1)
		{
			return false;
		}
	}

}
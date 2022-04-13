package model;

import datasource.DatabaseException;

/**
 * A command that deletes all existing players from the database
 * @author Jordan Long
 */
public class CommandDeleteAllPlayers extends Command
{

	/**
	 * Constructor for the CommandDeleteAllPlayers
	 */
	public CommandDeleteAllPlayers()
	{
	}

	/**
	 * Execute method for command
	 * @return true if successful
	 */
	@Override
	public boolean execute()
	{

		try
		{
			GameManagerPlayerManager manager = GameManagerPlayerManager.getInstance();

			manager.removeAllPlayers();
			return true;
		}
		catch (DatabaseException e)
		{
			return false;
		}
	}


}

package model;

import datasource.DatabaseException;

/**
 * A command that deletes all existing quests and adventures from the database
 * @author Jordan Long
 *
 */
public class CommandDeleteAllQuestsAdventures extends Command
{

	/**
	 * Constructor for the CommandDeleteAllQuestions
	 */
	public CommandDeleteAllQuestsAdventures()
	{
	}

	/**
	 * Execute method for command
	 * @return true if successful
	 */
	@Override
	public boolean execute()
	{

		GameManagerQuestManager manager = GameManagerQuestManager.getInstance();

		try
		{
			manager.removeAllQuestsAdventures();
		}
		catch (DatabaseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}


}

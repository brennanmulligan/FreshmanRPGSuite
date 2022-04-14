package model;

import datasource.DatabaseException;

/**
 * A command that deletes all existing quests and objectives from the database
 * @author Jordan Long
 *
 */
public class CommandDeleteAllQuestsObjectives extends Command
{

	/**
	 * Constructor for the CommandDeleteAllQuestions
	 */
	public CommandDeleteAllQuestsObjectives()
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
			manager.removeAllQuestsObjectives();
		}
		catch (DatabaseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}


}

package model;

import datasource.DatabaseException;

/**
 * A command that deletes all existing questions from the database
 * @author Jordan Long
 *
 */
public class CommandDeleteAllQuestions extends Command
{

	/**
	 * Constructor for the CommandDeleteAllQuestions
	 */
	public CommandDeleteAllQuestions()
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
			QuestionManager manager = QuestionManager.getInstance();

			manager.removeAllQuestions();
			return true;
		}
		catch (DatabaseException e)
		{
			return false;
		}
	}


}

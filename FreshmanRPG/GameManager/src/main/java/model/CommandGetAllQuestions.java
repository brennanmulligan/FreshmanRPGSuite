package model;

import datasource.DatabaseException;

/**
 * Throws a report containing all questions.
 *
 */
public class CommandGetAllQuestions extends Command
{
	/**
	 * Execute command
	 * @return whether the command failed or not
	 */
	boolean execute()
	{
		try
		{
			QuestionManager.getInstance().refreshQuestionList();
		}
		catch (DatabaseException e)
		{
			return false;
		}
		return true;
	}
}

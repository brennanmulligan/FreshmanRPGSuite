package model;

import datasource.DatabaseException;

/**
 * A command that deletes one question from the data source
 *
 */
public class CommandDeleteQuestion extends Command
{
	private int ID;

	/**
	 * @param ID the ID of the question to delete
	 */
	public CommandDeleteQuestion(int ID)
	{
		this.ID = ID;
	}

	/**
	 * Executes the command for deleting a question.
	 * @return true if successful.
	 */
	@Override
	public boolean execute()
	{

		try
		{
			QuestionManager manager = QuestionManager.getInstance();

			manager.removeQuestion(this.ID);
			return true;
		}
		catch (DatabaseException e)
		{
			return false;
		}
	}


}
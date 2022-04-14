package model;

import datasource.DatabaseException;

/**
 * Command generates a quest list report
 *
 * @author da8289
 *
 */
public class CommandGetAllQuestsAndObjectives extends Command
{

	/**
	 * @see model.Command#execute()
	 */
	@Override
	boolean execute()
	{
		try
		{
			GameManagerQuestManager.getInstance().sendQuestReport();
		}
		catch (DatabaseException e)
		{
			return false;
		}
		return true;
	}

}

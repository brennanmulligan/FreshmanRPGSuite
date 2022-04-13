package model;

import datasource.DatabaseException;

/**
 * @author Darin Alleman
 *
 */
public class CommandTriggerQuestForAllUsers extends Command
{

	private int questID;

	/**
	 * @param questID the quest being triggered
	 */
	public CommandTriggerQuestForAllUsers(int questID)
	{
		this.questID = questID;
	}

	/**
	 * create a new report with all players
	 */
	@Override
	boolean execute()
	{
		try
		{
			GameManagerPlayerManager pm = GameManagerPlayerManager.getInstance();
			pm.triggerQuestForAllUsers(questID);
			return true;
		}
		catch (DatabaseException e1)
		{
			e1.printStackTrace();
		}
		return false;
	}

}
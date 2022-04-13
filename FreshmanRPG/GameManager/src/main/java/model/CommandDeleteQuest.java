package model;

import java.sql.SQLException;

/**
 * Command to delete a quest
 */
public class CommandDeleteQuest extends Command
{

	private int questID;
	private final GameManagerQuestManager manager = GameManagerQuestManager.getInstance();

	/**
	 * Constructor to take in questID
	 *
	 * @param questID ID of the quest to be deleted 
	 */
	public CommandDeleteQuest(int questID)
	{
		this.questID = questID;
	}

	/**
	 * Executes the command 
	 *
	 * @return true or false depending on success of the command 
	 */
	@Override
	boolean execute()
	{
		try
		{
			return manager.deleteQuest(this.questID);
		}
		catch (SQLException e)
		{
			return false;
		}
	}
}
package model;

/**
 * @author Ryan
 *
 */
public class CommandQuestNotificationComplete extends Command
{

	private int playerID;
	private int questID;

	/**
	 * Constructor
	 *
	 * @param playerID id of the player
	 * @param questID id of the quest
	 */
	public CommandQuestNotificationComplete(int playerID, int questID)
	{
		this.playerID = playerID;
		this.questID = questID;
	}

	/**
	 * @see model.Command#execute()
	 */
	@Override
	boolean execute()
	{
		QuestManager.getSingleton().turnOffQuestNotification(playerID, questID);
		return true;
	}

	/**
	 * @return id of the player
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return id of the quest
	 */
	public int getQuestID()
	{
		return questID;
	}
}

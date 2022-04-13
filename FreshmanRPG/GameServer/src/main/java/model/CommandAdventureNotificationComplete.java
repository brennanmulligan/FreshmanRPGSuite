package model;

import datasource.DatabaseException;

/**
 * @author Ryan
 *
 */
public class CommandAdventureNotificationComplete extends Command
{

	private int playerID;
	private int questID;
	private int adventureID;

	/**
	 * Constructor
	 *
	 * @param playerID id of the player
	 * @param questID id of the quest
	 * @param adventureID id of the adventure
	 */
	public CommandAdventureNotificationComplete(int playerID, int questID, int adventureID)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.adventureID = adventureID;
	}

	/**
	 * @see model.Command#execute()
	 */
	@Override
	boolean execute()
	{
		try
		{
			QuestManager.getSingleton().turnOffNotification(playerID, questID, adventureID);
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAdventureChangeException e)
		{
			e.printStackTrace();
		}
		catch (IllegalQuestChangeException e)
		{
			e.printStackTrace();
		}
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

	/**
	 * @return id of the adventure
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

}

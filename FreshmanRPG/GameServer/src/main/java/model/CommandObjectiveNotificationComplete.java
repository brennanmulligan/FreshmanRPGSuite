package model;

import datasource.DatabaseException;

/**
 * @author Ryan
 *
 */
public class CommandObjectiveNotificationComplete extends Command
{

	private int playerID;
	private int questID;
	private int objectiveID;

	/**
	 * Constructor
	 *
	 * @param playerID id of the player
	 * @param questID id of the quest
	 * @param objectiveID id of the objective
	 */
	public CommandObjectiveNotificationComplete(int playerID, int questID, int objectiveID)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.objectiveID = objectiveID;
	}

	/**
	 * @see model.Command#execute()
	 */
	@Override
	boolean execute()
	{
		try
		{
			QuestManager.getSingleton().turnOffNotification(playerID, questID, objectiveID);
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
		catch (IllegalObjectiveChangeException e)
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
	 * @return id of the objective
	 */
	public int getObjectiveID()
	{
		return objectiveID;
	}

}

package communication.messages;

import java.io.Serializable;

/**
 *
 * @author Ryan
 *
 */
public class ObjectiveNotificationCompleteMessage implements Message, Serializable
{

	private int playerID;
	private int questID;
	private int objectiveID;

	/**
	 * @param playerID id of the player
	 * @param questID id of the quest
	 * @param objectiveID id of the objective
	 */
	public ObjectiveNotificationCompleteMessage(int playerID, int questID, int objectiveID)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.objectiveID = objectiveID;
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

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
